package com.example.bsnanny.views.fragments.congratulations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentCongratulationsBinding
import com.example.bsnanny.databinding.FragmentPricingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Congratulations : Fragment() {

    private lateinit var binding: FragmentCongratulationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     binding= FragmentCongratulationsBinding.inflate(layoutInflater)
     return binding.root
    }

}