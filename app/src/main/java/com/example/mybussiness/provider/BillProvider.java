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

public class BillProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.mybussiness";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    private static final String DATABASENAME = "myBusiness.sql";


    public static final int BILLS = 0;
    public static final int BILL = 1;

    public static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, UserProvider.UserContract.PATH, BILLS);
        uriMatcher.addURI(AUTHORITY, UserProvider.UserContract.PATH + "/#", BILL);
    }


    Context _context;
    MyBusinessDB _myBusineessDB;
    SQLiteDatabase db;

    public static class BillContract implements BaseColumns {

        static final String PATH = "bill";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        public static final String _tableName = "Bill";
        public static final String _iD = "BillId";
        public static final String _name = "Name";
        public static final String _description = "Description";
        public static final String _amount = "Amount";
        public static final String _expirationDate = "ExpirationDate";
        public static final String _repeat = "Repeat";
        public static final String _userId = "UserId";

        public static String CreateBillTable() {
            return "CREATE TABLE " + _tableName + " ( " + _iD + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + _name + " VARCHAR(80) NOT NULL, "
                    + _amount + " FLOAT NOT NULL,"
                    + _description + " VARCHAR(200) NOT NULL,"
                    + _expirationDate + " DATETIME NOT NULL,"
                    + _repeat + " BOOLEAN NOT NULL,"
                    + _userId + " INTEGER NOT NULL,"
                    + " FOREIGN KEY (" + _userId + ") REFERENCES " + UserProvider.UserContract._tableName + "(" + UserProvider.UserContract._ID + ")"
                    + ");";
        }

        public static String DestroyBILLTable() {
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
            case BILLS:
                cursor = db.query(BillProvider.BillContract._tableName, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case BILL:
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                qb.setTables(UserProvider.UserContract._tableName);
                long id = ContentUris.parseId(uri);
                qb.appendWhere(UserProvider.UserContract._ID + "=" + id);
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
            case BILLS:
                return "vnd." + AUTHORITY + "." + "Cursor.dir" + "/Users";
            case BILL:
                return "vnd." + AUTHORITY + "." + "Cursor.item" + "/User";
            default:
                throw new IllegalArgumentException("Erro Uri:" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case BILLS:
                long i = db.insert(UserProvider.UserContract._tableName, null, values);
                if (i > 0) {
                    Uri _uri = ContentUris.withAppendedId(UserProvider.UserContract.CONTENT_URI, i);
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
            case BILLS:
                count = db.delete(BillProvider.BillContract._tableName, selection, selectionArgs);
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
            case BILLS:
                count = db.update(BillProvider.BillContract._tableName, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri Erro: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
