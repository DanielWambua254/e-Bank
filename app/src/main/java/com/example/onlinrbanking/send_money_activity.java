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

public class send_money_activity extends AppCompatActivity {
    EditText sendAmount;
    EditText receiver_acc_number;
    Dialog dialog;
    EditText pin_send_number;
    TextView customersName;

    MaterialButton send_send_money;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        dialog = new Dialog(this);
        sendAmount = findViewById(R.id.sendAmount);
        receiver_acc_number = findViewById(R.id.receiver_acc_Number);
        pin_send_number = findViewById(R.id.pin_send_Number);
        customersName = findViewById(R.id.customerName);

        send_send_money = findViewById(R.id.send_send_money);
        DB = new DBHelper(this);

        Intent intent2 = getIntent();
        String accountNumberTXT = intent2.getStringExtra(customer_workspace_activity.EXTRA_TEXT2);

        send_send_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendAmountTXT = sendAmount.getText().toString();
                String receiverAccountNumberTXT = receiver_acc_number.getText().toString();
                String pinSendTXT = pin_send_number.getText().toString();

                if (sendAmountTXT.equals("") || receiverAccountNumberTXT.equals("") || pinSendTXT.equals("")) {
                    Toast.makeText(send_money_activity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCustomerPin2 = DB.checkUserPin(pinSendTXT);
                    if (checkCustomerPin2) {
                        Boolean checkReceiverAccount = DB.accountExistance(receiverAccountNumberTXT);
                        if (checkReceiverAccount) {
                            // read customer balance
                            Cursor customerBalance = DB.readBalance(accountNumberTXT);
                            if (customerBalance.getCount() == 0) {
                                Toast.makeText(send_money_activity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            StringBuilder readCustomerBalance = new StringBuilder();
                            while (customerBalance.moveToNext()) {
                                readCustomerBalance.append(customerBalance.getString(0));
                            }
                            float customerInitialBL = Float.parseFloat(String.valueOf(readCustomerBalance));

                            // read receiver's balance
                            Cursor receiverBalance = DB.readBalance(receiverAccountNumberTXT);
                            if (receiverBalance.getCount() == 0) {
                                Toast.makeText(send_money_activity.this, "Failed !", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            StringBuilder readReceiverBalance = new StringBuilder();
                            while (receiverBalance.moveToNext()) {
                                readReceiverBalance.append(receiverBalance.getString(0));
                            }
                            float receiverInitialBL = Float.parseFloat(String.valueOf(readReceiverBalance));


                            // doing calculations
                            float amountToSend = Float.parseFloat(sendAmountTXT);
                            if (customerInitialBL < amountToSend){
                                insufficientFunds();
                            } else {
                                float newCustomerBalance = customerInitialBL - amountToSend;
                                float newReceiverBalance = receiverInitialBL + amountToSend;

                                String newCustomerBalanceTXT = String.valueOf(newCustomerBalance);
                                String newReceiverBalanceTXT = String.valueOf(newReceiverBalance);

                                // update database
                                Boolean updateCustomerBalance = DB.updateBalance(accountNumberTXT, newCustomerBalanceTXT);
                                if (updateCustomerBalance) {
                                    Toast.makeText(send_money_activity.this, "Money send successfully.", Toast.LENGTH_SHORT).show();
                                } else {
                                    transactionSuccessful();
                                     }
                                // update receivers balance
                                Boolean updateReceiverBalance = DB.updateBalance(receiverAccountNumberTXT,newReceiverBalanceTXT);
                                if (updateReceiverBalance) {
                                    Toast.makeText(send_money_activity.this, " ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(send_money_activity.this, " ", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            wrongAcc();
                        }
                    } else {
                        wrongPin();
                    }
                }
                sendAmount.setText("");
                receiver_acc_number.setText("");
                pin_send_number.setText("");

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
        customersName.setText(buffer.toString());
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

    public  void wrongAcc() {
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