package com.example.bsnanny.fragments.jobCard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.core.view.marginLeft
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentJobCardBinding

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