package com.anthony.myapplication.ui.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Employee
import com.anthony.myapplication.entity.Trx
import com.anthony.myapplication.ui.MainActivity
import com.anthony.myapplication.ui.report.ReportActivity
import com.anthony.myapplication.ui.setting.SettingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class EmployeeActivity : AppCompatActivity() {
    private lateinit var btnNav:BottomNavigationView
    private lateinit var btnBack:ImageButton
    private lateinit var searchView: SearchView
    private lateinit var tvQty:TextView
    private lateinit var empViewModel: EmpViewModel
    private lateinit var rvEmp:RecyclerView
    private lateinit var empAdapter:EmpAdapter
    private var empList= arrayListOf<Employee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        btnNav = findViewById(R.id.bottom_navigation)
        searchView = findViewById(R.id.search_employee)
        tvQty = findViewById(R.id.tv_qty)
        rvEmp = findViewById(R.id.rvEmp)
        btnBack = findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            finish()
        }
        empViewModel = ViewModelProvider(this@EmployeeActivity)[EmpViewModel::class.java]

        rvEmp.setHasFixedSize(false)
        rvEmp.layoutManager = LinearLayoutManager(applicationContext)
        empAdapter = EmpAdapter(empList)
        empViewModel.getAllEmployee().observe(this@EmployeeActivity){
            empList.clear()
            empList.addAll(it)
            rvEmp.adapter = empAdapter
            val count = empList.size
            tvQty.text = count.toString()
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
                    return@setOnItemSelectedListener true

                }
                R.id.page_news -> {
                    val i = Intent(applicationContext, ReportActivity::class.java)
                    startActivity(i)
                    finish()
                    return@setOnItemSelectedListener true

                }
                R.id.page_setting -> {
                    val i = Intent(applicationContext, SettingActivity::class.java)
                    startActivity(i)
                    finish()
                    return@setOnItemSelectedListener true

                }
            }

            false
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
    }

    private fun searchList(text: String?) {
        val searchList = ArrayList<Employee>()
        for (dataPro in empList){
            if (dataPro.name!!.lowercase().contains(text!!.lowercase()) || dataPro.area!!.lowercase().contains(text.lowercase())
            ){
                searchList.add(dataPro)

            }
        }
        empAdapter.searchDataList(searchList)
    }
}