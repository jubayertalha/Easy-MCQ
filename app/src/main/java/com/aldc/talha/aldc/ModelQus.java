package com.aldc.talha.aldc;

/**
 * Created by HP-NPC on 03/08/2017.
 */

public class ModelQus {
    public  int qusNo;
    public  String qusName;
    public  String ops1;
    public  String ops2;
    public  String ops3;
    public  String ops4;
    public  int Ans;

    public  int point = 0;
    public  int currect = 0;

    public  int current = -1;

    public int getQusNo() {
        return qusNo;
    }

    public void setQusNo(int qusNo) {
        this.qusNo = qusNo;
    }

    public String getQusName() {
        return qusName;
    }

    public void setQusName(String qusName) {
        this.qusName = qusName;
    }

    public String getOps1() {
        return ops1;
    }

    public void setOps1(String ops1) {
        this.ops1 = ops1;
    }

    public String getOps2() {
        return ops2;
    }

    public void setOps2(String ops2) {
        this.ops2 = ops2;
    }

    public String getOps3() {
        return ops3;
    }

    public void setOps3(String ops3) {
        this.ops3 = ops3;
    }

    public String getOps4() {
        return ops4;
    }

    public void setOps4(String ops4) {
        this.ops4 = ops4;
    }

    public int getAns() {
        return Ans;
    }

    public void setAns(int ans) {
        Ans = ans;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCurrect() {
        return currect;
    }

    public void setCurrect(int currect) {
        this.currect = currect;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public ModelQus(int qus_No, String qus_Name, String ops_1, String ops_2, String ops_3, String ops_4, int ans){
        qusNo = qus_No;
        qusName = qus_Name;
        ops1 = ops_1;
        ops2 = ops_2;
        ops3 = ops_3;
        ops4 = ops_4;
        Ans = ans;

    }

    public ModelQus(int qus_No, String qus_Name, String ops_1, String ops_2, String ops_3, String ops_4, int ans,int Point,int Currect,int Current){
        qusNo = qus_No;
        qusName = qus_Name;
        ops1 = ops_1;
        ops2 = ops_2;
        ops3 = ops_3;
        ops4 = ops_4;
        Ans = ans;
        point = Point;
        currect = Currect;
        current = Current;

    }
}
