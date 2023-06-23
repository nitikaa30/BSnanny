package com.example.bsnanny.fragments.dashboard

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentDashboardBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.compass.compass

class Dashboard : Fragment() {
    private lateinit var binding : FragmentDashboardBinding
    private var textLatitude: Double = 0.0
    private var textLongitude: Double = 0.0
    private lateinit var actionDrawerToggle: ActionBarDrawerToggle
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
        binding.manualAddressAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_jobCard)
        }
        setUpDrawer()

    }
    private fun getLocation() {
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
    private fun setUpDrawer() {
        actionDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.mainDrawer,
            binding.dashboardToolbar,
            R.string.open,
            R.string.close
        )

        val headerView = binding.navView
        val header = headerView.getHeaderView(0)
        //val title = header.findViewById<TextView>(R.id.name_drawer)
//        title.text = name
//        actionDrawerToggle.apply {
//            setHomeAsUpIndicator(R.drawable.menu)
//        }




        binding.mainDrawer.addDrawerListener(actionDrawerToggle)
        actionDrawerToggle.syncState()

      //  binding.navView.menu.findItem(R.id.menu_logout).setTitleColor(Color.parseColor("#FF9C1C"))

        binding.navView.setNavigationItemSelectedListener {
            it.isChecked
            try {
                when (it.itemId) {



                    else -> {
                        true
                    }
                }
            }catch (e : Exception){
                e.printStackTrace()
                true
            }


        }

    }

}