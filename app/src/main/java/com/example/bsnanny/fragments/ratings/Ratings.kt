package com.example.bsnanny.fragments.ratings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentRatingsBinding


class Ratings : Fragment() {

    private lateinit var binding:FragmentRatingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRatingsBinding.inflate(layoutInflater)
        return binding.root
    }

}