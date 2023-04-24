package com.example.onlinrbanking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class deposit extends AppCompatActivity {
    EditText accountNumber;
    EditText amount;
    EditText pin;
    TextView agentNameTXT;
    DBHelper DB;
    Dialog dialog;
    MaterialButton sendDeposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        dialog = new Dialog(this);
        accountNumber = findViewById(R.id.deposit_Account_number);
        amount = findViewById(R.id.deposit_amount);
        pin = findViewById(R.id.agent_pin_entry);
        agentNameTXT = findViewById(R.id.agentName);
        sendDeposit = findViewById(R.id.sendDeposit);
        DB = new DBHelper(this);

        Intent intent2 = getIntent();
        String agentName = intent2.getStringExtra(agent_work_page.AGENT_NAME2);

        sendDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountNumberTXT = accountNumber.getText().toString();
                String amountTXT = amount.getText().toString();
                String pinTXT = pin.getText().toString();

                if (accountNumberTXT.equals("") || amountTXT.equals("") || pinTXT.equals("")) {
                    Toast.makeText(deposit.this, "PLEASE ENTER ALL THE FIELDS!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkPin = DB.authenticateDeposit(pinTXT);
                    if (checkPin) {
                        Boolean checkAccount = DB.accountExistance(accountNumberTXT);
                        if (checkAccount) {
                            //read balance
                            Cursor theBalance = DB.readBalance(accountNumberTXT);
                            if (theBalance.getCount() == 0) {
                                Toast.makeText(deposit.this, "Failed!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            StringBuilder stringBuilder = new StringBuilder();

                            while (theBalance.moveToNext()) {
                                stringBuilder.append(theBalance.getString(0));
                            }
                            float initialBalance = Float.parseFloat(String.valueOf(stringBuilder));
                            float amountToAdd = Float.parseFloat(amountTXT);
                            float newBalance = initialBalance + amountToAdd;
                            String newBalanceTXT  = String.valueOf(newBalance);


                            Boolean updateBalance1 = DB.updateBalance(accountNumberTXT, newBalanceTXT);
                            if (updateBalance1) {
                                Toast.makeText(deposit.this, "DEPOSIT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                depositSuccessful();
                            }



                        } else {
                            wrongAccount();
                        }
                    } else {
                        wrongPin2();
                    }
                }

            }
        });

       Cursor res= DB.readAgentName(agentName);
        if (res.getCount()  == 0) {
            Toast.makeText(this, "brr", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append(res.getString(0));
        }
        agentNameTXT.setText(buffer.toString());
    }

    public  void depositSuccessful() {
        dialog.setContentView(R.layout.deposit_successful);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView = dialog.findViewById(R.id.imageViewClose);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public  void wrongPin2() {
        dialog.setContentView(R.layout.wrong_pin);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView = dialog.findViewById(R.id.imageViewClose);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public  void wrongAccount() {
        dialog.setContentView(R.layout.account_does_not_exist);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView = dialog.findViewById(R.id.imageViewClose);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}