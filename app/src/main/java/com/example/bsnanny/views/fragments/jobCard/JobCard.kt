package com.example.bsnanny.views.fragments.jobCard

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
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentJobCardBinding
import java.util.Calendar

class JobCard :Fragment() {
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


        binding.SearchNannyBtn.setOnClickListener{
            findNavController().navigate(R.id.action_jobCard_to_nav_graph_pro)
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


}