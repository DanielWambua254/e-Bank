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

public class checkBalance_activity extends AppCompatActivity {
    EditText pinCheck_BL;
    MaterialButton send_check_balance;
    Dialog dialog;
    TextView customerNames;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        dialog = new Dialog(this);
        pinCheck_BL = findViewById(R.id.pin_checkBL);
        send_check_balance = findViewById(R.id.send_check_balance);
        customerNames = findViewById(R.id.customerName);

        Intent intent2 = getIntent();
        String accountNumberTXT = intent2.getStringExtra(customer_workspace_activity.EXTRA_TEXT2);

        DB = new DBHelper(this);

        send_check_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinTXT = pinCheck_BL.getText().toString();
                if (pinTXT.equals(""))
                    Toast.makeText(checkBalance_activity.this, "Please Enter your pin.", Toast.LENGTH_SHORT).show();
                else {
                    Cursor res = DB.checkBalance(pinTXT);
                    if (res.getCount() == 0) {
                        wrongPin();
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();

                    while (res.moveToNext()) {
                        buffer.append("YOUR BALANCE IS:  "+res.getString(0)+"\n");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(checkBalance_activity.this);
                    builder.setCancelable(true);
                    builder.setTitle("MY BALANCE");
                    builder.setMessage(buffer.toString());
                    builder.show();
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
        customerNames.setText(buffer.toString());
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
}