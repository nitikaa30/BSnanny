package com.example.bsnanny.fragments.invoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentCreateInvoiceBinding

class CreateInvoice : Fragment() {

    private lateinit var binding: FragmentCreateInvoiceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCreateInvoiceBinding.inflate(layoutInflater)
        return binding.root
    }

}