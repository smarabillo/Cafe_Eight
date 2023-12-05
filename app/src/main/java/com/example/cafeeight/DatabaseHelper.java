package com.example.cafeeight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CafeEightDB";
    private static final int DATABASE_VERSION = 1;

    // Users table properties
    private static final String TABLE_USERS = "Users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String createUsersTableQuery = "CREATE TABLE " + TABLE_USERS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_PASSWORD + " TEXT)";
        db.execSQL(createUsersTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop Users table on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Users methods

    public void insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_PASSWORD, password);

        try {
            db.insertOrThrow(TABLE_USERS, null, contentValues);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting user data: " + e.getMessage());
        } finally {
            closeDatabase(db);
        }
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ?", new String[]{email});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ? AND " + KEY_PASSWORD + " = ?", new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    private void closeDatabase(SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private void closeStatement(SQLiteStatement statement) {
        if (statement != null) {
            statement.close();
        }
    }
}
