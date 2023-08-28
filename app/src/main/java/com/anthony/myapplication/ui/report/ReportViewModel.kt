package com.anthony.myapplication.ui.report

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anthony.myapplication.entity.Employee
import com.anthony.myapplication.entity.Trx
import com.anthony.myapplication.helper.SharePref
import com.anthony.myapplication.ui.camera.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReportViewModel(app:Application):AndroidViewModel(app) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val trxLiveData = MutableLiveData<List<Trx>>()
    private val empByIdLiveData = MutableLiveData<Employee>()
    private val empByPhoneLiveData = MutableLiveData<Employee>()
    private val sPref = SharePref(context)
    private val phone = sPref.phone
    private var trxs:List<Trx> = arrayListOf()
    private val mData= Constant.ROOT_DB

    fun getAllTrx(now:String):MutableLiveData<List<Trx>>{
        mData.child("trx").orderByChild("startDate").equalTo(now)
            .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    trxs = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Trx::class.java)!!
                    }
                    trxLiveData.value = trxs

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
        return trxLiveData
    }


}