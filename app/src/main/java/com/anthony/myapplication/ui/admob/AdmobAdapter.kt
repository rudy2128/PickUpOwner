package com.anthony.myapplication.ui.admob

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Admob
import com.bumptech.glide.Glide

class AdmobAdapter(private var listAdds:List<Admob>):
RecyclerView.Adapter<AdmobAdapter.AddViewHolder>(){
    class AddViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val admobUrl:ImageView =v.findViewById(R.id.image_adds)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_banner,parent,false)
        return AddViewHolder(v)
    }

    override fun getItemCount(): Int {
       return listAdds.size
    }

    override fun onBindViewHolder(holder: AddViewHolder, position: Int) {
       val admob = listAdds[position]
        Glide.with(holder.itemView.context)
            .load(admob.admobUrl)
            .override(120,120)
            .error(R.drawable.baseline_account_circle_24)
            .into(holder.admobUrl)
        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context,DetailAddActivity::class.java)
            i.putExtra("IMG_URL",admob.admobUrl)
            i.putExtra("ADD_ID",admob.admobId)
            i.putExtra("DESC",admob.description)
            holder.itemView.context.startActivity(i)

        }
    }
}