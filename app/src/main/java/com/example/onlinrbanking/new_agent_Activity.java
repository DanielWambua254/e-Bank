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

public class new_agent_Activity extends AppCompatActivity {
    EditText agentName;
    EditText agentNumber;
    EditText agentPin1;
    EditText agentPin2;
    Dialog dialog;
    Button register;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_agent);
        dialog = new Dialog(this);
        agentName = findViewById(R.id.agent_name);
        agentNumber = findViewById(R.id.agent_number);
        agentPin1 = findViewById(R.id.agent_pin_1);
        agentPin2 = findViewById(R.id.agent_pin_2);

        register = findViewById(R.id.agent_register_btn);

        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String agentNameTXT = agentName.getText().toString();
                String agentNumberTXT = agentNumber.getText().toString();
                String pin1TXT = agentPin1.getText().toString();
                String pin2TXT = agentPin2.getText().toString();

                if (agentNameTXT.equals("") || agentNumberTXT.equals("") || pin1TXT.equals("") || pin2TXT.equals(""))
                    Toast.makeText(new_agent_Activity.this, "Please enter all the fields !", Toast.LENGTH_SHORT).show();
                else {
                    if (pin1TXT.equals(pin2TXT)) {
                        Boolean checkAgentExists = DB.CheckAgent(agentNameTXT);
                        if (!checkAgentExists) {
                            Boolean insert = DB.newAgent(agentNameTXT, agentNumberTXT, pin1TXT);
                            if (insert)
                                agentRegistration();
                            else {
                                agentAlreadyExists();
                            }
                        }else {
                            Toast.makeText(new_agent_Activity.this, "AGENT REGISTRATION FAILED.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        wrongPins();
                    }
                }
            }
        });
    }
    public  void agentRegistration() {
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
    public  void agentAlreadyExists() {
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