package com.aldc.talha.aldc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP-NPC on 31/07/2017.
 */

public class TestDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tests.db";

    private static String TABLE_NAME = TestsContract.TestEntry.table_name;

    public TestDbHelper(Context context, String name) {
        super(context, name, null, 1);
        //TABLE_NAME = name;
    }

    String SQL_CREATE_Tests_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
            + TestsContract.TestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TestsContract.TestEntry.test_ID + " INTEGER, "
            + TestsContract.TestEntry.Result + " TEXT, "
            + TestsContract.TestEntry.Per + " TEXT, "
            + TestsContract.TestEntry.Total + " TEXT, "
            + TestsContract.TestEntry.test_Name + " TEXT, "
            + TestsContract.TestEntry.Catagory + " TEXT, "
            + TestsContract.TestEntry.table_name_of_Qus + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE_Tests_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
