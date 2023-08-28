package com.anthony.myapplication.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class MyPref{
    private  var sp: SharedPreferences?=null
    private  var spe: SharedPreferences.Editor?=null
    private lateinit var context: Context

    @SuppressLint("CommitPrefEdits")
    fun save(context: Context) {
        // TODO Auto-generated constructor stub
        this.context = context
        sp = context.getSharedPreferences("MyData", Context.MODE_PRIVATE)
        spe = sp?.edit()
    }

    fun setJwtToken(token: String?) {
        spe?.putString("token", token)?.apply()
    }

    fun getJwtToken(): String? {
        return sp?.getString("token", "")
    }

    fun deleteJwtToken() {
        spe?.clear()
        spe?.commit()
    }

    fun setName(name: String?) {
        spe?.putString("name", name)?.apply()
    }

    fun getName(): String? {
        return sp?.getString("name", "")
    }

    fun deleteName() {
        spe?.clear()
        spe?.commit()
    }
}