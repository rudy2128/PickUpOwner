package com.anthony.myapplication.ui.trx

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anthony.myapplication.entity.Employee
import com.anthony.myapplication.entity.History
import com.anthony.myapplication.entity.Trx
import com.anthony.myapplication.helper.SharePref
import com.anthony.myapplication.ui.camera.Constant
import com.google.firebase.database.*

class TrxViewModel(app:Application):AndroidViewModel(app) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val trxLiveData = MutableLiveData<List<Trx>>()
    private val trxByIdLiveData = MutableLiveData<Trx>()
    private val statusLiveData = MutableLiveData<String>()
    private val sPref = SharePref(context)
    private val phone = sPref.phone
    private var trxs:List<Trx> = arrayListOf()
    private val mData = Constant.ROOT_DB

    fun getAllTrx():MutableLiveData<List<Trx>>{
        mData.child("trx").addValueEventListener(object: ValueEventListener {
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

    fun getAllTrxHis():MutableLiveData<List<Trx>>{
        mData.child("history").addValueEventListener(object: ValueEventListener {
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

    fun saveHistory(history: History, trxId: String){
        mData.child("history").child(trxId).setValue(history)
    }

    fun deleteTrxById(trxId: String){
        mData.child("trx").child(trxId).removeValue()
    }

    fun inputEmp(employee: Employee, trxId:String){
        mData.child("trx").child(trxId).child("employee").setValue(employee)
    }

    fun checkStatus(trxId: String):MutableLiveData<String>{
        mData.child("status").child(trxId).get().addOnSuccessListener {
            val status = it.value.toString()
            statusLiveData.value = status
        }
        return statusLiveData
    }

    fun getTrxById(trxId:String):MutableLiveData<Trx>{
        mData.child("trx").child(trxId).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val trx:Trx = snapshot.getValue(Trx::class.java)!!
                    trxByIdLiveData.value = trx
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())

            }

        })
        return trxByIdLiveData
    }

    fun getTrxHisById(trxId:String):MutableLiveData<Trx>{
        mData.child("history").child(trxId).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val trx:Trx = snapshot.getValue(Trx::class.java)!!
                    trxByIdLiveData.value = trx
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())

            }

        })
        return trxByIdLiveData
    }

}