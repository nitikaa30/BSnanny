package com.example.bsnanny.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.ViewPagerAdapter
import com.example.bsnanny.databinding.FragmentOnboardingBinding
import com.example.bsnanny.sharedPreferences.SavePrefs


class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_onboarding, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = ViewPagerAdapter(requireContext())
        binding.viewPager.adapter = viewPagerAdapter
        binding.dotIndicator.attachTo(binding.viewPager)
        binding.nextContinueBtn.setOnClickListener {
            if (binding.viewPager.currentItem < 2) {
                binding.viewPager.currentItem = binding.viewPager.currentItem.plus(1)
            }
            else{
                findNavController().navigate(R.id.action_onboardingFragment_to_signIn)
                SavePrefs.saveOnBoardingStatus(requireContext(), ONBOARDING_SAVE_NAME, ONBOARDING_SAVE_KEY, true)
            }
        }
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //TODO("Not yet implemented")
            }

            override fun onPageSelected(position: Int) {
                if(position == 2) {
                    binding.nextContinueBtn.text = "Continue"
                }
                else {
                    binding.nextContinueBtn.text = "Next"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
              //  TODO("Not yet implemented")
            }

        })

    }
    companion object{
        const val ONBOARDING_SAVE_NAME = "OnBoardingSaveName"
        const val ONBOARDING_SAVE_KEY = "OnBoardingSaveKey"
    }

}