package com.example.bsnanny.fragments.jobCard

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentJobCardBinding
import java.util.Calendar

class JobCard : Fragment() {
    private lateinit var binding : FragmentJobCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJobCardBinding.inflate(inflater, container, false)
       // return inflater.inflate(R.layout.fragment_job_card, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(binding.jobCardToolbar)
        if (activity?.supportActionBar != null) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        }
        binding.jobCardToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.FromED.setOnClickListener {
            pickTime(binding.FromED)
        }
        binding.TOED.setOnClickListener {
            pickTime(binding.TOED)
        }



        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressTextView.text = "₹$progress"

                val seekBarWidth = seekBar?.width ?: 0
                val maxProgress = seekBar?.max ?: 0
                val margin = (seekBarWidth * progress) / 563
                val layoutParams = binding.progressTextView.layoutParams as RelativeLayout.LayoutParams
                layoutParams.marginStart = margin
                binding.progressTextView.layoutParams = layoutParams

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d("seekbar", "onStartTrackingTouch: ${seekBar?.progress} ")
            }

        })
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