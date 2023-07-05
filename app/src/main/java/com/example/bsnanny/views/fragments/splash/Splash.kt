package com.example.bsnanny.views.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentSplashBinding
import com.example.bsnanny.utils.sharedPreferences.SharedPreferences
import com.example.bsnanny.utils.sharedPreferences.SharedPreferences.SAVE_JWT_USER_KEY
import com.example.bsnanny.views.fragments.onboarding.OnboardingFragment.Companion.ONBOARDING_SAVE_KEY


class Splash : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SharedPreferences.initSharedPref(requireContext())
        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedPreferences.getOnBoardingStatus(ONBOARDING_SAVE_KEY)) {
                if (SharedPreferences.getUser(SAVE_JWT_USER_KEY)?.token == null){
                    findNavController().navigate(R.id.action_splash_to_signIn)
                }else{
                    findNavController().navigate(R.id.action_splash_to_dashboard)
                }
            } else {
                findNavController().navigate(R.id.action_splash_to_onboardingFragment)
            }
        }, 3000)
    }
}