package com.anthony.myapplication.ui.admob

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Admob
import com.anthony.myapplication.entity.Advert
import com.bumptech.glide.Glide

class AdvertAdapter(private var listAdv:List<Advert>):
RecyclerView.Adapter<AdvertAdapter.AddViewHolder>(){
    class AddViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val admobUrl:ImageView =v.findViewById(R.id.image_adds)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_banner,parent,false)
        return AddViewHolder(v)
    }

    override fun getItemCount(): Int {
       return listAdv.size
    }

    override fun onBindViewHolder(holder: AddViewHolder, position: Int) {
       val advert = listAdv[position]
        Glide.with(holder.itemView.context)
            .load(advert.admobUrl)
            .override(120,120)
            .error(R.drawable.baseline_account_circle_24)
            .into(holder.admobUrl)
        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context,DetailAddActivity::class.java)
            i.putExtra("IMG_URL",advert.admobUrl)
            i.putExtra("ADD_ID",advert.admobId)
            i.putExtra("DESC",advert.description)
            holder.itemView.context.startActivity(i)

        }
    }
}