package com.example.bsnanny.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.databinding.RatingsItemsBinding
import com.example.bsnanny.models.feedbackModel.FeedbackBody

class FeedbackAdapter(private val mlist:ArrayList<FeedbackBody> ): RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedbackViewHolder {
        val binding= RatingsItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FeedbackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedbackAdapter.FeedbackViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class FeedbackViewHolder(binding: RatingsItemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}