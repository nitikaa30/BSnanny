package com.example.bsnanny.adapter.bookings

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.R
import com.example.bsnanny.databinding.BookingsItemsBinding
import com.example.bsnanny.models.bookings.parentBooking.ParentBookingData

class ParentBookingsAdapter(private val mLIst: ArrayList<ParentBookingData>) :
    RecyclerView.Adapter<ParentBookingsAdapter.ParentBookingVieHolder>() {

        private lateinit var context : Context
        interface OnBookingItemClicked{
            fun onCancelOrder(position: Int, parentBookingVieHolder: ParentBookingVieHolder)
            fun onSearchOrder(position: Int, parentBookingVieHolder: ParentBookingVieHolder)
            fun onPayNow(position: Int, parentBookingVieHolder: ParentBookingVieHolder)
        }
    private lateinit var mListener : OnBookingItemClicked

    fun itemClicked(listeners: OnBookingItemClicked){
        mListener = listeners
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentBookingVieHolder {
        context = parent.context
        val binding = BookingsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentBookingVieHolder(binding,mListener)

    }



    override fun onBindViewHolder(holder: ParentBookingVieHolder, position: Int) {
        with(holder){
            with(mLIst[position]){
                binding.bookingStartDate.text = "Start- $startDate"
                binding.bookingEndDate.text = "End- $endDate"
                binding.bookingFrom.text = "From- $from"
                binding.bookingEnd.text = "To- $to"

                binding.bookingNoChild.text = "No. of children- $numberOfChildren"
                binding.parentBookingAdd.text = address


                if (status =="Cancelled"){
                    binding.bookingStatus.text = "Cancelled"
                    binding.bookingStatus.setTextColor(Color.parseColor("#FF0000"))
                    binding.bookingStatus.background =ContextCompat.getDrawable(context, R.drawable.view_details_status_cancelled)
                    binding.parentBookingSearchBtn.visibility = View.GONE
                    binding.parentBookingCancelBtn.visibility = View.GONE
                }


                when(status){
                    "Cancelled" ->{
                        binding.bookingStatus.text = "Cancelled"
                        binding.bookingStatus.setTextColor(Color.parseColor("#FF0000"))
                        binding.bookingStatus.background =ContextCompat.getDrawable(context, R.drawable.view_details_status_cancelled)
                        binding.parentBookingSearchBtn.visibility = View.GONE
                        binding.parentBookingCancelBtn.visibility = View.GONE
                        binding.pendingBtnLayout.visibility = View.GONE
                        binding.payNowBtn.visibility = View.GONE
                    }
                    "Pending" ->{
                        binding.bookingStatus.text = "Pending"
                        binding.bookingStatus.setTextColor(Color.parseColor("#8E8EA1"))
                        binding.bookingStatus.background =ContextCompat.getDrawable(context, R.drawable.view_details_status_pending)
                        binding.parentBookingSearchBtn.visibility = View.VISIBLE
                        binding.parentBookingCancelBtn.visibility = View.VISIBLE
                        binding.pendingBtnLayout.visibility = View.VISIBLE
                        binding.payNowBtn.visibility = View.GONE
                    }
                    "Accepted" ->{
                        binding.bookingStatus.text = "Accepted"
                        binding.bookingStatus.setTextColor(Color.parseColor("#00C853"))
                        binding.bookingStatus.background =ContextCompat.getDrawable(context, R.drawable.view_details_status_accepted)
                        binding.pendingBtnLayout.visibility = View.INVISIBLE
                        binding.payNowBtn.visibility = View.VISIBLE
                    }


                }
            }
        }
    }
    override fun getItemCount(): Int {
        return mLIst.size
    }
    inner class ParentBookingVieHolder(val binding: BookingsItemsBinding, listeners: OnBookingItemClicked) :
        RecyclerView.ViewHolder(binding.root){
            init {
                binding.parentBookingCancelBtn.setOnClickListener {
                    listeners.onCancelOrder(bindingAdapterPosition, this)
                }
                binding.parentBookingSearchBtn.setOnClickListener {
                    listeners.onSearchOrder(bindingAdapterPosition, this)
                }
            }
        }

}
