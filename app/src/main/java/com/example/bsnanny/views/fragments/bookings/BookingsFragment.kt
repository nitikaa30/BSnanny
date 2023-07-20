package com.example.bsnanny.views.fragments.bookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.adapter.bookings.ParentBookingsAdapter
import com.example.bsnanny.databinding.FragmentBookingsBinding
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.viewmodels.parentBooking.ParentBookingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingsFragment : Fragment() {
    private lateinit var binding : FragmentBookingsBinding
    private val parentBookingViewModel : ParentBookingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        getBooking()
        subscribeObserver()
    }
    private fun subscribeObserver(){
        parentBookingViewModel.res.observe(viewLifecycleOwner){
            when(it){
                is NetworkResults.Error -> {

                }
                is NetworkResults.Loading -> {

                }
                is NetworkResults.Success -> {
                    val mList = it.data?.parentBookingData
                    val adapter = mList?.let { it1 -> ParentBookingsAdapter(it1) }
                    binding.bookingRecyclerView.adapter = adapter

                }
            }
        }
    }
    private fun getBooking(){
        parentBookingViewModel.getBookingHistory()
    }
}