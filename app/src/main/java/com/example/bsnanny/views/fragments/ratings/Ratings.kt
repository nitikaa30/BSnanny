package com.example.bsnanny.views.fragments.ratings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.R
import com.example.bsnanny.adapter.feedback.FeedbackAdapter
import com.example.bsnanny.databinding.FragmentRatingsBinding
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.viewmodels.feedback.FeedbackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Ratings : Fragment() {

    private lateinit var binding:FragmentRatingsBinding
    private val viewModel: FeedbackViewModel by viewModels()
    private lateinit var feedbackAdapter: FeedbackAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRatingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedbackAdapter = FeedbackAdapter(ArrayList())
        binding.ratingRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedbackAdapter
        }
        viewModel.new.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResults.Error -> {
                    ProgressDialog.cancelProgressDialog()
                    showSnackBar(binding.root, it.errorMessage.toString(), "#AA4A44")
                }

                is NetworkResults.Loading -> {
                    ProgressDialog.showProgressDialog(requireContext())
                }
                is NetworkResults.Success -> {
                    ProgressDialog.cancelProgressDialog()
                    if (it.data?.success == true) {
                        val feedbackList = it.data.feedbacks
                        feedbackAdapter.setFeedbacks(feedbackList)
                    }
                }
            }
        })
        viewModel.getList()
    }

}