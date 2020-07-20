package com.example.mybussiness.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mybussiness.database.MyBusinessDB;

public class UserProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.mybussiness";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);


    public static final int USERS = 0;
    public static final int USER = 1;

    public static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, UserContract.PATH, USERS);
        uriMatcher.addURI(AUTHORITY, UserContract.PATH + "/#", USER);
    }


    Context _context;
    MyBusinessDB _myBusineessDB;
    SQLiteDatabase db;

    public static class UserContract implements BaseColumns {

        static final String PATH = "user";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        public static final String _tableName = "User";
        public static final String _ID = "UserId";
        public static final String _name = "Name";
        public static final String _balance = "Balance";
        public static final String _image = "Photo";

        public static String CreateUserTable() {
            return "CREATE TABLE " + _tableName + " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + _name + " VARCHAR(80) NOT NULL, "
                    + _balance + " FLOAT NOT NULL,"
                    + _image + " BLOB"
                    + ");";
        }

        public static String DestroyUserTable() {
            return "DROP TABLE IF EXIST " + _tableName + ";";
        }

    }

    @Override
    public boolean onCreate() {
        _myBusineessDB = new MyBusinessDB(getContext());
        db = _myBusineessDB.getWritableDatabase();
        return (db != null);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case USERS:
                cursor = db.query(UserContract._tableName, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case USER:
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                qb.setTables(UserContract._tableName);
                long id = ContentUris.parseId(uri);
                qb.appendWhere(UserContract._ID + "=" + id);
                cursor = qb.query(db, projection, selection, selectionArgs, null, null, null);
                cursor.setNotificationUri(_context.getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("URI NOT FOUND");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case USERS:
                return "vnd." + AUTHORITY + "." + "Cursor.dir" + "/Users";
            case USER:
                return "vnd." + AUTHORITY + "." + "Cursor.item" + "/User";
            default:
                throw new IllegalArgumentException("Erro Uri:" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case USERS:
                long i = db.insert(UserContract._tableName, null, values);
                if (i > 0) {
                    Uri _uri = ContentUris.withAppendedId(UserContract.CONTENT_URI, i);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            default:
                throw new SQLException("Error on Insert");
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case USERS:
                count = db.delete(UserContract._tableName, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Error uri:" + uri);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case USERS:
                count = db.update(UserContract._tableName, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri Erro: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}
