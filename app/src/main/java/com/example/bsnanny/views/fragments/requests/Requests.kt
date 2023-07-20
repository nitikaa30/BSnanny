package com.example.bsnanny.views.fragments.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.adapter.requests.ParentsRequestAdapter
import com.example.bsnanny.databinding.FragmentRequestsBinding
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.viewmodels.requests.RequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Requests : Fragment() {
    private lateinit var binding : FragmentRequestsBinding
    private lateinit var viewModel: RequestViewModel
    private lateinit var requestAdapter: ParentsRequestAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestsBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_requests, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(RequestViewModel::class.java)
        requestAdapter= ParentsRequestAdapter(arrayListOf(),this)
        binding.requestsRecyclerView.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=requestAdapter
        }
        viewModel.res.observe(viewLifecycleOwner, Observer {
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
                        val list = it.data.data
                      // requestAdapter.setRequests(list)
                    }
                }
            }
        })
        viewModel.getParentsRequests()

        requestAdapter.itemClicked(object :ParentsRequestAdapter.OnItemsClicked{
            override fun onClicked(position: Int) {

            }

        })
    }

}