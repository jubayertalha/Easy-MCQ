package com.aldc.talha.aldc;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HP-NPC on 01/08/2017.
 */

public class CrackTestJson {
    public String json_main = "";
    public Context myContext = null;
    public CrackTestJson(String json, Context context){
        json_main = json;
        myContext = context;
    }

    public void crackTestJson(){
        try {
            JSONObject object_main = new JSONObject(json_main);
            JSONArray test_array = object_main.getJSONArray("tests");
            int r = myContext.getContentResolver().delete(TestsContract.TestEntry.CONTENT_URI,null,null);
            int lenth = test_array.length();
            for(int i = 0;lenth>i;i++){
                JSONObject current_test = test_array.getJSONObject(i);
                JSONArray qus_array = current_test.getJSONArray("quss");
                String name_of_tests = current_test.getString(TestsContract.TestEntry.test_Name);
                String catagory = current_test.getString(TestsContract.TestEntry.Catagory);
                String id_of_tests = current_test.getString(TestsContract.TestEntry.test_ID);
                String all_qus = qus_array.toString();
                ContentValues values = new ContentValues();
                values.put(TestsContract.TestEntry.test_ID,id_of_tests);
                values.put(TestsContract.TestEntry.test_Name,name_of_tests);
                values.put(TestsContract.TestEntry.Catagory,catagory);
                values.put(TestsContract.TestEntry.table_name_of_Qus,all_qus);
                Uri newUri = myContext.getContentResolver().insert(TestsContract.TestEntry.CONTENT_URI,values);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
