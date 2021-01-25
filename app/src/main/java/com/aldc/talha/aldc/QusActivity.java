package com.aldc.talha.aldc;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class QusActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public Uri currentUri;
    public String test_name;
    public String test_json;
    public ArrayList<ModelQus> modelQuses;
    public ListView listView;
    public boolean everSubmit = false;
    public CustomBaseAdapter adapter;
    public boolean isResult = false;
    public Menu menu;
    public MenuItem menuItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qus);

        listView = (ListView)findViewById(R.id.qus_listView);
        modelQuses = new ArrayList<ModelQus>();
        modelQuses.clear();

        Intent intent = getIntent();
        currentUri = intent.getData();
        isResult = intent.getBooleanExtra("isResult",false);

        if(savedInstanceState==null && !isResult){
            showWelcomeDialog();
            getLoaderManager().initLoader(0,null,this);
        }else if(savedInstanceState==null && isResult) {
            everSubmit = true;
            getLoaderManager().initLoader(0,null,this);
        }else{
            modelQuses = (ArrayList<ModelQus>) savedInstanceState.getSerializable("list");
            everSubmit = savedInstanceState.getBoolean("eversubmit");
            test_name = savedInstanceState.getString("title");
            setTitle(test_name);
            adapter = new CustomBaseAdapter(this,modelQuses);
            if(everSubmit){
                adapter.last(0);
            }
            listView.setAdapter(adapter);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {TestsContract.TestEntry._ID, TestsContract.TestEntry.test_Name, TestsContract.TestEntry.table_name_of_Qus};
        return new CursorLoader(this,currentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()){
            int nameIdx = data.getColumnIndex(TestsContract.TestEntry.test_Name);
            int jsonIdx = data.getColumnIndex(TestsContract.TestEntry.table_name_of_Qus);

            test_name = data.getString(nameIdx);
            test_json = data.getString(jsonIdx);

            setTitle(test_name);

            setData();

            adapter = new CustomBaseAdapter(this,modelQuses);
            if(everSubmit){
                adapter.last(0);
            }
            listView.setAdapter(adapter);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void setData(){
        try {
            JSONArray qus_array = new JSONArray(test_json);
            for(int i=0;i<qus_array.length();i++){
                JSONObject currentObj = qus_array.getJSONObject(i);
                int qusNmb = currentObj.getInt(TestsContract.TestEntry.qus_ID);
                String qusName = currentObj.getString(TestsContract.TestEntry.Qus);
                String ops1 = currentObj.getString(TestsContract.TestEntry.OP1);
                String ops2 = currentObj.getString(TestsContract.TestEntry.OP2);
                String ops3 = currentObj.getString(TestsContract.TestEntry.OP3);
                String ops4 = currentObj.getString(TestsContract.TestEntry.OP4);
                int ans = currentObj.getInt(TestsContract.TestEntry.Ans);
                ModelQus m1;
                if(!isResult){
                    m1 = new ModelQus(qusNmb,qusName,ops1,ops2,ops3,ops4,ans);
                }else {
                    int point = currentObj.getInt(TestsContract.TestEntry.Point);
                    int currect = currentObj.getInt(TestsContract.TestEntry.Currect);
                    int current = currentObj.getInt(TestsContract.TestEntry.Current);
                    m1 = new ModelQus(qusNmb,qusName,ops1,ops2,ops3,ops4,ans,point,currect,current);
                }
                modelQuses.add(i,m1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu,menu);
        menuItem = menu.findItem(R.id.done);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done:
                showConfirmSubmitDilog();
                everSubmit = true;
                return true;
            case android.R.id.home:
                if(everSubmit){
                    finish();
                }else {
                    showExitDialog();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menuItem = menu.findItem(R.id.done);
        if(everSubmit){
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(everSubmit){
            super.onBackPressed();
        }else {
            showExitDialog();
        }
    }

    public void result(){
        int result=0;
        for(int i=0;modelQuses.size()>i;i++){
            if(modelQuses.get(i).getPoint()==1){
                result++;
            }
        }
        String r = Integer.toString(result);
        int j = modelQuses.size();
        String t = Integer.toString(j);
        double size = Double.parseDouble(t);
        double res = Double.parseDouble(r);
        double persent = (res/size)*100.0;
        String per = Double.toString(persent);
        String percent = String.format(Locale.getDefault(),"%.2f",persent);
        String percent1 = percent + "%";
        String text;
        int i;
        if(persent<=33.0){
            i = 1;
            text = "So bad result!\n"+"You got "+ percent1 + " marks.\n"+"Your score "+r + " within "+t+".";
        }else if(persent>33.0 && persent<80.0){
            i = 2;
            text = "Good result!\n"+"You got "+ percent1 + " marks.\n"+"Your score "+r + " within "+t+".";
        }else {
            i = 3;
            text = "Excellent result!\n"+"You got "+ percent1 + " marks.\n"+"Your score "+r + " within "+t+".";
        }

        showCongratulatioDiolog(text,i);
        adapter.last(0);
        listView.setAdapter(adapter);

        Toast.makeText(this,"Your result is Added!",Toast.LENGTH_SHORT).show();

        ResultClass resultClass = new ResultClass(this,test_name,per,t,r,modelQuses);

        resultClass.resultInDB();

    }

    private void showConfirmSubmitDilog(){
        String text = "Sure to confirm?\nOr want to Continue?";
        boolean checked = true;
        for(int i=0;i<modelQuses.size();i++){
            if(modelQuses.get(i).getCurrent() == -1){
                checked = false;
                break;
            }
        }
        if(!checked){
            text = "You dont answer all.\nDo you want to Continue or Confirm?";
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result();
                menuItem = menu.findItem(R.id.done);
                menuItem.setVisible(false);
            }
        });
        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(20);

    }

    private void showCongratulatioDiolog(String text,int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text);
        if(i==1){
            builder.setIcon(R.drawable.low);
            builder.setTitle("Opppss!");
        }else if(i == 2){
            builder.setIcon(R.drawable.middel);
            builder.setTitle("Congratulation!");
        }else {
            builder.setIcon(R.drawable.high);
            builder.setTitle("You are Great!!");
        }
        builder.setCancelable(false);
        builder.setPositiveButton("OK DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }

    public void showExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("You dont Submit Your Ans.\nDo you want to stop your exam?");
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }

    public void showWelcomeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("OK MAN LETS DO IT!");
        builder.setIcon(R.drawable.wel);
        builder.setTitle("Get Ready For Exam!");
        final AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("list",modelQuses);
        outState.putBoolean("eversubmit",everSubmit);
        outState.putString("title",test_name);
    }
}
