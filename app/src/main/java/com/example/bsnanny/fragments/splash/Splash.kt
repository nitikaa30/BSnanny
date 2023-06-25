package com.example.bsnanny.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView.FindListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentSplashBinding
import com.example.bsnanny.fragments.onboarding.OnboardingFragment.Companion.ONBOARDING_SAVE_KEY
import com.example.bsnanny.fragments.onboarding.OnboardingFragment.Companion.ONBOARDING_SAVE_NAME
import com.example.bsnanny.sharedPreferences.GetPrefs


class Splash : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Looper.myLooper()?.let {
//            Handler(it).postDelayed(Runnable{
//              //  findNavController().navigate(R.id.action_splash_to_intro)
//            },3000)
//        }

        Handler(Looper.getMainLooper()).postDelayed({
            if(GetPrefs.getOnBoardingStatus(requireContext(), ONBOARDING_SAVE_NAME, ONBOARDING_SAVE_KEY)){
                findNavController().navigate(R.id.action_splash_to_signIn)
            }else{
                findNavController().navigate(R.id.action_splash_to_onboardingFragment)
            }

        }, 3000)
    }
}