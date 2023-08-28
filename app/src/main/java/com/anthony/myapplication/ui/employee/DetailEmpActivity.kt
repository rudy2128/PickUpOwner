package com.anthony.myapplication.ui.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.anthony.myapplication.R
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class DetailEmpActivity : AppCompatActivity() {
    private lateinit var empViewModel: EmpViewModel
    private lateinit var btnBack:ImageButton
    private lateinit var btnDel:ImageButton
    private lateinit var tvName:TextView
    private lateinit var tvPhone:TextView
    private lateinit var tvAddress:TextView
    private lateinit var tvArea:TextView
    private lateinit var tvStatus:TextView
    private lateinit var imgProf:ImageView
    private lateinit var phoneEmp:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_emp)
        btnBack = findViewById(R.id.btn_back)
        btnDel = findViewById(R.id.btn_delete)
        tvName = findViewById(R.id.tv_name)
        tvPhone = findViewById(R.id.tv_phone)
        tvAddress = findViewById(R.id.tv_address)
        tvArea = findViewById(R.id.tv_area)
        tvStatus = findViewById(R.id.tv_status)
        imgProf = findViewById(R.id.img_profile)

        btnBack.setOnClickListener {
            finish()
        }

        empViewModel = ViewModelProvider(this)[EmpViewModel::class.java]

        phoneEmp = intent.getStringExtra("PHONE").toString()
        empViewModel.getEmpByPhone(phoneEmp).observe(this@DetailEmpActivity){
            tvName.text = it.name.toString()
            tvPhone.text = it.phone.toString()
            tvAddress.text = it.address.toString()
            tvStatus.text = it.status.toString()
            tvArea.text = it.area.toString()
            val imgUrl = it.empUrl.toString()
            Glide.with(applicationContext)
                .load(imgUrl)
                .override(150,200)
                .into(imgProf)

        }

        btnDel.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus Data Karyawan")
        builder.setMessage("Yakin akan dihapus")

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            empViewModel.deleteById(phoneEmp)
            Toast.makeText(applicationContext,
                "Data $phoneEmp berhasil dihapus!!", Toast.LENGTH_SHORT).show()
            finish()
        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->

        }

        builder.setNeutralButton("Cancel") { _, _ ->

        }
        builder.show()
    }
}