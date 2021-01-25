package com.aldc.talha.aldc;


import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    public ResultFragment() {
        // Required empty public constructor
    }

    ResultCursorAdapter cursorAdapter;
    int total=0,good=0,mideum=0,bad=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Summery...");
                builder.setIcon(R.drawable.wel);
                builder.setMessage("Total    :     "+total+"\n" +
                                    "Best     :     "+good+"\n" +
                                    "Good    :     "+mideum+"\n" +
                                     "Bad       :     "+bad);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                textView.setTextSize(20);
            }
        });

        ListView listView = (ListView)view.findViewById(R.id.resultList);
        cursorAdapter = new ResultCursorAdapter(getActivity(),null);
        listView.setAdapter(cursorAdapter);

        getLoaderManager().initLoader(0,null,this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),QusActivity.class);
                Uri uri = ContentUris.withAppendedId(TestsContract.TestEntry.RESULT_URI,id);
                intent.setData(uri);
                intent.putExtra("isResult",true);
                startActivity(intent);
            }
        });

        View emptiView = view.findViewById(R.id.empti);

        listView.setEmptyView(emptiView);

        return view;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projectiom = {TestsContract.TestEntry._ID, TestsContract.TestEntry.test_Name, TestsContract.TestEntry.Total, TestsContract.TestEntry.Per, TestsContract.TestEntry.Result};
        return new CursorLoader(getActivity(), TestsContract.TestEntry.RESULT_URI,projectiom,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Cursor cursor = data;
        total = data.getCount();
        bad = 0;
        mideum = 0;
        good = 0;
        if(data.moveToFirst()){
            do{
                int percent_indx = data.getColumnIndex(TestsContract.TestEntry.Per);
                String percent = data.getString(percent_indx);
                double per = Double.parseDouble(percent);
                if(per<33.00){
                    bad++;
                }else if(per>=88.0){
                    good++;
                }else {
                    mideum++;
                }
            }while (data.moveToNext());
        }
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
