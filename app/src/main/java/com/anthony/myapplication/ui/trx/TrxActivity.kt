package com.anthony.myapplication.ui.trx

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Trx

class TrxActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var tvQty: TextView
    private lateinit var rvTrx: RecyclerView
    private lateinit var trxViewModel: TrxViewModel
    private lateinit var trxAdapter: TrxAdapter
    private lateinit var searchView: SearchView
    private var trxList = arrayListOf<Trx>()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trx)
        tvQty = findViewById(R.id.tv_qty_trx)
        rvTrx = findViewById(R.id.rvTrx)
        searchView = findViewById(R.id.search_trx)
        btnBack = findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            finish()
        }

        trxViewModel = ViewModelProvider(this@TrxActivity)[TrxViewModel::class.java]

        rvTrx.setHasFixedSize(false)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        val code = "1"
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        rvTrx.layoutManager = mLayoutManager
        trxAdapter = TrxAdapter(trxList,code)

        trxViewModel.getAllTrx().observe(this){
            trxList.clear()
            trxList.addAll(it)
            val count = trxList.size
            tvQty.text = "$count Order"
            rvTrx.adapter = trxAdapter

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
        val searchList = ArrayList<Trx>()
        for (dataPro in trxList){
            if (dataPro.customers!!.name?.lowercase()?.contains(text!!.lowercase()) == true ||
                dataPro.customers!!.address?.lowercase()?.contains(text!!.lowercase()) == true ){
                searchList.add(dataPro)

            }
        }
        trxAdapter.searchDataList(searchList)
    }
}