package com.example.bsnanny.views.fragments.families

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentFindFamiliesBinding


class FindFamilies : Fragment() {

    private lateinit var binding: FragmentFindFamiliesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFindFamiliesBinding.inflate(inflater, container, false)
        return binding.root
    }

}