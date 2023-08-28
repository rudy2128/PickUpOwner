package com.anthony.myapplication.ui.reg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Employee
import com.anthony.myapplication.ui.employee.EmpViewModel
import com.anthony.myapplication.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText

class RegActivity : AppCompatActivity() {
    private lateinit var edtName: TextInputEditText
    private lateinit var edtKec: TextInputEditText
    private lateinit var edtAddress: TextInputEditText
    private lateinit var edtPhone: TextInputEditText
    private lateinit var edtPass: TextInputEditText
    private lateinit var edtPassword: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var empViewModel: EmpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        edtName = findViewById(R.id.edt_name)
        edtKec = findViewById(R.id.edt_kec)
        edtAddress = findViewById(R.id.edt_address)
        edtPass = findViewById(R.id.edt_pass)
        edtPassword = findViewById(R.id.edt_password)
        edtPhone = findViewById(R.id.edt_phone)
        btnSave = findViewById(R.id.btn_save)
        empViewModel = ViewModelProvider(this@RegActivity)[EmpViewModel::class.java]

        val phone = intent.getStringExtra("PHONE")
        edtPhone.setText(phone)

        btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val name = edtName.text.toString()
        val area = edtKec.text.toString()
        val phone = edtPhone.text.toString()
        val address = edtAddress.text.toString()
        val pass = edtPass.text.toString()
        val empUrl = ""
        val password = edtPassword.text.toString()
        if (name.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()&& area.isNotEmpty() && pass.isNotEmpty() && password.isNotEmpty()){
            if (pass == password){
                val employee = Employee(phone, name,area,address,password, empUrl)
                empViewModel.saveEmployee(employee,phone)
                Toast.makeText(applicationContext,"Pendaftaran Berhasil!!", Toast.LENGTH_SHORT).show()
                val i = Intent(applicationContext, LoginActivity::class.java)
                i.putExtra("PHONE",phone)
                startActivity(i)
                finish()

            }else{
                Toast.makeText(applicationContext,"Password tidak sama!!", Toast.LENGTH_SHORT).show()

            }

        }else{
            Toast.makeText(applicationContext,"Masih ada yang kosong!!", Toast.LENGTH_SHORT).show()
        }

    }

}