package com.aldc.talha.aldc;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by HP-NPC on 01/08/2017.
 */

public class TestCursorAdapter extends CursorAdapter {
    public TestCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.test_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView test_name_view = (TextView) view.findViewById(R.id.test_name);

        int test_name_index = cursor.getColumnIndex(TestsContract.TestEntry.test_Name);

        String test_name = cursor.getString(test_name_index);

        test_name_view.setText(test_name);
    }
}
