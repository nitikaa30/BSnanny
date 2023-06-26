package com.example.bsnanny.fragments.otp

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentOtpBinding


class Otp : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        //  return inflater.inflate(R.layout.fragment_otp, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pinView.setAnimationEnable(true)
        // Get instance of Vibrator from current Context
        val v = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

        binding.pinView.doOnTextChanged { text, start, before, count ->
            v!!.vibrate(35)
        }
        binding.verificationBtn.setOnClickListener {


            if (binding.pinView.text.toString() != "123456") {
                binding.pinView.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                binding.pinView.setText("")
                v!!.vibrate(100)
            }else{
                findNavController().navigate(R.id.action_otp_to_chooseProfile)
            }
        }


    }


}