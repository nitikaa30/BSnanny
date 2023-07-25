package com.example.bsnanny.views.fragments.congratulations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentCongratulationsBinding
import com.example.bsnanny.databinding.FragmentPricingBinding
import com.example.bsnanny.utils.DASHBOARD_FRAGMENT_TAG
import com.example.bsnanny.utils.fragmentTransactions.FragmentTransactions
import com.example.bsnanny.views.fragments.dashboard.Dashboard
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btn.setOnClickListener {
            FragmentTransactions.replaceFragment(Dashboard(),requireActivity() ,DASHBOARD_FRAGMENT_TAG)

        }
    }

}