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

public class new_admin_activity extends AppCompatActivity {
    Button registerAdmin;
    EditText adminName;
    EditText pass1;
    EditText pass2;
    Dialog dialog;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin);
        dialog = new Dialog(this);
        registerAdmin = findViewById(R.id.admin_register_btn);
        adminName = findViewById(R.id.admin_userName);
        pass1 = findViewById(R.id.admin_password_1);
        pass2 = findViewById(R.id.admin_password_2);
        DB = new DBHelper(this);

        registerAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminTXT = adminName.getText().toString();
                String passwordTXT1 = pass1.getText().toString();
                String passwordTXT2 = pass2.getText().toString();

                if (adminTXT.equals("") || passwordTXT1.equals("") || passwordTXT2.equals(""))
                    Toast.makeText(new_admin_activity.this, "Please Enter All The fields !", Toast.LENGTH_SHORT).show();
                else {
                    if (passwordTXT1.equals(passwordTXT2)) {
                        Boolean checkAdminExists = DB.checkAdmin(passwordTXT1);
                        if (!checkAdminExists) {
                            Boolean insert = DB.newAdmin(passwordTXT1, adminTXT);
                            if (insert) {
                                adminRegistrerd();
                            }
                            else {
                                Toast.makeText(new_admin_activity.this, "Failed !",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            alreadyExists();
                        }
                    } else {
                        wrongPins();
                    }

                }
            }
        });
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

    public  void adminRegistrerd() {
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