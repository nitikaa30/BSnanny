package com.example.bsnanny.fragments.chooseProfile

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentChooseProfileBinding
import com.example.bsnanny.sharedPreferences.SavePrefs

class ChooseProfile : Fragment() {
    private lateinit var binding : FragmentChooseProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseProfileBinding.inflate(inflater, container, false)
       // return inflater.inflate(R.layout.fragment_choose_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.nannyBtn.setOnClickListener {
            binding.babysitBtn.isEnabled = false
            findNavController().navigate(R.id.action_chooseProfile_to_dashboard)
            SavePrefs.saveChooseProfileStatus(requireContext(), CHOOSE_PROFILE_STATUS_NAME, CHOOSE_PROFILE_STATUS_KEY, "Nanny")
        }
        binding.babysitBtn.setOnClickListener {
            binding.nannyBtn.isEnabled = false
            findNavController().navigate(R.id.action_chooseProfile_to_dashboard)
            SavePrefs.saveChooseProfileStatus(requireContext(), CHOOSE_PROFILE_STATUS_NAME, CHOOSE_PROFILE_STATUS_KEY, "Parent")
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100
            )
            return
        }
    }
    companion object{
        const val CHOOSE_PROFILE_STATUS_NAME = "ChooseProfileStatusName"
        const val CHOOSE_PROFILE_STATUS_KEY = "ChooseProfileStatusKey"
    }

}