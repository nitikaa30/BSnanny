package com.example.bsnanny.views.fragments.maps

import android.Manifest
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bsnanny.R
import com.example.bsnanny.databinding.PlacesAutoCompleteBinding
import com.example.bsnanny.utils.MapMarkersManager
import com.example.bsnanny.utils.dpToPx
import com.example.bsnanny.utils.fragmentTransactions.FragmentTransactions
import com.example.bsnanny.utils.geoIntent
import com.example.bsnanny.utils.hideKeyboard
import com.example.bsnanny.utils.isPermissionGranted
import com.example.bsnanny.utils.lastKnownLocation
import com.example.bsnanny.utils.placesAutoCompleteTag
import com.example.bsnanny.utils.shareIntent
import com.example.bsnanny.utils.showToast
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.search.autocomplete.PlaceAutocomplete
import com.mapbox.search.autocomplete.PlaceAutocompleteOptions
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion
import com.mapbox.search.autocomplete.PlaceAutocompleteType
import com.mapbox.search.common.IsoCountryCode
import com.mapbox.search.ui.adapter.autocomplete.PlaceAutocompleteUiAdapter
import com.mapbox.search.ui.view.CommonSearchViewConfiguration
import com.mapbox.search.ui.view.SearchResultsView
import com.mapbox.search.ui.view.place.SearchPlace
import com.mapbox.search.ui.view.place.SearchPlaceBottomSheetView
import kotlinx.coroutines.launch


class PlacesAutoComplete : Fragment() {

