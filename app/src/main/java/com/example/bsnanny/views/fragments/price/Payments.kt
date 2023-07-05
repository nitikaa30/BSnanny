package com.example.bsnanny.views.fragments.price

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentPaymentsBinding


class Payments : Fragment() {

    private lateinit var binding:FragmentPaymentsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPaymentsBinding.inflate(layoutInflater)
        return binding.root
    }

}