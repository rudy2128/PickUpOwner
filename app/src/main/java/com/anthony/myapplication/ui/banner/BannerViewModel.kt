package com.anthony.myapplication.ui.banner

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anthony.myapplication.entity.Banner
import com.anthony.myapplication.ui.camera.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class BannerViewModel(app:Application):AndroidViewModel(app) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val bannerLiveData = MutableLiveData<List<Banner>>()
    private val banLiveData = MutableLiveData<List<String>>()
    private var listBanner:List<Banner> = arrayListOf()
    private var listBan = arrayListOf<String>()
    private val mData= Constant.ROOT_DB

    fun saveBanner(banner: Banner,bannerId:String){
        mData.child("banner").child(bannerId).setValue(banner)
    }

    fun getAllBanner():MutableLiveData<List<Banner>>{
        mData.child("banner").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   listBanner = snapshot.children.map { dataSnapshot ->
                       dataSnapshot.getValue<Banner>()!!
                   }
                   bannerLiveData.value = listBanner
               }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
        return bannerLiveData
    }


}