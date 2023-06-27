package com.example.bsnanny.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.databinding.HireNannyItemsBinding
import com.example.bsnanny.models.HireNannyModel

class HireNannyAdapter (private val mList :ArrayList<HireNannyModel>) :
    RecyclerView.Adapter<HireNannyAdapter.HireNannyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HireNannyViewHolder {
        val binding = HireNannyItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HireNannyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HireNannyViewHolder, position: Int) {
        holder.image.setImageResource(mList[position].photo)
        holder.name.text = mList[position].name
        holder.desc.text = mList[position].desc
    }
    override fun getItemCount(): Int {
        return mList.size
    }


    class HireNannyViewHolder(binding: HireNannyItemsBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.hireNannyRCVName
        val image = binding.hireNannyRCVImage
        val desc = binding.HireNannyDesc

    }
}
