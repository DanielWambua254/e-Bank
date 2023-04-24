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

public class delete_customer_activity extends AppCompatActivity {
    Button deleteCustomer;
    EditText accountNumber;
    Dialog dialog;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_customer);

        deleteCustomer = findViewById(R.id.customer_delete_btn);
        accountNumber = findViewById(R.id.delete_customer_account);
        DB = new DBHelper(this);
        dialog = new Dialog(this);

        deleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountTXT = accountNumber.getText().toString();
                Boolean deleteCustomer = DB.deleteCustomer(accountTXT);
                if (deleteCustomer)
                    customerDeleted();
                else
                    customerDeletionFailed();
            }
        });
    }

    public  void customerDeleted() {
        dialog.setContentView(R.layout.customer_deleted);
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

    public  void customerDeletionFailed() {
        dialog.setContentView(R.layout.customer_deletion_failed);
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