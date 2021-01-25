package com.aldc.talha.aldc;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by HP-NPC on 01/08/2017.
 */


public class TestDbProvider extends ContentProvider {

    private static final int TESTS = 100;

    private static final int TEST_ID = 101;

    private static final int TEST_RESULT = 102;

    private static final int TEST_RESULT_ID = 103;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(TestsContract.CONTENT_AUTHORITY, TestsContract.PATH_TESTS, TESTS);

        sUriMatcher.addURI(TestsContract.CONTENT_AUTHORITY, TestsContract.PATH_RESULT, TEST_RESULT);

        sUriMatcher.addURI(TestsContract.CONTENT_AUTHORITY, TestsContract.PATH_TESTS + "/#", TEST_ID);

        sUriMatcher.addURI(TestsContract.CONTENT_AUTHORITY, TestsContract.PATH_RESULT + "/#", TEST_RESULT_ID);
    }

    private TestDbHelper mDbHelper,m1DbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new TestDbHelper(getContext(), "tets.db");
        m1DbHelper = new TestDbHelper(getContext(), "result.db");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        SQLiteDatabase database;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                database = mDbHelper.getReadableDatabase();
                cursor = database.query(TestsContract.TestEntry.table_name, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case TEST_ID:
                selection = TestsContract.TestEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                database = mDbHelper.getReadableDatabase();
                cursor = database.query(TestsContract.TestEntry.table_name, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case TEST_RESULT:
                database = m1DbHelper.getReadableDatabase();
                cursor = database.query(TestsContract.TestEntry.table_name, projection, selection, selectionArgs, null, null, TestsContract.TestEntry._ID + " DESC");
                break;
            case TEST_RESULT_ID:
                selection = TestsContract.TestEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                database = m1DbHelper.getReadableDatabase();
                cursor = database.query(TestsContract.TestEntry.table_name, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase database;
        switch (match) {
            case TESTS:
                database = mDbHelper.getWritableDatabase();
                long id = database.insert(TestsContract.TestEntry.table_name, null, values);
                if (id == -1) {
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case TEST_RESULT:
                database = m1DbHelper.getWritableDatabase();
                long id1 = database.insert(TestsContract.TestEntry.table_name, null, values);
                if (id1 == -1) {
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id1);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database;

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                database = mDbHelper.getWritableDatabase();
                rowsDeleted = database.delete(TestsContract.TestEntry.table_name, selection, selectionArgs);
                break;
            case TEST_RESULT:
                database = m1DbHelper.getWritableDatabase();
                rowsDeleted = database.delete(TestsContract.TestEntry.table_name, selection, selectionArgs);
                break;
           default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
