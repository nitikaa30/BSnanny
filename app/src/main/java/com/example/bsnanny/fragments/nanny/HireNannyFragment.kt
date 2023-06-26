package com.example.bsnanny.fragments.nanny

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentHireNannyBinding
import java.util.Calendar

class HireNannyFragment : Fragment() {
    private lateinit var binding : FragmentHireNannyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHireNannyBinding.inflate(inflater, container, false)
       // return inflater.inflate(R.layout.fragment_hire_nanny, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val duration = resources.getStringArray(R.array.Duration)
        val durationArrayAdapter = context?.let {
            ArrayAdapter(it, R.layout.dropdown_item_pricing, duration)
        }
        binding.autoCompleteTextViewDuration.setAdapter(durationArrayAdapter)
        binding.startDateTIL.setEndIconOnClickListener {
            datePicker()
        }
    }
    private fun datePicker() {
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selYear, selMonth, dayOfMonth ->
            val selDate = "$dayOfMonth/${selMonth +1}/$selYear"
            binding.startDate.setText(selDate)

        },year,month,day)
        datePickerDialog.show()
        datePickerDialog.datePicker.background = AppCompatResources.getDrawable(requireContext(), R.color.white)
        datePickerDialog.datePicker.setBackgroundColor(Color.TRANSPARENT)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 864000
    }

}