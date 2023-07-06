package com.example.bsnanny.adapter.feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bsnanny.R
import com.example.bsnanny.databinding.RatingsItemsBinding
import com.example.bsnanny.models.feedbackModel.FeedbackBody
import com.example.bsnanny.models.feedbackModel.FeedbackList
import java.util.concurrent.TimeUnit

class FeedbackAdapter(private val mlist: ArrayList<FeedbackList>)
    : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val binding = RatingsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedbackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(mlist[position])
    }

    override fun getItemCount(): Int {
        return mlist.size
    }
    fun setFeedbacks(newFeedbacks: List<FeedbackList>) {
        mlist.clear()
        mlist.addAll(newFeedbacks)
        notifyDataSetChanged()
    }

    inner class FeedbackViewHolder(private val binding: RatingsItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedbackList: FeedbackList) {
            binding.apply {
                ratingStar.rating=feedbackList.stars.toFloat()
                ratingName.text=feedbackList.nanny.name
                messageRating.text=feedbackList.comment
                val elapsedTime = calculateElapsedTime(feedbackList.timestamp)
                timeRating.text=elapsedTime
                Glide.with(itemView)
                    .load(feedbackList.imageUrl)
                    .into(ratingImage)
            }
        }
        private fun calculateElapsedTime(timestamp: Long): String {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMillis = currentTime - timestamp

            val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTimeMillis)
            val hours = TimeUnit.MILLISECONDS.toHours(elapsedTimeMillis)

            return if (hours > 0) {
                itemView.resources.getQuantityString(R.plurals.hours_ago, hours.toInt(), hours)
            } else {
                itemView.resources.getQuantityString(R.plurals.minutes_ago, minutes.toInt(), minutes)
            }
        }
    }
}

