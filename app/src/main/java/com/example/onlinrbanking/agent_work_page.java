package com.example.onlinrbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class agent_work_page extends AppCompatActivity {

    Button depositBtn;
    Button registerCustomerBtn;
    TextView agentName;
    DBHelper DB;

    public static final String AGENT_NAME2 = "com.example.onlineBanking.AGENT_NAME2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_work_page);

        agentName = findViewById(R.id.agentName);
        depositBtn = findViewById(R.id.deposit_btn);
        registerCustomerBtn = findViewById(R.id.Register_customer_btn);
        DB = new DBHelper(this);

        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depositMoney();
            }
        });

        registerCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewCustomer();
            }
        });

        Intent intent2 = getIntent();
        String accTXT2 = intent2.getStringExtra(agent_login_Activity.AGENT_NAME);
        Cursor res= DB.readAgentName(accTXT2);
        if (res.getCount()  == 0) {
            Toast.makeText(this, "brr", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append(res.getString(0)+"\n");
        }
        agentName.setText(buffer.toString());
    }

    public  void depositMoney() {
        Intent intent2 = getIntent();
        String accTXT2 = intent2.getStringExtra(agent_login_Activity.AGENT_NAME);
        Intent intent = new Intent(this, deposit.class);
        intent.putExtra(AGENT_NAME2, accTXT2);
        startActivity(intent);
    }

    public  void registerNewCustomer() {
        Intent intent = new Intent(this, customer_registration.class);
        startActivity(intent);
    }
}