package com.example.bsnanny.views.fragments.jobhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentJobHistoryBinding

class JobHistory : Fragment() {
    private lateinit var binding:FragmentJobHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentJobHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


}