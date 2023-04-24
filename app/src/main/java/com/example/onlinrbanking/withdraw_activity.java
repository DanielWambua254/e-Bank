package com.example.onlinrbanking;

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

public class withdraw_activity extends AppCompatActivity {

    EditText withdrawAmount;
    EditText agentNumber;
    EditText pinEntry;
    TextView customName;
    Dialog dialog;
    MaterialButton sendWithdraw;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        dialog = new Dialog(this);
        withdrawAmount = findViewById(R.id.withdrawAmount);
        agentNumber = findViewById(R.id.agentNumber);
        pinEntry = findViewById(R.id.pin_entry);
        customName = findViewById(R.id.customerName);

        sendWithdraw = findViewById(R.id.sendWithdraw);
        DB = new DBHelper(this);

        Intent intent2 = getIntent();
        String accountNumberTXT = intent2.getStringExtra(customer_workspace_activity.EXTRA_TEXT2);

        sendWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountTXT = withdrawAmount.getText().toString();
                String agentNumberTXT  = agentNumber.getText().toString();
                String pinTXT = pinEntry.getText().toString();

                if (amountTXT.equals("") || agentNumberTXT.equals("") || pinTXT.equals("")) {
                    Toast.makeText(withdraw_activity.this, "Enter All the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCustomerPin = DB.checkUserPin(pinTXT);
                    if (checkCustomerPin) {
                        Boolean checkAgentNumber = DB.agentNumberExistance(agentNumberTXT);
                        if (checkAgentNumber) {
                            // read balance
                            Cursor customerBalance = DB.readBalance(accountNumberTXT);
                            if (customerBalance.getCount() ==0 ) {
                                Toast.makeText(withdraw_activity.this, "FAILED", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            StringBuilder stringBuilder = new StringBuilder();

                            while (customerBalance.moveToNext()) {
                                stringBuilder.append(customerBalance.getString(0));
                            }
                            float initialBL = Float.parseFloat(String.valueOf(stringBuilder));
                            float amountToReduce = Float.parseFloat(amountTXT);

                            if (initialBL < amountToReduce) {
                                insufficientFunds();
                            } else {
                                float newBalance = initialBL - amountToReduce;
                                String newBalanceTXT = String.valueOf(newBalance);


                                Boolean updateBalance2 = DB.updateBalance(accountNumberTXT,newBalanceTXT);
                                if (updateBalance2) {
                                    Toast.makeText(withdraw_activity.this, "WITHDRAW SUCCESSFUL !", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    transactionSuccessful();
                                }
                            }


                        }else {
                            wrongAgent();
                        }
                    } else {
                        wrongPin();
                    }
                    withdrawAmount.setText("");
                    agentNumber.setText("");
                    pinEntry.setText("");
                }

            }
        });


        Cursor res= DB.readCustomerName(accountNumberTXT);
        if (res.getCount()  == 0) {
            Toast.makeText(this, "brr", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append(res.getString(0)+"\n");
        }
        customName.setText(buffer.toString());
    }

    public  void transactionSuccessful() {
        dialog.setContentView(R.layout.send_successful);
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

    public  void insufficientFunds() {
        dialog.setContentView(R.layout.insufficient_funds);
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

    public  void wrongPin() {
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

    public  void wrongAgent() {
        dialog.setContentView(R.layout.agent_does_not_exist);
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