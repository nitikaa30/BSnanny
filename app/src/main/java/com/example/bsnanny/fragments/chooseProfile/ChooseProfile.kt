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
            findNavController().navigate(R.id.action_chooseProfile_to_dashboard)
        }
        binding.babysitBtn.setOnClickListener {
            findNavController().navigate(R.id.action_chooseProfile_to_dashboard)
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
//            binding.mapView.getMapboxMap().loadStyleUri(
//                Style.MAPBOX_STREETS
//            )
            return
        }
    }

}