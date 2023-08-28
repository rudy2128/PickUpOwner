package com.anthony.myapplication.ui.trx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Order

class OrderAdapter(private val orders:List<Order>):
RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){
    class OrderViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val tvOrder:TextView = v.findViewById(R.id.tv_order_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val v =LayoutInflater.from(parent.context).inflate(R.layout.item_order,parent,false)
        return OrderViewHolder(v)
    }

    override fun getItemCount(): Int {
       return orders.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.tvOrder.text = order.product!!.product_name
    }
}