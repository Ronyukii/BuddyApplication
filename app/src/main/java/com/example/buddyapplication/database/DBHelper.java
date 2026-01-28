package com.example.buddyapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String COL_BUDDY_ID = "id";
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
                + COL_BUDDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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
    // What we need:
    // 1. public boolean addUser(User user)
    // 2. public boolean checkLogin(String user, String pass)
    // 3. public boolean addBuddy(Buddy buddy)
    // 4. public ArrayList<Buddy> getAllBuddies()
    // 5. public void updateBuddy(Buddy buddy)
    // 6. public void deleteBuddy(int id)
    // 7. public ArrayList<Buddy> searchBuddy(String keyword)
}
