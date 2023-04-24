package com.example.onlinrbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class admin_work_space extends AppCompatActivity {

    Button registerNewCustomer2;
    Button deleteCustomerBtn;
    Button registerNewAgentBtn;
    Button deleteAgentBtn;
    Button newAdminBtn;
    Button reportsBtn;

    TextView adminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work_space);

        registerNewCustomer2 = findViewById(R.id.register_NewCustomer_btn);
        deleteCustomerBtn = findViewById(R.id.delete_customer_btn);
        registerNewAgentBtn = findViewById(R.id.register_newAgent_btn);
        deleteAgentBtn = findViewById(R.id.delete_agent_btn);
        newAdminBtn = findViewById(R.id.new_admin_btn);
        reportsBtn = findViewById(R.id.reports_btn);

        adminName = findViewById(R.id.admin_Name);

        registerNewCustomer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewCustomer();
            }
        });

        deleteCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomer();
            }
        });

        registerNewAgentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewAgent();
            }
        });

        deleteAgentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAgent();
            }
        });

        newAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAdmin();
            }
        });

        reportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reports();
            }
        });
    }

    public void registerNewCustomer() {
        Intent intent = new Intent(this,customer_registration.class);
        startActivity(intent);
    }

    public void deleteCustomer() {
        Intent intent = new Intent(this, delete_customer_activity.class);
        startActivity(intent);
    }

    public  void registerNewAgent() {
        Intent intent = new Intent(this, new_agent_Activity.class);
        startActivity(intent);
    }

    public void deleteAgent() {
        Intent intent = new Intent(this, delete_agent_activity.class);
        startActivity(intent);
    }

    public void newAdmin() {
        Intent intent = new Intent(this, new_admin_activity.class);
        startActivity(intent);
    }
    public void reports() {
        Intent intent = new Intent(this, reports_activity.class);
        startActivity(intent);
    }
}