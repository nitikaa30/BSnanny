package com.example.bsnanny.adapter.families

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bsnanny.adapter.findNanny.FindNannyAdapter
import com.example.bsnanny.databinding.FamiliesItemsBinding
import com.example.bsnanny.models.findNanny.FindNanny
import com.example.bsnanny.models.findfamilies.Data

class FindFamilyAdapter(private var mList:ArrayList<Data>):
    RecyclerView.Adapter<FindFamilyAdapter.FindFamilyViewHolder>() {

    interface OnItemsClicked {
        fun onClicked(position: Int)
    }

    private lateinit var mListeners: OnItemsClicked

    fun itemClicked(listeners: OnItemsClicked) {
        mListeners = listeners
    }
    fun setRequests(requests: List<Data>) {
        mList.clear()
        mList.addAll(requests)
        notifyDataSetChanged()
    }

    fun filteredList(mList: ArrayList<Data>) {
        this.mList = mList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FindFamilyAdapter.FindFamilyViewHolder {
        val binding= FamiliesItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FindFamilyViewHolder(binding,mListeners)
    }

    override fun onBindViewHolder(holder: FindFamilyAdapter.FindFamilyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class FindFamilyViewHolder
        (val binding:FamiliesItemsBinding,listeners:FindFamilyAdapter.OnItemsClicked)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(list:Data){
            binding.name.text= list.Parent.User.name
            binding.place.text= list.Parent.User.city.toString()
            binding.noOfChild.text=list.no_of_children.toString()
            Glide.with(itemView).load(list.Parent.User.avatar).into(binding.requestsImage)
        }

        init {
            binding.root.setOnClickListener {
                listeners.onClicked(position = bindingAdapterPosition)
            }
        }
    }
}