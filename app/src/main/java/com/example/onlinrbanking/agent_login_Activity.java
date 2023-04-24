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

public class agent_login_Activity extends AppCompatActivity {
    EditText agentNumber;
    EditText agentPin;
    Button agentLoginBtn;
    Button agent_register;
    Dialog dialog;

    DBHelper DB;
    public static final String AGENT_NAME = "com.example.onlineBanking.AGENT_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_login);
        dialog = new Dialog(this);

        agentNumber = findViewById(R.id.agent_no);
        agentPin = findViewById(R.id.agent_pin_no);

        agentLoginBtn = findViewById(R.id.agent_login_btn);
        agent_register= findViewById(R.id.agent_loginRegisterBTN);

        DB = new DBHelper(this);

        agentLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String agentNoTXT = agentNumber.getText().toString();
                String agentPinTXT = agentPin.getText().toString();

                // this code checks agent login credentials
                if (agentNoTXT.equals("") || agentPinTXT.equals("")) {
                    Toast.makeText(agent_login_Activity.this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
                } else  {
                    Boolean authenticateAgent = DB.authenticateAgent(agentNoTXT,agentPinTXT);//checks info from database
                    if (authenticateAgent) {
                        agentLogin();// logs in agent
                    }
                    else
                        invalidCredentialsDialog();// displays error message when details are invalid
                }
                agentNumber.setText("");
                agentPin.setText("");
            }
        });

        agent_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAgent();
            }
        });
    }

    public  void agentLogin() {
        String agentNoTXT = agentNumber.getText().toString();
        Intent intent = new Intent(this, agent_work_page.class);
        // passing data
        intent.putExtra(AGENT_NAME,agentNoTXT);
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

    public void registerAgent() {
        Intent intent = new Intent(this, new_agent_Activity.class);
        startActivity(intent);
    }

}