    private lateinit var binding : PlacesAutoCompleteBinding
    private lateinit var placeAutocomplete: PlaceAutocomplete
    private lateinit var searchPlaceView: SearchPlaceBottomSheetView
    private lateinit var mapMarkersManager: MapMarkersManager
    private var ignoreNextQueryUpdate = false
    private lateinit var queryEditText: EditText
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var searchResultsView: SearchResultsView
    private lateinit var placeAutocompleteUiAdapter: PlaceAutocompleteUiAdapter
    private lateinit var confirmButton: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlacesAutoCompleteBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        confirmButton = binding.confirmButton
        placeAutocomplete = PlaceAutocomplete.create(getString(R.string.mapbox_access_token))
        queryEditText = binding.queryText
        mapView = binding.mapView
        mapView.getMapboxMap().also { mapboxMap ->
            this.mapboxMap = mapboxMap

            mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
                mapView.location.updateSettings {
                    enabled = true
                }

                mapView.location.addOnIndicatorPositionChangedListener(object :
                    OnIndicatorPositionChangedListener {
                    override fun onIndicatorPositionChanged(point: Point) {
                        mapView.getMapboxMap().setCamera(
                            CameraOptions.Builder()
                                .center(point)
                                .zoom(9.0)
                                .build()
                        )

                        mapView.location.removeOnIndicatorPositionChangedListener(this)
                    }
                })
            }
        }
        mapMarkersManager = MapMarkersManager(mapView)

        mapboxMap.addOnMapLongClickListener {
            reverseGeocoding(it)
            return@addOnMapLongClickListener true
        }
        searchResultsView = binding.searchResultsView

        searchResultsView.initialize(
            SearchResultsView.Configuration(
                commonConfiguration = CommonSearchViewConfiguration()
            )
        )
        placeAutocompleteUiAdapter = PlaceAutocompleteUiAdapter(
            view = searchResultsView,
            placeAutocomplete = placeAutocomplete
        )
        searchPlaceView = binding.searchPlaceView.apply {
            initialize(CommonSearchViewConfiguration())

            isFavoriteButtonVisible = false

            addOnCloseClickListener {
                hide()
                closePlaceCard()
            }


            confirmButton.setOnClickListener {
                FragmentTransactions.popFragmentFromBackStack(placesAutoCompleteTag, requireActivity())
            }


            addOnShareClickListener { searchPlace ->
                startActivity(shareIntent(searchPlace))
            }
        }
        LocationEngineProvider.getBestLocationEngine(requireContext()).lastKnownLocation(requireContext()) { point ->
            point?.let {
                mapView.getMapboxMap().setCamera(
                    CameraOptions.Builder()
                        .center(point)
                        .zoom(9.0)
                        .build()
                )
            }
        }

        placeAutocompleteUiAdapter.addSearchListener(object : PlaceAutocompleteUiAdapter.SearchListener {

            override fun onSuggestionsShown(suggestions: List<PlaceAutocompleteSuggestion>) {
                // Nothing to do
                Log.d("SUGGESTIONS", "onSuggestionsShown: $suggestions ")
            }

            override fun onSuggestionSelected(suggestion: PlaceAutocompleteSuggestion) {
                openPlaceCard(suggestion)
                Log.d("SUGGESTIONS", "onSuggestionCLicked: ${suggestion} ")
            }

            override fun onPopulateQueryClick(suggestion: PlaceAutocompleteSuggestion) {
                queryEditText.setText(suggestion.name)
            }

            override fun onError(e: Exception) {
                // Nothing to do
            }
        })

        queryEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (ignoreNextQueryUpdate) {
                    ignoreNextQueryUpdate = false
                } else {
                    closePlaceCard()
                }

                lifecycleScope.launch {
                    placeAutocompleteUiAdapter.search(text.toString(),
                        options = PlaceAutocompleteOptions(limit = 30,
                            countries = listOf(IsoCountryCode.INDIA))
                    )
                    searchResultsView.isVisible = text.isNotEmpty()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Nothing to do
            }

            override fun afterTextChanged(s: Editable) {
                // Nothing to do
            }
        })

        if (!isPermissionGranted(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    private fun reverseGeocoding(point: Point) {
        val types: List<PlaceAutocompleteType> = when (mapboxMap.cameraState.zoom) {
            in 0.0..4.0 -> REGION_LEVEL_TYPES
            in 4.0..6.0 -> DISTRICT_LEVEL_TYPES
            in 6.0..12.0 -> LOCALITY_LEVEL_TYPES
            else -> ALL_TYPES
        }

        lifecycleScope.launchWhenStarted {
            val response = placeAutocomplete.suggestions(point, PlaceAutocompleteOptions(
                types= types))
            response.onValue { suggestions ->
                if (suggestions.isEmpty()) {
                    this@PlacesAutoComplete.context?.showToast(R.string.place_autocomplete_reverse_geocoding_error_message)
                } else {
                    openPlaceCard(suggestions.first())
                }
            }.onError { error ->
                Log.d(LOG_TAG, "Reverse geocoding error", error)
                this@PlacesAutoComplete.context?.showToast(R.string.place_autocomplete_reverse_geocoding_error_message)
            }
        }
    }
    private fun closePlaceCard() {
        searchPlaceView.hide()
        mapMarkersManager.clearMarkers()
    }
    private fun openPlaceCard(suggestion: PlaceAutocompleteSuggestion) {
        confirmButton.visibility = View.VISIBLE
        ignoreNextQueryUpdate = true
        queryEditText.setText("")

        lifecycleScope.launchWhenStarted {
            placeAutocomplete.select(suggestion).onValue { result ->
                mapMarkersManager.showMarker(suggestion.coordinate)
                searchPlaceView.open(SearchPlace.createFromPlaceAutocompleteResult(result))
                queryEditText.hideKeyboard()
                searchResultsView.isVisible = false
            }.onError { error ->
                Log.d(LOG_TAG, "Suggestion selection error", error)

            }
        }
    }
    companion object {

        const val PERMISSIONS_REQUEST_LOCATION = 0

        const val LOG_TAG = "AutocompleteUiActivity"

        val MARKERS_EDGE_OFFSET = dpToPx(64).toDouble()
        val PLACE_CARD_HEIGHT = dpToPx(300).toDouble()
        val MARKERS_TOP_OFFSET = dpToPx(88).toDouble()

        val MARKERS_INSETS_OPEN_CARD = EdgeInsets(
            MARKERS_TOP_OFFSET, MARKERS_EDGE_OFFSET, PLACE_CARD_HEIGHT, MARKERS_EDGE_OFFSET
        )

        val REGION_LEVEL_TYPES = listOf(
            PlaceAutocompleteType.AdministrativeUnit.Country,
            PlaceAutocompleteType.AdministrativeUnit.Region
        )

        val DISTRICT_LEVEL_TYPES = REGION_LEVEL_TYPES + listOf(
            PlaceAutocompleteType.AdministrativeUnit.Postcode,
            PlaceAutocompleteType.AdministrativeUnit.District
        )

        val LOCALITY_LEVEL_TYPES = DISTRICT_LEVEL_TYPES + listOf(
            PlaceAutocompleteType.AdministrativeUnit.Place,
            PlaceAutocompleteType.AdministrativeUnit.Locality
        )

         val ALL_TYPES = listOf(
            PlaceAutocompleteType.Poi,
            PlaceAutocompleteType.AdministrativeUnit.Country,
            PlaceAutocompleteType.AdministrativeUnit.Region,
            PlaceAutocompleteType.AdministrativeUnit.Postcode,
            PlaceAutocompleteType.AdministrativeUnit.District,
            PlaceAutocompleteType.AdministrativeUnit.Place,
            PlaceAutocompleteType.AdministrativeUnit.Locality,
            PlaceAutocompleteType.AdministrativeUnit.Neighborhood,
            PlaceAutocompleteType.AdministrativeUnit.Street,
            PlaceAutocompleteType.AdministrativeUnit.Address,
        )
    }
}