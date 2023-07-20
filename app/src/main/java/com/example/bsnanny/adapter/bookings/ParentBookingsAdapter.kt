package com.example.bsnanny.adapter.bookings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.databinding.BookingsItemsBinding
import com.example.bsnanny.models.bookings.parentBooking.ParentBookingData

class ParentBookingsAdapter(private val mLIst: ArrayList<ParentBookingData>) :
    RecyclerView.Adapter<ParentBookingsAdapter.ParentBookingVieHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentBookingVieHolder {
        val binding = BookingsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentBookingVieHolder(binding)
    }



    override fun onBindViewHolder(holder: ParentBookingVieHolder, position: Int) {
        with(holder){
            with(mLIst[position]){
                binding.bookingStartDate.append(startDate)
                binding.bookingEndDate.append(endDate)
                binding.bookingFrom.append(from)
                binding.bookingEnd.append(to)
                binding.bookingNoChild.append(numberOfChildren.toString())
                binding.parentBookingAdd.text = address
            }
        }
    }
    override fun getItemCount(): Int {
        return mLIst.size
    }
    inner class ParentBookingVieHolder(val binding: BookingsItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

}
