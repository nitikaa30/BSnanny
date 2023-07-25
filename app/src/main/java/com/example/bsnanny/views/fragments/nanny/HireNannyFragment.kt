package com.example.bsnanny.views.fragments.nanny

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bsnanny.R
import com.example.bsnanny.adapter.HireNannyAdapter
import com.example.bsnanny.databinding.FragmentHireNannyBinding
import com.example.bsnanny.models.findNanny.inviteNanny.InviteNannyBody
import com.example.bsnanny.utils.Constants
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.viewmodels.nannies.getNannyDetails.GetNannyViewModel
import com.example.bsnanny.viewmodels.nannies.invite.InviteNannyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HireNannyFragment : Fragment() {
    private lateinit var binding: FragmentHireNannyBinding
    private lateinit var nannyId: String
    private val getNannyViewModel: GetNannyViewModel by viewModels()
    private val inviteNannyViewModel: InviteNannyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHireNannyBinding.inflate(inflater, container, false)
        // return inflater.inflate(R.layout.fragment_hire_nanny, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nannyId = arguments.let { HireNannyFragmentArgs.fromBundle(it!!).nannyId }


        getNannyData(nannyId)
        subscribeObservers()

        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(binding.hireNannyToolbar)
        if (activity?.supportActionBar != null) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        }
        binding.hireNannyToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.nannyCommentsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

//        mList.add(
//            HireNannyModel(
//                R.drawable.image,
//                "Alice",
//                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)
//            )
//        )
//        mList.add(
//            HireNannyModel(
//                R.drawable.image,
//                "Bob",
//                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)
//            )
//        )
//        mList.add(
//            HireNannyModel(
//                R.drawable.image,
//                "Edward",
//                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)
//            )
//        )


    }

    private fun subscribeObservers() {
        getNannyViewModel.res.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResults.Error -> {
                    Toast.makeText(requireContext(), "${it.errorMessage}", Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkResults.Loading -> {

                }

                is NetworkResults.Success -> {
                    Glide.with(requireContext())
                        .load(Constants.BASE_URL + it.data?.nannyBookingData?.nany?.avatar)
                        .into(binding.hireNannyImageView)
                        ?: binding.hireNannyImageView.setImageResource(R.drawable.image)
                    binding.hireNannyName.text = it.data?.nannyBookingData?.nany?.name ?: "User"
                    binding.hireNannyCountry.text =
                        it.data?.nannyBookingData?.nany?.city ?: "Country"
                    binding.hireNannyAge.text =
                        "${it.data?.nannyBookingData?.nany?.age.toString()} years old" ?: "00"
                    binding.hireNannyExperience.text =
                        "${it.data?.nannyBookingData?.nany?.experience} years of experience" ?: "00"
                    binding.hireNannyCost.text =
                        "₹${it.data?.nannyBookingData?.nany?.price}" ?: "0.0"
                    binding.hireNannyDescription.text = it.data?.nannyBookingData?.nany?.description
                        ?: getString(R.string.lorem_ipsum)
                    binding.hireNannyAddress.text =
                        it.data?.nannyBookingData?.nany?.address ?: getString(R.string.lorem_ipsum)
                    binding.startDate.setText(it.data?.nannyBookingData?.nannyBooking?.startDate)
                        ?: "YYYY-MM-DD"
                    binding.endDate.setText(it.data?.nannyBookingData?.nannyBooking?.endDate)
                        ?: "YYYY-MM-DD"
                    binding.FromED.setText(it.data?.nannyBookingData?.nannyBooking?.from) ?: "XX:YY"
                    binding.TOED.setText(it.data?.nannyBookingData?.nannyBooking?.to) ?: "XX:YY"

                    val mList = it.data?.nannyBookingData?.nany?.nany?.feedbacksData

                    val adapter = mList?.let { it1 -> HireNannyAdapter(it1) }
                    binding.shimmerLayout.startShimmer()
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.INVISIBLE
                        binding.nannyCommentsRecyclerView.visibility = View.VISIBLE
                        binding.nannyCommentsRecyclerView.adapter = adapter
                    }, 3000)



                    binding.startDate.isEnabled = false
                    binding.endDate.isEnabled = false
                    binding.FromED.isEnabled = false
                    binding.TOED.isEnabled = false

                    binding.invite.setOnClickListener { _ ->
                        val inviteNannyBody =
                            InviteNannyBody(it.data?.nannyBookingData?.nany?.nany?.id.toString())
                        inviteNanny(inviteNannyBody)
                        inviteNannyViewModel.res.observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is NetworkResults.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "${result.errorMessage}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                                is NetworkResults.Loading -> {

                                }

                                is NetworkResults.Success -> {
                                    findNavController().navigate(R.id.action_hireNannyFragment_to_congratulations)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getNannyData(userId: String) {
        getNannyViewModel.getNannyDetails(userId)
    }

    private fun inviteNanny(inviteNannyBody: InviteNannyBody) {
        inviteNannyViewModel.inviteNanny(inviteNannyBody)
    }
}