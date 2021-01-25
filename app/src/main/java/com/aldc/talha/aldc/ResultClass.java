package com.aldc.talha.aldc;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP-NPC on 07/08/2017.
 */

public class ResultClass {

    public String mName,mResult,mPer,mTotal;
    public ArrayList<ModelQus> mArrayList;
    public Context myContext;

    public ResultClass(Context context,String name,String per, String total, String result, ArrayList<ModelQus> arrayList){
        mName = name;
        mPer = per;
        mTotal = total;
        mResult = result;
        mArrayList = arrayList;
        myContext = context;
    }

    public void resultInDB(){
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < mArrayList.size(); i++){
            JSONObject jsonObject = new JSONObject();
            ModelQus m1 = mArrayList.get(i);
            try {
                jsonObject.put(TestsContract.TestEntry.qus_ID,m1.getQusNo());
                jsonObject.put(TestsContract.TestEntry.Qus,m1.getQusName());
                jsonObject.put(TestsContract.TestEntry.OP1,m1.getOps1());
                jsonObject.put(TestsContract.TestEntry.OP2,m1.getOps2());
                jsonObject.put(TestsContract.TestEntry.OP3,m1.getOps3());
                jsonObject.put(TestsContract.TestEntry.OP4,m1.getOps4());
                jsonObject.put(TestsContract.TestEntry.Ans,m1.getAns());
                jsonObject.put(TestsContract.TestEntry.Point,m1.getPoint());
                jsonObject.put(TestsContract.TestEntry.Currect,m1.getCurrect());
                jsonObject.put(TestsContract.TestEntry.Current,m1.getCurrent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        String array = jsonArray.toString();
        ContentValues values = new ContentValues();
        values.put(TestsContract.TestEntry.test_Name,mName);
        values.put(TestsContract.TestEntry.Per,mPer);
        values.put(TestsContract.TestEntry.Result,mResult);
        values.put(TestsContract.TestEntry.Total,mTotal);
        values.put(TestsContract.TestEntry.table_name_of_Qus,array);
        Uri newUri = myContext.getContentResolver().insert(TestsContract.TestEntry.RESULT_URI,values);
    }

}
