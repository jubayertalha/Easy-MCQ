package com.aldc.talha.aldc;


import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    ImageView imageView;
    ProgressBar progressBar;
    Button button;
    Menu menu;
    MenuItem catagoriItem;
    MenuItem dletItem;
    MenuItem aboutItem;
    MenuItem retryItem;
    boolean isNew = true;
    boolean lol = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(" ");

        imageView = (ImageView)findViewById(R.id.welcome);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        button = (Button)findViewById(R.id.button);

        button.setVisibility(View.GONE);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);

        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        if(savedInstanceState!=null){
            imageView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            lol = true;
            setTitle("Easy MCQ");
        }else {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu,menu);
        catagoriItem = menu.findItem(R.id.catagori);
        dletItem = menu.findItem(R.id.delete);
        aboutItem = menu.findItem(R.id.about);
        retryItem = menu.findItem(R.id.retry);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you SURE to delete all of Your Result?");
                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int row = getContentResolver().delete(TestsContract.TestEntry.RESULT_URI,null,null);
                        Toast.makeText(MainActivity.this,"All Results are Deleted!",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                textView.setTextSize(20);
                break;
            case R.id.retry:
                start();
            case R.id.about:

                break;
            case R.id.catagori:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        catagoriItem = menu.findItem(R.id.catagori);
        dletItem = menu.findItem(R.id.delete);
        aboutItem = menu.findItem(R.id.about);
        retryItem = menu.findItem(R.id.retry);
        if(isNew && !lol){
            catagoriItem.setVisible(false);
            aboutItem.setVisible(false);
            dletItem.setVisible(false);
            retryItem.setVisible(false);
        }
        return true;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        return new TestLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        CrackTestJson crackTestJson = new CrackTestJson(data,this);
        crackTestJson.crackTestJson();
        String[] projection = new String[]{TestsContract.TestEntry._ID};
        Cursor cursor = getContentResolver().query(TestsContract.TestEntry.CONTENT_URI,projection,null,null,null);
        int i = cursor.getCount();
        if(i==0){
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    start();
                }
            });
            Toast.makeText(this,"No Internet!\nYou need data connection for first time.",Toast.LENGTH_SHORT).show();
        }else {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            isNew = false;
            catagoriItem.setVisible(true);
            aboutItem.setVisible(true);
            dletItem.setVisible(true);
            retryItem.setVisible(true);
            if(data==""){
                Toast.makeText(this,"No Internet!",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Updated!",Toast.LENGTH_SHORT).show();
            }
            setTitle("Easy MCQ");
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("lol",false);
    }

    public void start(){
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(1,null,this);
    }
}