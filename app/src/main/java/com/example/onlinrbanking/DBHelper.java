package com.example.onlinrbanking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper( Context context) {
        super(context, "bankDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create TABLE customerDetails (name TEXT, accountNumber TEXT primary key, pin TEXT, balance TEXT )");
        DB.execSQL("create TABLE agentTable (agentNumber TEXT primary key,agentName TEXT, agentPin TEXT )");
        DB.execSQL("create TABLE adminTable (adminPassword TEXT primary key, adminUserName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop TABLE if exists customerDetails");
        DB.execSQL("drop TABLE if exists agentTable ");
        DB.execSQL("drop TABLE if exists adminTable ");
        onCreate(DB);
    }

    //this method is for registering new customer
    public Boolean newCustomer (String name, String accountNumber, String pin, String balance) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("accountNumber", accountNumber);
        contentValues.put("pin", pin);
        contentValues.put("balance", balance);

        Long result = DB.insert("customerDetails", null, contentValues);

        return result != -1;
    }

    //this method is for deleting existing customer
    public Boolean deleteCustomer (String accountNumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from customerDetails where accountNumber = ?", new String[]{accountNumber});

        if (cursor.getCount() > 0) {
            int result = DB.delete("customerDetails", "accountNumber = ?", new  String[] {accountNumber});

            return result != -1;
        }else {
            return false;
        }
    }

    //checks if customer exists in the database
    public Boolean CheckUser(String accountNumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from customerDetails where accountNumber = ?",new  String[] {accountNumber});
        return cursor.getCount() > 0;
    }

    //used to authenticate customers
    public Boolean authenticateUser(String accountNumber, String pin) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from customerDetails where accountNumber = ? and pin = ?", new String[] {accountNumber, pin});
        return cursor.getCount() > 0;
    }



    //this method is for registering new agents
    public Boolean newAgent (String agentName, String agentNumber, String agentPin) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("agentName", agentName);
        contentValues.put("agentNumber", agentNumber);
        contentValues.put("agentPin", agentPin);

        Long result = DB.insert("agentTable", null, contentValues);

        return result != -1;
    }

    // adding new admin
    public Boolean newAdmin (String adminPassword , String adminUserName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminPassword",adminPassword );
        contentValues.put("adminUserName", adminUserName);

        Long result = DB.insert("adminTable", null, contentValues);

        return result != -1;
    }

    //this method is for deleting existing agent
    public Boolean deleteAgent (String agentNumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from agentTable where agentNumber = ?", new String[]{agentNumber});

        if (cursor.getCount() > 0) {
            int result = DB.delete("agentTable", "agentNumber= ?", new  String[] {agentNumber});

            return result != -1;
        }else {
            return false;
        }
    }

    //checks if agent exists in the database
    public Boolean CheckAgent (String agentNumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from agentTable where agentNumber = ?",new  String[] {agentNumber});
        return cursor.getCount() > 0;
    }

    //used to authenticate agents
    public Boolean authenticateAgent (String agentNumber, String agentPin) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from agentTable where agentNumber = ? and agentPin = ?", new String[] {agentNumber, agentPin});
        return cursor.getCount() > 0;
    }

    //used by customer to check balance
    public Cursor checkBalance ( String pin) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select balance from customerDetails where  pin = ?", new String[]{ pin});
        return cursor;
    }

    //checks if account exists in the database
    public Boolean accountExistance (String accountNumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select accountNumber from customerDetails where accountNumber = ?",new  String[] {accountNumber});
        return cursor.getCount() > 0;
    }

    //used to authenticate  pin for deposit
    public Boolean authenticateDeposit ( String agentPin) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from agentTable where agentPin = ?", new String[] { agentPin});
        return cursor.getCount() > 0;
    }

    //used by customer to read balance
    public Cursor readBalance ( String accountNumber) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select balance from customerDetails where  accountNumber = ?", new String[]{ accountNumber});
        return cursor;
    }

    //this method is for updating  customer balance
    public Boolean updateBalance (String accountNumber, String balance ) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("balance", balance);
        Cursor cursor = DB.rawQuery("select balance from customerDetails where accountNumber = ?",
                new String[] {accountNumber});

        if (cursor.getCount() > 0) {
            Long result = Long.valueOf(DB.update("customerDetails", contentValues,
                    "accountNumber = ?", new String[] {accountNumber}));

            return result != 1;
        }
        else {
            return false;
        }
    }

    //used to check user pin
    public Boolean checkUserPin ( String pin) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select pin from customerDetails where pin = ?", new String[] { pin});
        return cursor.getCount() > 0;
    }

    //check if agent number exists in the database
    public Boolean agentNumberExistance (String agentNumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select agentNumber from agentTable where agentNumber = ?",new  String[] {agentNumber});
        return cursor.getCount() > 0;
    }

    public Cursor getCustomers() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from customerDetails  ", null);
        return cursor;
    }

    public Cursor getAgents() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from agentTable  ", null);
        return cursor;
    }

    public Cursor readCustomerName ( String accountNumber) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select name from customerDetails where  accountNumber = ?", new String[]{ accountNumber});
        return cursor;
    }

    public Cursor readAgentName ( String agentNumber) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select agentName from agentTable where  agentNumber = ?", new String[]{ agentNumber});
        return cursor;
    }



    //used to authenticate admins
    public Boolean authenticateAdmin(String adminPassword , String adminUserName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from adminTable where adminPassword = ? and adminUserName = ?", new String[] {adminPassword, adminUserName});
        return cursor.getCount() > 0;
    }

    //checks if customer exists in the database
    public Boolean checkAdmin(String adminPassword) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from adminTable where adminPassword = ?",new  String[] {adminPassword});
        return cursor.getCount() > 0;
    }

    public Cursor getAdmins() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from  adminTable ", null);
        return cursor;
    }

}
