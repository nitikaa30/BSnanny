package com.example.bsnanny.views.fragments.bookings

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.bookings.ParentBookingsAdapter
import com.example.bsnanny.databinding.FragmentBookingsBinding
import com.example.bsnanny.models.bookings.parentBooking.cancelBooking.CancelBookingBody
import com.example.bsnanny.models.findNanny.FindNannyApiItems
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.viewmodels.parentBooking.ParentBookingsViewModel
import com.example.bsnanny.viewmodels.parentBooking.cancelBooking.CancelBookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingsFragment : Fragment() {
    private lateinit var binding: FragmentBookingsBinding
    private var adapter: ParentBookingsAdapter? = null
    private val cancelBookingViewModel: CancelBookingViewModel by viewModels()
    private val parentBookingViewModel: ParentBookingsViewModel by viewModels()

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

    private fun subscribeObserver() {
        parentBookingViewModel.res.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResults.Error -> {

                }

                is NetworkResults.Loading -> {

                }

                is NetworkResults.Success -> {
                    val mList = it.data?.parentBookingData
                    adapter = mList?.let { it1 -> ParentBookingsAdapter(it1) }
                    binding.bookingRecyclerView.adapter = adapter

                    adapter?.itemClicked(object : ParentBookingsAdapter.OnBookingItemClicked {
                        override fun onCancelOrder(
                            position: Int,
                            parentBookingVieHolder: ParentBookingsAdapter.ParentBookingVieHolder
                        ) {
                            cancelBooking(CancelBookingBody(it.data!!.parentBookingData[position].id))
                            cancelBookingViewModel.res.observe(viewLifecycleOwner) { cancel ->
                                when (cancel) {
                                    is NetworkResults.Error -> {

                                    }

                                    is NetworkResults.Loading -> {

                                    }

                                    is NetworkResults.Success -> {
                                        showSnackBar(
                                            binding.root,
                                            "${cancel.data!!.msg}",
                                            "#FF000000"
                                        )

                                        parentBookingVieHolder.binding.bookingStatus.text = "Cancelled"
                                        parentBookingVieHolder.binding.bookingStatus.setTextColor(Color.parseColor("#FF0000"))
                                        parentBookingVieHolder.binding.bookingStatus.background =
                                            ContextCompat.getDrawable(requireContext(), R.drawable.view_details_status_cancelled)
                                        parentBookingVieHolder.binding.parentBookingCancelBtn.visibility = View.GONE
                                        parentBookingVieHolder.binding.parentBookingSearchBtn.visibility = View.GONE

                                    }
                                }
                            }
                        }

                        override fun onSearchOrder(
                            position: Int,
                            parentBookingVieHolder: ParentBookingsAdapter.ParentBookingVieHolder
                        ) {

                            val bundle = Bundle()
                            val findNannyApiItems = FindNannyApiItems(
                                latitude = it.data!!.parentBookingData[position].latitude,
                                longitude = it.data.parentBookingData[position].longitude,
                                price = it.data.parentBookingData[position].price.toString(),
                                noOfChildren = it.data.parentBookingData[position].numberOfChildren.toString(),
                                from = it.data.parentBookingData[position].from,
                                to = it.data.parentBookingData[position].to,
                                startDate = it.data.parentBookingData[position].startDate,
                                endDate = it.data.parentBookingData[position].endDate,
                                address = it.data.parentBookingData[position].address,
                                city = it.data.parentBookingData[position].city,
                                pin = it.data.parentBookingData[position].pin,
                                country = it.data.parentBookingData[position].country
                            )
                            bundle.putParcelable("findNannyItems", findNannyApiItems)
                            findNavController().navigate(R.id.action_bookingsFragment2_to_nav_graph_pro, bundle)
                        }

                        override fun onPayNow(
                            position: Int,
                            parentBookingVieHolder: ParentBookingsAdapter.ParentBookingVieHolder
                        ) {

                        }

                    })

                }
            }
        }
    }

    private fun cancelBooking(cancelBookingBody: CancelBookingBody) {
        cancelBookingViewModel.cancelParentBooking(cancelBookingBody)
    }

    private fun getBooking() {
        parentBookingViewModel.getBookingHistory()
    }
}