package com.anthony.myapplication.ui.admob

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anthony.myapplication.R
import com.anthony.myapplication.helper.SharePref
import com.anthony.myapplication.ui.MainActivity
import com.bumptech.glide.Glide

class DetailAddActivity : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var imgAdd:ImageView
    private lateinit var tvDesc:TextView
    private lateinit var btnDel:Button
    private lateinit var adViewModel: AddViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_add)
        imgAdd = findViewById(R.id.img_admob)
        tvDesc = findViewById(R.id.tv_desc)
        btnBack = findViewById(R.id.btn_back)
        btnDel = findViewById(R.id.btn_delete)

        adViewModel = ViewModelProvider(this@DetailAddActivity)[AddViewModel::class.java]

        val imgUrl = intent.getStringExtra("IMG_URL")
        val admobId = intent.getStringExtra("ADD_ID")
        val description = intent.getStringExtra("DESC")

        Glide.with(applicationContext)
            .load(imgUrl)
            .override(150,150)
            .into(imgAdd)
        tvDesc.text = description

        btnBack.setOnClickListener {
            finish()
        }
        btnDel.setOnClickListener {
            showAlertDialog(admobId!!)
        }

    }

    private fun showAlertDialog(admobId:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus")
        builder.setMessage("Yakin akan dihapus?")

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val sPref = SharePref(applicationContext)
            sPref.deletePhone()
            adViewModel.deleteById(admobId)
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            Toast.makeText(applicationContext,
                "Berhasil dihapus!!", Toast.LENGTH_SHORT).show()
            finish()
        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->

        }

        builder.setNeutralButton("Cancel") { _, _ ->

        }
        builder.show()
    }
}