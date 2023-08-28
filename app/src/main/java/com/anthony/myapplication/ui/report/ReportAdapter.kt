package com.anthony.myapplication.ui.report

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Trx
import com.anthony.myapplication.ui.history.DetailHisActivity

class ReportAdapter(private var trxList: List<Trx>):
RecyclerView.Adapter<ReportAdapter.TrxViewHolder>(){
    class TrxViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val tvArea:TextView = v.findViewById(R.id.tv_area)
        val tvName:TextView = v.findViewById(R.id.tv_name)
        val tvOrder:TextView = v.findViewById(R.id.tv_order_name)
        val tvDate:TextView = v.findViewById(R.id.tv_date)
        val tvTime:TextView = v.findViewById(R.id.tv_time)
        val tvStatus:TextView = v.findViewById(R.id.tv_status)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrxViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_trx,parent,false)
        return TrxViewHolder(v)
    }

    override fun getItemCount(): Int {
       return trxList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun searchDataList(searchList:ArrayList<Trx>){
        trxList = searchList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrxViewHolder, position: Int) {
       val trx = trxList[position]
        holder.tvArea.text = trx.customers!!.address
        holder.tvName.text = trx.customers!!.name
        holder.tvTime.text = trx.timeDate
        holder.tvStatus.text = trx.status
        holder.tvOrder.text = trx.orders!!.listIterator().next().product!!.product_name
        holder.tvDate.text = trx.startDate

    }
}