package com.example.bsnanny.views.fragments.nanny

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.HireNannyAdapter
import com.example.bsnanny.databinding.FragmentHireNannyBinding
import com.example.bsnanny.models.HireNannyModel
import java.util.ArrayList
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
        binding.nannyCommentsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        val mList = ArrayList<HireNannyModel>()
        mList.add(HireNannyModel(R.drawable.image, "Alice", getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)))
        mList.add(HireNannyModel(R.drawable.image, "Bob", getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)))
        mList.add(HireNannyModel(R.drawable.image, "Edward", getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)))
        mList.add(HireNannyModel(R.drawable.image, "Rose", getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)))
        mList.add(HireNannyModel(R.drawable.image, "Ele", getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)))
        mList.add(HireNannyModel(R.drawable.image, "kate", getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)))
        mList.add(HireNannyModel(R.drawable.image, "Rick", getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur)))

        val adapter = HireNannyAdapter(mList)

        binding.shimmerLayout.startShimmer()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.INVISIBLE
            binding.nannyCommentsRecyclerView.visibility = View.VISIBLE
            binding.nannyCommentsRecyclerView.adapter = adapter
        }, 3000)
    binding.invite.setOnClickListener {
        findNavController().navigate(R.id.action_hireNannyFragment_to_paymentFragment)
    }
        val duration = resources.getStringArray(R.array.Duration)
        val durationArrayAdapter = context?.let {
            ArrayAdapter(it, R.layout.dropdown_item_pricing, duration)
        }
        binding.autoCompleteTextViewDuration.setAdapter(durationArrayAdapter)
        binding.startDateTIL.setEndIconOnClickListener {
            datePicker()
        }
        binding.FromED.setOnClickListener {
            pickTime(binding.FromED)
        }
        binding.TOED.setOnClickListener {
            pickTime(binding.TOED)
        }    }
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
    @SuppressLint("ResourceAsColor")
    private fun pickTime(text : EditText){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { view, hourOfDay, minute ->
                val amPm = if (hourOfDay < 12) "AM" else "PM"
                val hourFormatted = if (hourOfDay > 12) hourOfDay - 12 else hourOfDay
                text.setText("$hourOfDay:$minute $amPm")

                if (text.text.toString().isEmpty()){
                    text.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.clock_white,0)
                }else{
                    text.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.enabled_clock, 0)
                    binding.FromTIL.boxStrokeColor = R.color.purpleU1
                }

            },
            hour,
            minute,
            false
        )
        timePickerDialog.setCanceledOnTouchOutside(false)
        timePickerDialog.show()
    }
}