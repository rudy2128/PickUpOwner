package com.anthony.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Admob
import com.anthony.myapplication.entity.Advert
import com.anthony.myapplication.entity.Banner
import com.anthony.myapplication.ui.admob.AddViewModel
import com.anthony.myapplication.ui.admob.AdmobAdapter
import com.anthony.myapplication.ui.admob.AdvertAdapter
import com.anthony.myapplication.ui.banner.BannerViewModel
import com.anthony.myapplication.ui.banner.SliderAdapter
import com.anthony.myapplication.ui.employee.EditProfileActivity
import com.anthony.myapplication.ui.employee.EmpViewModel
import com.anthony.myapplication.ui.employee.EmployeeActivity
import com.anthony.myapplication.ui.history.HistoryActivity
import com.anthony.myapplication.ui.report.ReportActivity
import com.anthony.myapplication.ui.setting.SettingActivity
import com.anthony.myapplication.ui.trx.TrxActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smarteist.autoimageslider.SliderView

class MainActivity : AppCompatActivity() {
    private lateinit var rvAdv: RecyclerView
    private lateinit var rvBanner: RecyclerView
    private lateinit var btnOrder: ImageButton
    private lateinit var btnHis: ImageButton
    private lateinit var tvName: TextView
    private lateinit var tvStatus:TextView
    private lateinit var btnNav:BottomNavigationView
    private lateinit var phone:String
    private lateinit var empViewModel: EmpViewModel
    private lateinit var imgProf:ImageView
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    private lateinit var admobAdapter: AdmobAdapter
    private lateinit var bannerViewModel: BannerViewModel
    private lateinit var addViewModel: AddViewModel
    private lateinit var name:String
    private lateinit var imgProfile:String
    private var listBanner = arrayListOf<Banner>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnOrder = findViewById(R.id.btn_order)
        btnHis = findViewById(R.id.btn_history)
        tvName = findViewById(R.id.tv_name)
        tvStatus = findViewById(R.id.tv_status)
        imgProf = findViewById(R.id.img_profile)
        btnNav = findViewById(R.id.bottom_navigation)
        rvAdv = findViewById(R.id.rvAdv)
        rvBanner = findViewById(R.id.rvBanner)



        empViewModel = ViewModelProvider(this)[EmpViewModel::class.java]
        bannerViewModel = ViewModelProvider(this)[BannerViewModel::class.java]
        addViewModel = ViewModelProvider(this)[AddViewModel::class.java]

        empViewModel.getEmpById().observe(this@MainActivity) {
            imgProfile = it.empUrl.toString()
            name = it.name.toString()
            phone = it.phone.toString()
            val status = it.status
            tvName.text = name
            tvStatus.text = status
            Glide.with(applicationContext)
                .load(imgProfile)
                .override(70, 70)
                .error(R.drawable.baseline_account_circle_24)
                .into(imgProf)

        }
        val listAdds = arrayListOf<Admob>()
        rvBanner.setHasFixedSize(false)
        rvBanner.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        admobAdapter = AdmobAdapter(listAdds)
        addViewModel.getAllAdd().observe(this) {
            listAdds.clear()
            listAdds.addAll(it)
            rvBanner.adapter = admobAdapter
        }




        rvAdv.setHasFixedSize(false)
        rvAdv.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        sliderAdapter = SliderAdapter(listBanner)
        bannerViewModel.getAllBanner().observe(this@MainActivity) {
            listBanner.clear()
            listBanner.addAll(it)
            rvAdv.adapter = sliderAdapter


        }


        imgProf.setOnClickListener {
            val i =Intent(applicationContext,EditProfileActivity::class.java)
            startActivity(i)
        }
        btnOrder.setOnClickListener {
            val i = Intent(applicationContext, TrxActivity::class.java)
            startActivity(i)
        }
        btnHis.setOnClickListener {
            val i = Intent(applicationContext, HistoryActivity::class.java)
            startActivity(i)

        }

        btnNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_home -> {
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                    overridePendingTransition(0,0)
                    return@setOnItemSelectedListener true
                }
                R.id.page_employee -> {
                    startActivity(Intent(applicationContext,EmployeeActivity::class.java))
                    overridePendingTransition(0,0)
                    return@setOnItemSelectedListener true

                }
                R.id.page_news -> {
                    startActivity(Intent(applicationContext,ReportActivity::class.java))
                    overridePendingTransition(0,0)
                    return@setOnItemSelectedListener true

                }
                R.id.page_setting -> {
                    startActivity(Intent(applicationContext,SettingActivity::class.java))
                    overridePendingTransition(0,0)
                    return@setOnItemSelectedListener true

                }
            }

            false
        }


    }

}