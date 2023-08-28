package com.anthony.myapplication.helper;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private final SharedPreferences sp;
    private final SharedPreferences.Editor spe;
    Context context;

    @SuppressLint("CommitPrefEdits")
    public SharePref(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        sp = context.getSharedPreferences("MyData", MODE_PRIVATE);
        spe = sp.edit();
    }
    public void setPhone(String phone){spe.putString("phone",phone).apply();}
    public String getPhone(){return sp.getString("phone","");}
    public void deletePhone(){
        spe.clear();
        spe.commit();

    }
    public void setName(String name){spe.putString("name",name).apply();}
    public String getName(){return sp.getString("name","");}

    public void setTrxId(String trxId) {
        spe.putString("trxId", trxId).apply();
    }
    public String getTrxId() {
        return sp.getString("trxId", "");
    }
    public void deleteName(){
        spe.clear();
        spe.commit();
    }
}
