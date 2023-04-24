package com.example.onlinrbanking;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button loginAsCustomerBTN;
    Button loginAsAgentBtn;
    Button loginAsAdminBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginAsCustomerBTN = findViewById(R.id.login_as_customerBTN);
        loginAsAgentBtn = findViewById(R.id.login_as_agentBTN);
        loginAsAdminBTN = findViewById(R.id.login_as_adminBTN);

        loginAsCustomerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomerLoginForm();
            }
        });

        loginAsAgentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgentLoginForm();
            }
        });

        loginAsAdminBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminLoginForm();
            }
        });

    }

    public void openCustomerLoginForm() {
        Intent intent = new Intent(this,customer_login_page.class);
        startActivity(intent);
    }

    public void openAgentLoginForm() {
        Intent intent = new Intent(this,agent_login_Activity.class);
        startActivity(intent);
    }
    public void openAdminLoginForm() {
        Intent intent = new Intent(this,admin_login_activity.class);
        startActivity(intent);
    }
}
