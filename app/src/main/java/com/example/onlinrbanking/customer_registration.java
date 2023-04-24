package com.example.onlinrbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class  customer_registration extends AppCompatActivity {
    Button registerCustomer;
    EditText customerName;
    EditText accountNumber;
    EditText pin;
    EditText retypePin;
    Dialog dialog;
    float balance =0;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        dialog = new Dialog(this);
        registerCustomer = findViewById(R.id.customer_register_btn);

        customerName = findViewById(R.id.customer_name);
        accountNumber = findViewById(R.id.customer_account);
        pin = findViewById(R.id.customer_pin_1);
        retypePin = findViewById(R.id.customer_pin_2);

        DB = new DBHelper(this);

        registerCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerNameTXT = customerName.getText().toString();
                String accountNumberTXT = accountNumber.getText().toString();
                String pinTXT = pin.getText().toString();
                String retypePinTXT = retypePin.getText().toString();
                String balanceTXT = String.valueOf(balance);


                if (customerNameTXT.equals("") || accountNumberTXT.equals("") || pinTXT.equals("") || retypePinTXT.equals("") )
                    Toast.makeText(customer_registration.this, "PLEASE ENTER ALL THE FIELDS !", Toast.LENGTH_SHORT).show();
                else {
                    if (pinTXT.equals(retypePinTXT)){
                        Boolean checkUserExists = DB.CheckUser(accountNumberTXT);
                        if (!checkUserExists) {
                            Boolean insert = DB.newCustomer(customerNameTXT,accountNumberTXT, pinTXT, balanceTXT);
                            if (insert) {
                                customerRegistration();
                            }
                            else {
                                Toast.makeText(customer_registration.this, "CUSTOMER REGISTRATION FAILED.", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            alreadyExists();
                        }
                    }else {
                        wrongPins();
                    }

                }

            }
        });
    }

    public  void customerRegistration() {
        dialog.setContentView(R.layout.successful_customer_registration);
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

    public  void alreadyExists() {
        dialog.setContentView(R.layout.agent_already_exists);
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

    public  void wrongPins() {
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