package com.example.buddyapplication.database;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.buddyapplication.model.Buddy;
import com.example.buddyapplication.model.User;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BuddyApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USER = "users";
    public static final String TABLE_BUDDY = "buddies";

    // Column Names (User)
    public static final String COL_USER_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

    // Column Names (Buddy)
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_GENDER = "gender";
    public static final String COL_DOB = "dob";
    public static final String COL_PHONE = "phone";
    public static final String COL_EMAIL = "email";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //User Table
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT,"
                + COL_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        //Buddy Table
        String CREATE_BUDDY_TABLE = "CREATE TABLE " + TABLE_BUDDY + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_GENDER + " TEXT,"
                + COL_DOB + " TEXT,"
                + COL_PHONE + " TEXT,"
                + COL_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_BUDDY_TABLE);

        //default admin user dummy
        db.execSQL("INSERT INTO " + TABLE_USER + " (username, password) VALUES ('admin', '1234')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDDY);
        onCreate(db);
    }

    // CRUDS OPERATIONS BELOW HERE
    // User Operations
    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_PASSWORD, user.getPassword());

        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE "
                + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Buddy Operations
    // 1. CREATE
    public boolean addBuddy(Buddy buddy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, buddy.getName());
        values.put(COL_GENDER, buddy.getGender());
        values.put(COL_DOB, buddy.getDob());
        values.put(COL_PHONE, buddy.getPhone());
        values.put(COL_EMAIL, buddy.getEmail());

        long result = db.insert(TABLE_BUDDY, null, values);
        return result != -1;
    }

    // 2. READ (All)
    public List<Buddy> getAllBuddies() {
        List<Buddy> buddyList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BUDDY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Buddy buddy = new Buddy();
                buddy.setId(cursor.getInt(0));
                buddy.setName(cursor.getString(1));
                buddy.setGender(cursor.getString(2));
                buddy.setDob(cursor.getString(3));
                buddy.setPhone(cursor.getString(4));
                buddy.setEmail(cursor.getString(5));
                buddyList.add(buddy);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return buddyList;
    }

    // 3. UPDATE
    public int updateBuddy(Buddy buddy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, buddy.getName());
        values.put(COL_GENDER, buddy.getGender());
        values.put(COL_DOB, buddy.getDob());
        values.put(COL_PHONE, buddy.getPhone());
        values.put(COL_EMAIL, buddy.getEmail());

        return db.update(TABLE_BUDDY, values, COL_ID + " = ?",
                new String[]{String.valueOf(buddy.getId())});
    }

    // 4. DELETE
    public void deleteBuddy(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUDDY, COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // 5. SEARCH (Requirement 3)
    public List<Buddy> searchBuddy(String keyword) {
        List<Buddy> buddyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Search by Name
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUDDY + " WHERE " + COL_NAME + " LIKE ?",
                new String[]{"%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            do {
                Buddy buddy = new Buddy();
                buddy.setId(cursor.getInt(0));
                buddy.setName(cursor.getString(1));
                buddy.setGender(cursor.getString(2));
                buddy.setDob(cursor.getString(3));
                buddy.setPhone(cursor.getString(4));
                buddy.setEmail(cursor.getString(5));
                buddyList.add(buddy);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return buddyList;
    }
}
