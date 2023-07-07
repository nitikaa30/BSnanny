package com.example.bsnanny.adapter.findNanny

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.databinding.NannyItemsBinding
import com.example.bsnanny.models.findNanny.FindNanny

class FindNannyAdapter(private var mList: ArrayList<FindNanny>) :
    RecyclerView.Adapter<FindNannyAdapter.FindNannyViewHolder>() {

    interface OnItemsClicked {
        fun onClicked(position: Int)
    }

    private lateinit var mListeners: OnItemsClicked

    fun itemClicked(listeners: OnItemsClicked) {
        mListeners = listeners
    }

    fun filteredList(mList: ArrayList<FindNanny>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindNannyViewHolder {
        val binding = NannyItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FindNannyViewHolder(binding, mListeners)
    }

    override fun onBindViewHolder(holder: FindNannyViewHolder, position: Int) {
        with(holder) {
            with(mList[position]) {
                binding.name.text = name
                binding.age.text = age
                binding.nannyImage.setImageResource(image)
                binding.place.text = place
                binding.ratebar.rating = ratingBar
                binding.nannyRating.text = rating
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
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