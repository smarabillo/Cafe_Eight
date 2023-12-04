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

    // Orders table properties
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME_ORDERS = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "price";

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
        // Create Orders table
        String createOrdersTableQuery = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_ORDERS + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_PRICE + " REAL)";
        db.execSQL(createOrdersTableQuery);

        // Create Users table
        String createUsersTableQuery = "CREATE TABLE " + TABLE_USERS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_PASSWORD + " TEXT)";
        db.execSQL(createUsersTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop both tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Orders methods

    public long insertOrder(String name, int quantity, double price) {
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_ORDERS, name);
            values.put(COLUMN_QUANTITY, quantity);
            values.put(COLUMN_PRICE, price);
            id = db.insert(TABLE_ORDERS, null, values);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting order: " + e.getMessage());
        } finally {
            closeDatabase(db);
        }

        return id;
    }

    public List<Fragment_Cart.CartItem> getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Fragment_Cart.CartItem> cartItems = new ArrayList<>();

        Cursor cursor = db.query(TABLE_ORDERS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME_ORDERS);
            int quantityIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
            int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);

            do {

                if (nameIndex >= 0 && quantityIndex >= 0 && priceIndex >= 0) {
                    String itemName = cursor.getString(nameIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    double price = cursor.getDouble(priceIndex);

                    Fragment_Cart.CartItem cartItem = new Fragment_Cart.CartItem(itemName, quantity, price);
                    cartItems.add(cartItem);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return cartItems;
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, null, null, null, null, null);
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
