package com.example.mybussiness.DAO;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.mybussiness.database.MyBusinessDB;
import com.example.mybussiness.model.User;
import com.example.mybussiness.provider.UserProvider;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private MyBusinessDB myBusinessDB;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        myBusinessDB = new MyBusinessDB(context);
        db = myBusinessDB.getWritableDatabase();
    }

    public long InsertUser(User user) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        ContentValues cv = new ContentValues();

        cv.put(UserProvider.UserContract._balance, user.get_balance());
        cv.put(UserProvider.UserContract._name, user.get_name());
        cv.put(UserProvider.UserContract._image, user.getPhoto());

        long i = db.insert(UserProvider.UserContract._tableName, null, cv);
        return i;
    }

    public void UpdateUserBalance(long userId, float amount) {
        db.execSQL("update "
                + UserProvider.UserContract._tableName
                + " SET "
                + UserProvider.UserContract._balance
                + " = " + amount
                + " WHERE " + UserProvider.UserContract._ID + "= " + userId);
    }

    public List<User> GetUser() {
        List<User> users = new ArrayList<>();

        Cursor cursor = db.query(UserProvider.UserContract._tableName,
                new String[]{UserProvider.UserContract._ID, UserProvider.UserContract._name, UserProvider.UserContract._balance, UserProvider.UserContract._image},
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            User u = new User();
            u.set_iD(cursor.getInt(0));
            u.set_name(cursor.getString(1));
            u.set_balance(cursor.getFloat(2));
            u.setPhoto(cursor.getBlob(3));
            users.add(u);
        }

        return users;
    }

}
