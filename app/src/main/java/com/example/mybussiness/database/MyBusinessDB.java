package com.example.mybussiness.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mybussiness.model.User;
import com.example.mybussiness.provider.BillProvider;
import com.example.mybussiness.provider.UserProvider;

public class MyBusinessDB extends SQLiteOpenHelper {

    Context _context;
    private static final String DATABASENAME = "myBusiness.sql";
    private static final int VERSION = 1;

    public MyBusinessDB(@Nullable Context context) {
        super(context, DATABASENAME, null, VERSION);
        _context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserProvider.UserContract.CreateUserTable());
        db.execSQL(BillProvider.BillContract.CreateBillTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserProvider.UserContract.DestroyUserTable());
        db.execSQL(BillProvider.BillContract.DestroyBILLTable());
    }

}
