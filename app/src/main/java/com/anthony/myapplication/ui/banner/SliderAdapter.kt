package com.anthony.myapplication.ui.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Banner
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val listBanner:List<Banner>):
    RecyclerView.Adapter<SliderAdapter.SlideViewHolder>(){

    class SlideViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView!!.findViewById(R.id.img_admob)
        val tvTitle:TextView = itemView.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val v = LayoutInflater.from(parent!!.context).inflate(R.layout.item_adv, parent,false)
        return SlideViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listBanner.size
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val ban = listBanner[position]
        holder.tvTitle.text = ban.title
        Glide.with(holder.imageView.context)
            .load(ban.bannerUrl)
            .override(300,150)
            .into(holder.imageView)

    }
}