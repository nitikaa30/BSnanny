package com.example.bsnanny.fragments.invoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentInvoiceBinding


class Invoice : Fragment() {

    private lateinit var binding:FragmentInvoiceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentInvoiceBinding.inflate(layoutInflater)
        return binding.root
    }


}