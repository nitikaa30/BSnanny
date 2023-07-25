package com.example.bsnanny.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bsnanny.databinding.HireNannyItemsBinding
import com.example.bsnanny.models.HireNannyModel
import com.example.bsnanny.models.findNanny.getNanny.FeedbackData
import com.example.bsnanny.utils.Constants

class HireNannyAdapter (private val mList :ArrayList<FeedbackData>) :
    RecyclerView.Adapter<HireNannyAdapter.HireNannyViewHolder>() {
        private val limit = 3
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HireNannyViewHolder {
        context = parent.context
        val binding = HireNannyItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HireNannyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HireNannyViewHolder, position: Int) {
        Glide.with(context).load(Constants.BASE_URL+mList[position].parent.user.avatar).into(holder.image)
        holder.name.text = mList[position].parent.user.name
        holder.desc.text = mList[position].comment
    }
    override fun getItemCount(): Int {
        return if(mList.size > limit){
            limit
        } else {
            mList.size
        }
    }


    class HireNannyViewHolder(binding: HireNannyItemsBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.hireNannyRCVName
        val image = binding.hireNannyRCVImage
        val desc = binding.HireNannyDesc

    }
}
