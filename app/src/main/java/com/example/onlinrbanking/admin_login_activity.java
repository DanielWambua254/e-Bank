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

public class admin_login_activity extends AppCompatActivity {

    Button adminLoginBtn;
    EditText userName;
    EditText password;
    Dialog dialog;
    DBHelper DB;

    public  static  final String EXTRA_TEXT  = "com.example.onlineBanking.EXTRA_TEXT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        dialog = new Dialog(this);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.admin_password);


        adminLoginBtn = findViewById(R.id.admin_login_btn);
        DB = new DBHelper(this);
        adminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameTXT = userName.getText().toString();
                String passwordTXT = password.getText().toString();
                 //checks customer credentials
                if (userNameTXT.equals("") || passwordTXT.equals("")) {
                    Toast.makeText(admin_login_activity.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();

                } else if (userNameTXT.equals("admin") && passwordTXT.equals("admin")){
                    adminLogin();
                } else {
                    Boolean checkAdminPass = DB.authenticateAdmin(passwordTXT, userNameTXT);
                    if (checkAdminPass) {
                        adminLogin();
                    }
                    else {
                        invalidCredentialsDialog();
                    }
                }
                userName.setText("");
                password.setText("");
            }
        });
    }

    public  void adminLogin() {
        Intent intent = new Intent(this, admin_work_space.class);
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
}