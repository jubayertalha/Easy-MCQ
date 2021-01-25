package com.aldc.talha.aldc;


import android.content.ContentUris;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    public TestListFragment() {
        // Required empty public constructor
    }

    TestCursorAdapter cursorAdapter;
    public String select = "all";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_list, container, false);

        ListView listView = (ListView)view.findViewById(R.id.examList);
        cursorAdapter = new TestCursorAdapter(getActivity(),null);
        listView.setAdapter(cursorAdapter);

        getLoaderManager().initLoader(0,null,this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),QusActivity.class);
                Uri uri = ContentUris.withAppendedId(TestsContract.TestEntry.CONTENT_URI,id);
                intent.setData(uri);
                intent.putExtra("isResult",false);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = null;
        String[] selectionArgs = null;
        if(select != TestsContract.TestEntry.ALL){
            selection = TestsContract.TestEntry.Catagory + "=?";
            selectionArgs = new String[] { select };
        }
        String[] projectiom = {TestsContract.TestEntry._ID, TestsContract.TestEntry.test_Name};
        return new CursorLoader(getActivity(), TestsContract.TestEntry.CONTENT_URI,projectiom,selection,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
