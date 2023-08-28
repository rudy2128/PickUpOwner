package com.anthony.myapplication.ui.report

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Trx
import com.anthony.myapplication.helper.DateHelper
import com.anthony.myapplication.ui.MainActivity
import com.anthony.myapplication.ui.employee.EmployeeActivity
import com.anthony.myapplication.ui.setting.SettingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReportActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var tvQty:TextView
    private lateinit var reportViewModel: ReportViewModel
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var rvTrx:RecyclerView
    private lateinit var btnNav: BottomNavigationView
    private var trxList = arrayListOf<Trx>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        btnNav = findViewById(R.id.bottom_navigation)
        rvTrx = findViewById(R.id.rvTrx)
        tvQty = findViewById(R.id.tv_qty)
        searchView = findViewById(R.id.search_trx)

        reportViewModel = ViewModelProvider(this)[ReportViewModel::class.java]

        rvTrx.setHasFixedSize(false)
        rvTrx.layoutManager = LinearLayoutManager(applicationContext)
        reportAdapter = ReportAdapter(trxList)

        val now = DateHelper.currentDate

        reportViewModel.getAllTrx(now).observe(this@ReportActivity){
            trxList.clear()
            trxList.addAll(it)
            rvTrx.adapter = reportAdapter
            val count = trxList.size
            tvQty.text = count.toString()


        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList(newText)
                return true
            }


        })
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
                    startActivity(Intent(applicationContext, SettingActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
                    return@setOnItemSelectedListener true

                }
            }

            false
        }
    }

    private fun searchList(text: String?) {
        val searchList = ArrayList<Trx>()
        for (dataPro in trxList){
            if (dataPro.customers!!.name?.lowercase()?.contains(text!!.lowercase()) == true ||
                dataPro.startDate!!.lowercase()?.contains(text!!.lowercase()) == true ){
                searchList.add(dataPro)

            }
        }
        reportAdapter.searchDataList(searchList)
    }
}