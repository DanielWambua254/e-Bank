package com.example.onlinrbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class customer_workspace_activity extends AppCompatActivity {
    Button withdrawBtn;
    Button sendMoneyBtn;
    Button checkBalanceBtn;
    Button loansBtn;
    Button messagesBtn;
    Button editProfileBtn;
    TextView customerName;
    DBHelper DB;

    public  static final String EXTRA_TEXT2 = "com.example.onlineBanking.EXTRA_TEXT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_workspace_activity);

        withdrawBtn = findViewById(R.id.withdraw_btn);
        sendMoneyBtn = findViewById(R.id.send_money_btn);
        checkBalanceBtn = findViewById(R.id.check_BL_btn);
        loansBtn = findViewById(R.id.loans_btn);
        messagesBtn = findViewById(R.id.messages_btn);
        editProfileBtn = findViewById(R.id.edit_profile_btn);
        customerName = findViewById(R.id.customerName);
        DB = new DBHelper(this);



        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawCash();
            }
        });

        sendMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMoney();
            }
        });

        checkBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBalance();
            }
        });

        loansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(customer_workspace_activity.this, "This service is currently not available\nPlease try again later.", Toast.LENGTH_SHORT).show();
            }
        });

        messagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(customer_workspace_activity.this, "No messages.\n Thank you.", Toast.LENGTH_SHORT).show();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(customer_workspace_activity.this, "This service is temporarily disabled!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent2 = getIntent();
        String accTXT2 = intent2.getStringExtra(customer_login_page.EXTRA_TEXT);
        Cursor res= DB.readCustomerName(accTXT2);
        if (res.getCount()  == 0) {
            Toast.makeText(this, "brr", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append(res.getString(0)+"\n");
        }
        customerName.setText(buffer.toString());
    }

    public void withdrawCash() {
        Intent intent2 = getIntent();
        String accountNumberTXT = intent2.getStringExtra(customer_login_page.EXTRA_TEXT);
        Intent intent = new Intent(this, withdraw_activity.class);
        intent.putExtra(EXTRA_TEXT2, accountNumberTXT);
        startActivity(intent);
    }

    public void sendMoney() {
        Intent intent2 = getIntent();
        String accountNumberTXT = intent2.getStringExtra(customer_login_page.EXTRA_TEXT);
        Intent intent = new Intent(this, send_money_activity.class);
        intent.putExtra(EXTRA_TEXT2, accountNumberTXT);
        startActivity(intent);
    }

    public void checkBalance() {
        Intent intent2 = getIntent();
        String accountNumberTXT = intent2.getStringExtra(customer_login_page.EXTRA_TEXT);
        Intent intent = new Intent(this, checkBalance_activity.class);
        intent.putExtra(EXTRA_TEXT2, accountNumberTXT);
        startActivity(intent);
    }
}