package com.example.bsnanny.adapter.findNanny

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bsnanny.databinding.NannyItemsBinding
import com.example.bsnanny.models.findNanny.FindNanny
import com.example.bsnanny.models.findNanny.FindNannyData
import com.example.bsnanny.models.findNanny.Nanny
import com.example.bsnanny.utils.Constants

class FindNannyAdapter(private var mList: ArrayList<FindNannyData>?) :
    RecyclerView.Adapter<FindNannyAdapter.FindNannyViewHolder>() {

        private lateinit var context : Context
    interface OnItemsClicked {
        fun onClicked(position: Int)
    }

    private lateinit var mListeners: OnItemsClicked

    fun itemClicked(listeners: OnItemsClicked) {
        mListeners = listeners
    }

    fun filteredList(mList: ArrayList<FindNannyData>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindNannyViewHolder {
        context = parent.context
        val binding = NannyItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FindNannyViewHolder(binding, mListeners)
    }

    override fun onBindViewHolder(holder: FindNannyViewHolder, position: Int) {
        with(holder) {
            with(mList?.get(position) ) {
                binding.name.text = mList?.get(position)?.name
                binding.place.text = mList?.get(position)?.city
                binding.age.text = "${mList?.get(position)?.age} year old"
               binding.ratebar.rating = (mList?.get(position)?.averageRating?.toFloat()) ?:0.0F
                binding.nannyRating.text = mList!![position].averageRating.toString()
                Glide.with(context).load(Constants.BASE_URL + mList!![position].avatar).into(binding.nannyImage)
                binding.price.text = "₹${mList?.get(position)!!.price}"
            }
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }


    inner class FindNannyViewHolder(val binding: NannyItemsBinding, listeners: OnItemsClicked) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listeners.onClicked(position = bindingAdapterPosition)
            }
        }
    }

}