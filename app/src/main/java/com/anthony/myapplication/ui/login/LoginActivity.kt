package com.anthony.myapplication.ui.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.anthony.myapplication.R
import com.anthony.myapplication.helper.SharePref
import com.anthony.myapplication.ui.MainActivity
import com.anthony.myapplication.ui.reg.PhoneRegActivity
import com.anthony.myapplication.ui.reg.RegActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var edtPass: TextInputEditText
    private lateinit var edtPhone: TextInputEditText
    private lateinit var btnLog: Button
    private lateinit var tvAuth: TextView
    private lateinit var tvForget: TextView
    private lateinit var tvReg: TextView
    private lateinit var mData: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtPass = findViewById(R.id.edt_password)
        edtPhone = findViewById(R.id.edt_phone)
        tvForget = findViewById(R.id.tv_forget)
        tvAuth = findViewById(R.id.tv_auth)
        tvReg = findViewById(R.id.tv_reg)
        btnLog = findViewById(R.id.btn_login)


        val phone = intent.getStringExtra("PHONE")
        edtPhone.setText(phone)

        tvReg.setOnClickListener {
            val i = Intent(this@LoginActivity, RegActivity::class.java)
            startActivity(i)
        }

        tvAuth.setOnClickListener {
            val i = Intent(this@LoginActivity, PhoneRegActivity::class.java)
            startActivity(i)
        }

        btnLog.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            val currentUser = mAuth.currentUser
            if (currentUser != null) {
                goLogin()
            }else{
                Toast.makeText(applicationContext,"Validasi no Handphone dahulu!!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun goLogin() {
        val phone  = edtPhone.text.toString()
        val password = edtPass.text.toString()
        if (phone.isEmpty()){
            edtPhone.error = getString(R.string.empty)
        }else if (password.isEmpty()){
            edtPass.error = getString(R.string.empty)
        }else{

            mData = FirebaseDatabase.getInstance("https://persontracking-39122-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("PickUp")

            val query = mData.child("employee").orderByChild("phone").equalTo(phone)
            query.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        nextQuery(phone,password)
                    }else{
                        Toast.makeText(applicationContext,"No.Handphone belum terdaftar atau Kode salah!!",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
                }

            })
        }


    }

    private fun nextQuery(phone: String, password: String) {
        mData = FirebaseDatabase.getInstance("https://persontracking-39122-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("PickUp")
        val query = mData.child("employee").orderByChild("password").equalTo(password)

        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val sPref = SharePref(applicationContext)
                    sPref.phone = phone
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                    finish()

                }else{
                    Toast.makeText(applicationContext,"Password Anda salah!!", Toast.LENGTH_SHORT).show()


                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadData:onCancelled", error.toException())
            }

        })
    }
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        mAuth = FirebaseAuth.getInstance()
//        val currentUser = mAuth.currentUser
//        if (currentUser != null) {
//            goLogin()
//        }else{
//            Toast.makeText(applicationContext,"Validasi no Handphone dahulu!!", Toast.LENGTH_SHORT).show()
//
//        }
//    }

}