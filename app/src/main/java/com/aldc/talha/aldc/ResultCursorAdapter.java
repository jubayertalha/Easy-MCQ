package com.aldc.talha.aldc;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by HP-NPC on 11/08/2017.
 */

public class ResultCursorAdapter extends CursorAdapter {
    public ResultCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.result_item,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.testName);
        TextView totalView = (TextView) view.findViewById(R.id.total);
        TextView resultView = (TextView) view.findViewById(R.id.result);
        TextView percentView = (TextView) view.findViewById(R.id.persent);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.resultLayout);

        int name_indx = cursor.getColumnIndex(TestsContract.TestEntry.test_Name);
        int total_indx = cursor.getColumnIndex(TestsContract.TestEntry.Total);
        int result_indx = cursor.getColumnIndex(TestsContract.TestEntry.Result);
        int percent_indx = cursor.getColumnIndex(TestsContract.TestEntry.Per);

        String name = cursor.getString(name_indx);
        String total = cursor.getString(total_indx);
        String result = cursor.getString(result_indx);
        String percent = cursor.getString(percent_indx);
        double per = Double.parseDouble(percent);
        String percentlol = String.format(Locale.getDefault(),"%.2f",per);
        String percent1 = percentlol + "%";

        nameView.setText(name);
        totalView.setText(total);
        resultView.setText(result);
        percentView.setText(percent1);

        if(per<33.00){
            layout.setBackgroundColor(Color.parseColor("#80ff0000"));
        }else if(per>=88.0){
            layout.setBackgroundColor(Color.parseColor("#80008000"));
        }else {
            layout.setBackgroundColor(Color.parseColor("#80da68f9"));
        }
    }
}
