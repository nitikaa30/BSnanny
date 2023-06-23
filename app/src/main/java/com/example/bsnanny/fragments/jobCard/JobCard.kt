package com.example.bsnanny.fragments.jobCard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentJobCardBinding

class JobCard : Fragment() {
    private lateinit var binding : FragmentJobCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJobCardBinding.inflate(inflater, container, false)
       // return inflater.inflate(R.layout.fragment_job_card, container, false)
        return binding.root
    }

}