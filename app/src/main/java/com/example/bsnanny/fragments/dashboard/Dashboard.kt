package com.example.bsnanny.fragments.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
        binding.manualAddressAdd.setOnClickListener{
            findNavController().navigate(R.id.action_dashboard_to_jobCard)
        }
        setUpDrawer()

        val menu: Menu = binding.navView.menu
        val notificationMenuItem: MenuItem = menu.findItem(R.id.menu_item3)
        val actionView: View = LayoutInflater.from(requireContext()).inflate(R.layout.custom_navigation_item, null)
        notificationMenuItem.actionView = actionView
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 3, 430, 0) // Set top and bottom margins as desired
        actionView.layoutParams = layoutParams
        val titleTextView: TextView = actionView.findViewById(R.id.title)
        titleTextView.text = "5"


        val jobRequestMenuItem : MenuItem = menu.findItem(R.id.jobRequests)
        val jobRequestMenuAction: View = LayoutInflater.from(requireContext()).inflate(R.layout.custom_navigation_item, null)
        jobRequestMenuItem.actionView = jobRequestMenuAction
         // Set top and bottom margins as desired

        val layoutParams1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams1.setMargins(0, 3, 405, 0)
        jobRequestMenuAction.layoutParams = layoutParams1
        val titleTextView1: TextView = jobRequestMenuAction.findViewById(R.id.title)
        titleTextView1.text = "10"

        val requestMenuItem : MenuItem = menu.findItem(R.id.menu_item4)
        val requestMenuAction: View = LayoutInflater.from(requireContext()).inflate(R.layout.custom_navigation_item, null)
        requestMenuItem.actionView = requestMenuAction
        // Set top and bottom margins as desired

        val layoutParams2 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams2.setMargins(0, 3, 495, 0)
        requestMenuAction.layoutParams = layoutParams2
        val titleTextView2: TextView = requestMenuAction.findViewById(R.id.title)
        titleTextView2.text = "20"


    }
    private fun setUpDrawer() {
        actionDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.mainDrawer,
            binding.dashboardToolbar,
            R.string.open,
            R.string.close
        )
        binding.mainDrawer.addDrawerListener(actionDrawerToggle)
        actionDrawerToggle.syncState()

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