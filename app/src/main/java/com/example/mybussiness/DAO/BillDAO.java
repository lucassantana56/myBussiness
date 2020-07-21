package com.example.mybussiness.DAO;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.mybussiness.database.MyBusinessDB;
import com.example.mybussiness.model.Bill;
import com.example.mybussiness.model.User;
import com.example.mybussiness.provider.BillProvider;
import com.example.mybussiness.provider.UserProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private MyBusinessDB myBusinessDB;
    private SQLiteDatabase db;

    public BillDAO(Context context) {
        myBusinessDB = new MyBusinessDB(context);
        db = myBusinessDB.getWritableDatabase();
    }


    public void UpdateBill(Bill bill) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Bundle bundle = new Bundle();
        bundle.putParcelable("bill", bill);

        ContentValues cv = new ContentValues();

        cv.put(BillProvider.BillContract._amount, bill.get_amount());
        cv.put(BillProvider.BillContract._description, bill.get_description());
        cv.put(BillProvider.BillContract._expirationDate, dateFormat.format(bill.get_expirationDate()));
        cv.put(BillProvider.BillContract._name, bill.get_name());
        cv.put(BillProvider.BillContract._repeat, bill.get_repeat());
        cv.put(BillProvider.BillContract._userId, bill.get_userId());

        db.update(BillProvider.BillContract._tableName, cv, BillProvider.BillContract._iD + "=?", new String[]{String.valueOf(bill.get_iD())});
    }

    public long InsertBill(Bill bill) {

        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Bundle bundle = new Bundle();
        bundle.putParcelable("bill", bill);

        ContentValues cv = new ContentValues();

        cv.put(BillProvider.BillContract._amount, bill.get_amount());
        cv.put(BillProvider.BillContract._description, bill.get_description());
        cv.put(BillProvider.BillContract._expirationDate, dateFormat.format(bill.get_expirationDate()));
        cv.put(BillProvider.BillContract._name, bill.get_name());
        cv.put(BillProvider.BillContract._repeat, bill.get_repeat());
        cv.put(BillProvider.BillContract._userId, bill.get_userId());

        long i = db.insert(BillProvider.BillContract._tableName, null, cv);

        return i;
    }

    public void DeleteBill(long billId) {
        db.delete(BillProvider.BillContract._tableName, BillProvider.BillContract._iD + "=?", new String[]{String.valueOf(billId)});
    }

    public List<Bill> getBills(long userId) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Bill> bills = new ArrayList<>();

        Cursor cursor = db.query(BillProvider.BillContract._tableName,
                new String[]{BillProvider.BillContract._iD,
                        BillProvider.BillContract._name,
                        BillProvider.BillContract._description,
                        BillProvider.BillContract._expirationDate,
                        BillProvider.BillContract._amount,
                        BillProvider.BillContract._repeat},
                BillProvider.BillContract._userId + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            Bill b = new Bill();
            b.set_iD(cursor.getInt(cursor.getColumnIndexOrThrow(BillProvider.BillContract._iD)));
            b.set_amount(cursor.getFloat(cursor.getColumnIndexOrThrow(BillProvider.BillContract._amount)));
            b.set_description(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._description)));
            b.set_name(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._name)));
            b.set_expirationDate(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._expirationDate))));
            b.set_repeat(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._repeat))));
            bills.add(b);
        }

        return bills;
    }


    public Bill getBill(long billId) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Cursor cursor = db.query(BillProvider.BillContract._tableName,
                new String[]{BillProvider.BillContract._iD,
                        BillProvider.BillContract._name,
                        BillProvider.BillContract._description,
                        BillProvider.BillContract._expirationDate,
                        BillProvider.BillContract._amount,
                        BillProvider.BillContract._repeat},
                BillProvider.BillContract._iD + "=?",
                new String[]{String.valueOf(billId)},
                null,
                null,
                null,
                null);


        cursor.moveToNext();

        Bill b = new Bill();
        b.set_iD(cursor.getInt(cursor.getColumnIndexOrThrow(BillProvider.BillContract._iD)));
        b.set_amount(cursor.getFloat(cursor.getColumnIndexOrThrow(BillProvider.BillContract._amount)));
        b.set_description(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._description)));
        b.set_name(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._name)));
        b.set_expirationDate(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._expirationDate))));
        b.set_repeat(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(BillProvider.BillContract._repeat))));

        return b;
    }
}
