package com.example.bsnanny.fragments.families

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentBookAppointmentBinding


class BookAppointment : Fragment() {

    private lateinit var binding: FragmentBookAppointmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBookAppointmentBinding.inflate(layoutInflater)
        return binding.root
    }

}