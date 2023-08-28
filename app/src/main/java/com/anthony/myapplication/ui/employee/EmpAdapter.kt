package com.anthony.myapplication.ui.employee

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Employee

class EmpAdapter(private var empList:List<Employee>):
RecyclerView.Adapter<EmpAdapter.EmpViewHolder>(){
    class EmpViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val tvArea:TextView = v.findViewById(R.id.tv_area)
        val tvName:TextView = v.findViewById(R.id.tv_name)
        val tvPhone:TextView = v.findViewById(R.id.tv_phone)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_employee,parent,false)
        return EmpViewHolder(v)
    }

    override fun getItemCount(): Int {
       return empList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun searchDataList(searchList:ArrayList<Employee>){
        empList = searchList
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {
       val emp = empList[position]
        holder.tvArea.text = emp.area
        holder.tvName.text = emp.name
        holder.tvPhone.text ="0"+emp.phone
        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context,DetailEmpActivity::class.java)
            i.putExtra("NAME",emp.name)
            i.putExtra("PHONE",emp.phone)
            i.putExtra("ADDRESS",emp.address)
            i.putExtra("AREA",emp.area)
            i.putExtra("STATUS",emp.status)
            i.putExtra("URL",emp.empUrl)
            holder.itemView.context.startActivity(i)
        }

    }
}