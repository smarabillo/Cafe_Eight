package com.example.cafeeight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CafeEightDB";
    private static final int DATABASE_VERSION = 2; // Increment the version when you make changes

    // Users table properties
    private static final String TABLE_USERS = "Users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    // Orders table properties
    private static final String TABLE_ORDERS = "orders";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_TOTAL_AMOUNT = "total_amount";
    private static final String ORDER_TOTAL_ITEMS = "total_items";

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

        // Create Orders table
        String createOrdersTableQuery = "CREATE TABLE " + TABLE_ORDERS + "(" +
                ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ORDER_TOTAL_AMOUNT + " REAL, " +
                ORDER_TOTAL_ITEMS + " INTEGER)";
        db.execSQL(createOrdersTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop Users and Orders tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public static long insertOrder(Context context, double totalAmount, int totalItems) {
        SQLiteDatabase db = null;
        long orderId = -1;

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ORDER_TOTAL_AMOUNT, totalAmount);
            values.put(ORDER_TOTAL_ITEMS, totalItems);
            orderId = db.insert(TABLE_ORDERS, null, values);
        } catch (SQLiteException e) {
            Log.e("DatabaseHelper", "Error inserting order: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return orderId;
    }


    public Order getLatestOrder() {
        SQLiteDatabase db = null;
        Order latestOrder = null;

        try {
            db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_ORDERS + " ORDER BY " + ORDER_ID + " DESC LIMIT 1";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int orderIdIndex = cursor.getColumnIndex(ORDER_ID);
                int totalAmountIndex = cursor.getColumnIndex(ORDER_TOTAL_AMOUNT);
                int totalItemsIndex = cursor.getColumnIndex(ORDER_TOTAL_ITEMS);

                if (orderIdIndex != -1 && totalAmountIndex != -1 && totalItemsIndex != -1) {
                    int orderId = cursor.getInt(orderIdIndex);
                    double totalAmount = cursor.getDouble(totalAmountIndex);
                    int totalItems = cursor.getInt(totalItemsIndex);

                    latestOrder = new Order(orderId, totalAmount, totalItems);
                }
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting latest order: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return latestOrder;
    }



    // Users methods

    public void insertData(String email, String password) {
        SQLiteDatabase db = null;

        try {
            db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_EMAIL, email);
            contentValues.put(KEY_PASSWORD, password);
            db.insertOrThrow(TABLE_USERS, null, contentValues);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting user data: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
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