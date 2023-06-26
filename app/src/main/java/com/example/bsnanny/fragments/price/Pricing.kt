package com.example.bsnanny.fragments.price

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentPricingBinding

class Pricing : Fragment() {
    private lateinit var binding: FragmentPricingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentPricingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weekly=resources.getStringArray(R.array.weekly)
        val weekArrayAdapter= context?.let { ArrayAdapter(it,R.layout.dropdown_item_pricing,weekly) }
        binding.autoCompleteTextViewWeekly.setAdapter(weekArrayAdapter)

        val byweekly=resources.getStringArray(R.array.by_weekly)
        val byweekArrayAdapter= context?.let { ArrayAdapter(it,R.layout.dropdown_item_pricing,byweekly) }
        binding.autoCompleteByWeekly.setAdapter(byweekArrayAdapter)

        val bymonthly=resources.getStringArray(R.array.by_monthly)
        val bymonthlyArrayAdapter= context?.let { ArrayAdapter(it,R.layout.dropdown_item_pricing,bymonthly) }
        binding.autoCompleteByMonthly.setAdapter(bymonthlyArrayAdapter)
    }


}