package com.anthony.myapplication.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.anthony.myapplication.R
import com.anthony.myapplication.helper.MyPref
import com.anthony.myapplication.helper.SharePref
import com.anthony.myapplication.ui.MainActivity
import com.anthony.myapplication.ui.admob.InputAdActivity
import com.anthony.myapplication.ui.admob.InputAdvActivity
import com.anthony.myapplication.ui.banner.InputBannerActivity
import com.anthony.myapplication.ui.employee.EmployeeActivity
import com.anthony.myapplication.ui.login.LoginActivity
import com.anthony.myapplication.ui.report.ReportActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingActivity : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var cdBanner:CardView
    private lateinit var cdLogout:CardView
    private lateinit var cdAdmob:CardView
    private lateinit var cdAdv:CardView
    private lateinit var btnNav:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        btnBack = findViewById(R.id.btn_back)
        cdLogout = findViewById(R.id.card_logout)
        cdBanner = findViewById(R.id.card_banner)
        cdAdmob = findViewById(R.id.card_admob)
        cdAdv = findViewById(R.id.card_adv)
        btnNav = findViewById(R.id.bottom_navigation)

        btnBack.setOnClickListener {
            finish()
        }
        cdLogout.setOnClickListener {
            showAlertDialog()
        }

        cdAdv.setOnClickListener {
            val i = Intent(applicationContext, InputAdvActivity::class.java)
            startActivity(i)
            finish()

        }

        cdAdmob.setOnClickListener {
            val i = Intent(applicationContext, InputAdActivity::class.java)
            startActivity(i)
            finish()

        }

        cdBanner.setOnClickListener {
            val i = Intent(applicationContext, InputBannerActivity::class.java)
            startActivity(i)
            finish()

        }

        btnNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_home -> {
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.page_employee -> {
                    val i = Intent(applicationContext, EmployeeActivity::class.java)
                    startActivity(i)
                    finish()
                    return@setOnItemSelectedListener true

                }
                R.id.page_news -> {
                    val i = Intent(applicationContext, ReportActivity::class.java)
                    startActivity(i)
                    finish()
                    return@setOnItemSelectedListener true

                }
                R.id.page_setting -> {
                    startActivity(Intent(applicationContext,SettingActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
                    return@setOnItemSelectedListener true

                }
            }

            false
        }


    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus")
        builder.setMessage("Yakin akan keluar?")

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val sPref = SharePref(applicationContext)
            sPref.deletePhone()
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
            Toast.makeText(applicationContext,
                "Berhasil logout!!", Toast.LENGTH_SHORT).show()
            finish()
        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
            Toast.makeText(applicationContext,
                "Cancel logout", Toast.LENGTH_SHORT).show()

        }

        builder.setNeutralButton("Cancel") { _, _ ->
            Toast.makeText(applicationContext,
                "Cancel logout", Toast.LENGTH_SHORT).show()

        }
        builder.show()
    }
}