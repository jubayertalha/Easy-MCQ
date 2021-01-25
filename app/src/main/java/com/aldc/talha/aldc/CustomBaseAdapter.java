package com.aldc.talha.aldc;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP-NPC on 03/08/2017.
 */

public class CustomBaseAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<ModelQus> list;
    int cng=1;

    public CustomBaseAdapter(Context context, ArrayList<ModelQus> arrayList){
        inflater = LayoutInflater.from(context);
        list = arrayList;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if (v == null){
            v = inflater.inflate(R.layout.qus_item,parent,false);
            holder = new ViewHolder(v);


            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }
        holder.group.setTag(new Integer(position));

        holder.group.setOnCheckedChangeListener(null);

        if(list.get(position).getCurrent() != -1){
            RadioButton r = (RadioButton) holder.group.getChildAt(list.get(position).getCurrent());
            r.setChecked(true);
        }else {
            holder.group.clearCheck();
        }

        holder.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Integer pos = (Integer)group.getTag();
                ModelQus element = list.get(pos);
                switch (checkedId){
                    case R.id.op1:
                        if (element.getAns() == 1){
                            if(element.getCurrect() == 0){
                                element.setPoint(1);
                                element.setCurrect(1);
                            }
                        }else {
                            element.setPoint(0);
                            element.setCurrect(0);
                        }
                        element.setCurrent(0);
                        break;
                    case R.id.op2:
                        if (element.getAns() == 2){
                            if(element.getCurrect() == 0){
                                element.setPoint(1);
                                element.setCurrect(1);
                            }
                        }else {
                            element.setPoint(0);
                            element.setCurrect(0);
                        }
                        element.setCurrent(1);
                        break;
                    case R.id.op3:
                        if (element.getAns() == 3){
                            if(element.getCurrect() == 0){
                                element.setPoint(1);
                                element.setCurrect(1);
                            }
                        }else {
                            element.setPoint(0);
                            element.setCurrect(0);
                        }
                        element.setCurrent(2);
                        break;
                    case R.id.op4:
                        if (element.getAns() == 4){
                            if(element.getCurrect() == 0){
                                element.setPoint(1);
                                element.setCurrect(1);
                            }
                        }else {
                            element.setPoint(0);
                            element.setCurrect(0);
                        }
                        element.setCurrent(3);
                        break;
                }
            }
        });

        int j = list.get(position).getQusNo();
        String nmb = Integer.toString(j);

        holder.qus.setText(nmb+". "+ list.get(position).getQusName());
        holder.op1.setText(list.get(position).getOps1());
        holder.op2.setText(list.get(position).getOps2());
        holder.op3.setText(list.get(position).getOps3());
        holder.op4.setText(list.get(position).getOps4());

        if(cng==0){
            if(list.get(position).getCurrent()!=list.get(position).getAns()-1){
                RadioButton rc = (RadioButton) holder.group.getChildAt(list.get(position).getAns()-1);
                RadioButton r = (RadioButton) holder.group.getChildAt(0);
                RadioButton r1 = (RadioButton) holder.group.getChildAt(1);
                RadioButton r2 = (RadioButton) holder.group.getChildAt(2);
                RadioButton r3 = (RadioButton) holder.group.getChildAt(3);
                if(rc==r){
                    r.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    r.setTextColor(Color.parseColor("#000000"));
                }
                if(rc==r1){
                    r1.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    r1.setTextColor(Color.parseColor("#000000"));
                }
                if(rc==r2){
                    r2.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    r2.setTextColor(Color.parseColor("#000000"));
                }
                if(rc==r3){
                    r3.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    r3.setTextColor(Color.parseColor("#000000"));
                }
                holder.qus.setTextColor(Color.parseColor("#ff0000"));
            }else {
                RadioButton r = (RadioButton) holder.group.getChildAt(0);
                RadioButton r1 = (RadioButton) holder.group.getChildAt(1);
                RadioButton r2 = (RadioButton) holder.group.getChildAt(2);
                RadioButton r3 = (RadioButton) holder.group.getChildAt(3);
                r.setTextColor(Color.parseColor("#000000"));
                r1.setTextColor(Color.parseColor("#000000"));
                r2.setTextColor(Color.parseColor("#000000"));
                r3.setTextColor(Color.parseColor("#000000"));
                holder.qus.setTextColor(Color.parseColor("#008000"));
            }

            RadioButton r = (RadioButton) holder.group.getChildAt(0);
            RadioButton r1 = (RadioButton) holder.group.getChildAt(1);
            RadioButton r2 = (RadioButton) holder.group.getChildAt(2);
            RadioButton r3 = (RadioButton) holder.group.getChildAt(3);

            r.setClickable(false);
            r1.setClickable(false);
            r2.setClickable(false);
            r3.setClickable(false);

        }

        return v;
    }

    public void last(int a){
        cng = a;
    }

    class ViewHolder{
        TextView qus = null;
        RadioGroup group = null;
        RadioButton op1,op2,op3,op4;
        ViewHolder(View view){
            qus = (TextView) view.findViewById(R.id.qus);
            group = (RadioGroup) view.findViewById(R.id.radioGroup);
            op1 = (RadioButton) view.findViewById(R.id.op1);
            op2 = (RadioButton) view.findViewById(R.id.op2);
            op3 = (RadioButton) view.findViewById(R.id.op3);
            op4 = (RadioButton) view.findViewById(R.id.op4);
        }
    }

}
