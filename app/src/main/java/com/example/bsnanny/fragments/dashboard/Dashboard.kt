package com.example.bsnanny.fragments.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.view.marginTop
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentDashboardBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.badge.BadgeDrawable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.compass.compass

class Dashboard : Fragment() {
    private lateinit var binding : FragmentDashboardBinding
    private var textLatitude: Double = 0.0
    private var textLongitude: Double = 0.0
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_dashboard, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.manualAddressAdd.setOnClickListener{
            findNavController().navigate(R.id.action_dashboard_to_jobCard)
        }

        val menu: Menu = binding.navView.menu
        val menuItem: MenuItem = menu.findItem(R.id.menu_item3)
        val actionView: View = LayoutInflater.from(requireContext()).inflate(R.layout.custom_navigation_item, null)
        menuItem.actionView = actionView
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 10, 460, 0) // Set top and bottom margins as desired
        actionView.layoutParams = layoutParams
        val titleTextView: TextView = actionView.findViewById(R.id.title)
        titleTextView.text = "5"





    }
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100
            )

            binding.mapView.getMapboxMap().loadStyleUri(
                Style.MAPBOX_STREETS
            )

            return
        }

        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                textLatitude = it.latitude
                textLongitude = it.longitude
                Log.d("Lat Lng", "$textLatitude, $textLongitude")
                val initialCameraOptions = CameraOptions.Builder()
                    .center(Point.fromLngLat(it.longitude, it.latitude))
                    .pitch(30.0)
                    .zoom(9.0)
                    .bearing(-17.6)
                    .build()

                binding.mapView.getMapboxMap().setCamera(initialCameraOptions)
            }
        }
    }

}