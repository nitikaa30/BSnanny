package com.example.bsnanny.views.fragments.families

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.families.FindFamilyAdapter
import com.example.bsnanny.databinding.FragmentFindFamiliesBinding
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.fragmentTransactions.FragmentTransactions
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.viewmodels.families.FindFamiliesViewModel


class FindFamilies : Fragment() {

    private lateinit var binding: FragmentFindFamiliesBinding
    private lateinit var viewmodel:FindFamiliesViewModel
    private lateinit var findadapter: FindFamilyAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFindFamiliesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel= ViewModelProvider(this)[FindFamiliesViewModel::class.java]
        findadapter= FindFamilyAdapter(arrayListOf())
        binding.familiesRecyclerView.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=findadapter
        }
        viewmodel.res.observe(viewLifecycleOwner, Observer {
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
                        findadapter.setRequests(list)
                    }
                }
            }
        })
//        viewmodel.find(lat,long)
        findadapter.itemClicked(object :FindFamilyAdapter.OnItemsClicked{
            override fun onClicked(position: Int) {
                FragmentTransactions.replaceFragment(FamilyDetails(),requireActivity(),FamilyDetails().tag)
            }

        })
    }

}