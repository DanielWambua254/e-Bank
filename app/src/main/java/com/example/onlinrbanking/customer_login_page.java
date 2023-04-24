package com.example.onlinrbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class customer_login_page extends AppCompatActivity {
    EditText accountNumber;
    EditText pin;
    Dialog dialog;
    Button customerLoginBTN;
    Button customerRegisterBtn;
    DBHelper DB;

    public  static final String EXTRA_TEXT = "com.example.onlineBanking.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_page);
        dialog = new Dialog(this);
        accountNumber = findViewById(R.id.customer_acc_no);
        pin = findViewById(R.id.customer_pin_no);

        customerLoginBTN = findViewById(R.id.customer_login_btn);
        customerRegisterBtn = findViewById(R.id.customer_login_register_btn);

        DB = new DBHelper(this);

        customerLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountNumberTXT = accountNumber.getText().toString();
                String pinTXT = pin.getText().toString();

                // this code checks customer login credentials
                if  (accountNumberTXT.equals("") || pinTXT.equals("")) {
                    Toast.makeText(customer_login_page.this, "Please Enter all the fields !", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkUserPass = DB.authenticateUser(accountNumberTXT, pinTXT);//checks info from database
                    if (checkUserPass) {
                        customerLogin();// logs in customer

                    }
                    else {
                        invalidCredentialsDialog();// displays error message
                    }

                }
                accountNumber.setText("");
                pin.setText("");
            }
        });

        customerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCustomer();
            }
        });

    }

    public void customerLogin() {
        String accountNumberTXT = accountNumber.getText().toString();
        Intent intent = new Intent(this,customer_workspace_activity.class);
        // passing this data to next activity
        intent.putExtra(EXTRA_TEXT, accountNumberTXT);
        startActivity(intent);
    }

    public  void invalidCredentialsDialog() {
        dialog.setContentView(R.layout.invalid_credentials);
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

    public void registerCustomer() {
            Intent intent = new Intent(this, customer_registration.class);
            startActivity(intent);

    }

}