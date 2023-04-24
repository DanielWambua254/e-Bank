package com.example.onlinrbanking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class reports_activity extends AppCompatActivity {
    Button ourCustomers;
    Button ourAgents;
    Button admins;

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        DB = new DBHelper(this);

        ourCustomers = findViewById(R.id.number_of_customers_btn);
        ourAgents = findViewById(R.id.number_of_agents_btn);
        admins = findViewById(R.id.number_of_admins_btn);

        ourCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getCustomers();
                if (res.getCount() == 0) {
                    Toast.makeText(reports_activity.this, "Failed !", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("NAME:  "+res.getString(0)+"\n");
                    buffer.append("ACCOUNT NUMBER: "+res.getString(1)+"\n");
                    buffer.append(" \n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(reports_activity.this);
                builder.setCancelable(true);
                builder.setTitle("CUSTOMERS");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        ourAgents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getAgents();
                if (res.getCount() == 0) {
                    Toast.makeText(reports_activity.this, "Failed !", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("AGENT NUMBER:  "+res.getString(0)+"\n");
                    buffer.append("AGENT NAME: "+res.getString(1)+"\n");
                    buffer.append(" \n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(reports_activity.this);
                builder.setCancelable(true);
                builder.setTitle("AGENTS");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        admins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getAdmins();
                if (res.getCount() == 0) {
                    Toast.makeText(reports_activity.this, "Failed !", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("ADMIN NAME:  "+res.getString(1)+"\n");
                    buffer.append(" \n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(reports_activity.this);
                builder.setCancelable(true);
                builder.setTitle("ADMINISTRATORS");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}