package com.anthony.myapplication.ui.employee

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anthony.myapplication.entity.Employee
import com.anthony.myapplication.helper.SharePref
import com.anthony.myapplication.ui.camera.Constant
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class EmpViewModel(app:Application):AndroidViewModel(app) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val empLiveData = MutableLiveData<List<Employee>>()
    private val empByIdLiveData = MutableLiveData<Employee>()
    private val empByPhoneLiveData = MutableLiveData<Employee>()
    private val sPref = SharePref(context)
    private val phone = sPref.phone
    private var employees:List<Employee> = arrayListOf()
    private val mData= Constant.ROOT_DB

    fun saveEmployee(employee: Employee,phoneEmp:String){
        mData.child("employee").child(phoneEmp).setValue(employee)
    }

    fun deleteById(phoneEmp: String){
        mData.child("employee").child(phoneEmp).removeValue()
    }

    fun getAllEmployee():MutableLiveData<List<Employee>>{
        mData.child("employee").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    employees = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue<Employee>()!!
                    }
                    empLiveData.value = employees
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
        return empLiveData
    }

    fun getEmpById():MutableLiveData<Employee>{
        mData.child("employee").child(phone).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val emp:Employee = snapshot.getValue(Employee::class.java)!!
                    empByIdLiveData.value = emp
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
        return empByIdLiveData
    }

    fun getEmpByPhone(phoneEmp:String):MutableLiveData<Employee>{
        mData.child("employee").child(phoneEmp).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val emp:Employee = snapshot.getValue(Employee::class.java)!!
                    empByPhoneLiveData.value = emp
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
        return empByPhoneLiveData
    }

}