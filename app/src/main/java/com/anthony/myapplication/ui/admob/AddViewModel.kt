package com.anthony.myapplication.ui.admob

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anthony.myapplication.entity.Admob
import com.anthony.myapplication.entity.Advert
import com.anthony.myapplication.ui.camera.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class AddViewModel(app:Application):AndroidViewModel(app) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val admobLiveData = MutableLiveData<List<Admob>>()
    private val avertLiveData = MutableLiveData<List<Advert>>()
    private var listAdds:List<Admob> = arrayListOf()
    private var listAdv:List<Advert> = arrayListOf()
    private val mData= Constant.ROOT_DB

    fun saveBanner(admob: Admob, admobId:String){
        mData.child("admob").child(admobId).setValue(admob)
    }

    fun saveAdv(admob: Admob, admobId:String){
        mData.child("advert").child(admobId).setValue(admob)
    }

    fun getAllAdd(): MutableLiveData<List<Admob>> {
        mData.child("admob").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    listAdds = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue<Admob>()!!
                    }
                    admobLiveData.value = listAdds
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
        return admobLiveData
    }
    fun getAllAdv(): MutableLiveData<List<Advert>> {
        mData.child("advert").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    listAdv = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue<Advert>()!!
                    }
                    avertLiveData.value = listAdv
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
        return avertLiveData
    }
    fun deleteById(admobId: String){
        mData.child("admob").child(admobId).removeValue()
    }
}