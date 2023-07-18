package com.example.bsnanny.views.fragments.jobCard

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentJobCardBinding
import com.example.bsnanny.utils.fragmentTransactions.FragmentTransactions
import com.example.bsnanny.utils.placesAutoCompleteTag
import com.example.bsnanny.views.fragments.maps.PlacesAutoComplete
import com.mapbox.search.ApiType
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchEngine
import com.mapbox.search.SearchEngineSettings
import com.mapbox.search.offline.OfflineResponseInfo
import com.mapbox.search.offline.OfflineSearchEngine
import com.mapbox.search.offline.OfflineSearchEngineSettings
import com.mapbox.search.offline.OfflineSearchResult
import com.mapbox.search.record.HistoryRecord
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import com.mapbox.search.ui.adapter.engines.SearchEngineUiAdapter
import com.mapbox.search.ui.view.CommonSearchViewConfiguration
import com.mapbox.search.ui.view.DistanceUnitType
import com.mapbox.search.ui.view.SearchResultsView
import java.util.Calendar

class JobCard : Fragment() {
    private lateinit var mapboxDialog: Dialog
    private lateinit var binding: FragmentJobCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJobCardBinding.inflate(inflater, container, false)
        // return inflater.inflate(R.layout.fragment_job_card, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(binding.jobCardToolbar)
        if (activity?.supportActionBar != null) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        }
        binding.jobCardToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.startDateTILJob.setEndIconOnClickListener {
            datePicker(binding.startDateEDJob)
        }
        binding.EndDateTILJob.setEndIconOnClickListener {
            datePicker(binding.EndDateEDJob)
        }
        binding.FromEDJob.setOnClickListener {
            pickTime(binding.FromEDJob)
        }
        binding.TOED.setOnClickListener {
            pickTime(binding.TOED)
        }

        binding.AddressED.setOnClickListener {
           showMapDialog(it)
        }




        binding.SearchNannyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_jobCard_to_nav_graph_pro)
        }


        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressTextView.text = "₹$progress"

                val seekBarWidth = seekBar?.width ?: 1
                val maxProgress = seekBar?.max ?: 3
                val margin: Int
                try {
                    margin = (seekBarWidth * progress) / seekBarWidth
                    val layoutParams =
                        binding.progressTextView.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.marginStart = margin
                    binding.progressTextView.layoutParams = layoutParams
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d("seekbar", "onStartTrackingTouch: ${seekBar?.progress} ")
            }

        })
    }

    private fun datePicker(v: View) {
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selYear, selMonth, dayOfMonth ->
                val selDate = "$dayOfMonth/${selMonth + 1}/$selYear"

                when (v) {

                    binding.startDateEDJob -> {
                        binding.startDateEDJob.setText(selDate)

                    }

                    binding.EndDateEDJob -> {
                        binding.EndDateEDJob.setText(selDate)
                    }

                }


            }, year, month, day)
        datePickerDialog.show()
        datePickerDialog.datePicker.background =
            AppCompatResources.getDrawable(requireContext(), R.color.white)
        datePickerDialog.datePicker.setBackgroundColor(Color.TRANSPARENT)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 864000
    }

    @SuppressLint("ResourceAsColor")
    private fun pickTime(text: EditText) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { view, hourOfDay, minute ->
                val amPm = if (hourOfDay < 12) "AM" else "PM"
                val hourFormatted = if (hourOfDay > 12) hourOfDay - 12 else hourOfDay
                text.setText("$hourOfDay:$minute $amPm")

                if (text.text.toString().isEmpty()) {
                    text.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.clock_white,
                        0
                    )
                } else {
                    text.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.enabled_clock,
                        0
                    )
                    binding.FromTILJob.boxStrokeColor = R.color.purpleU1
                }

            },
            hour,
            minute,
            false
        )
        timePickerDialog.setCanceledOnTouchOutside(false)
        timePickerDialog.show()
    }
    private fun cancelDialog() {
        mapboxDialog.dismiss()
    }


    private fun showMapDialog(v: View) {
        mapboxDialog = Dialog(requireContext())
        mapboxDialog.setContentView(R.layout.search_engine)
        val accessToken =
            getString(R.string.mapbox_access_token)
        val queryEditText = mapboxDialog.findViewById<EditText>(R.id.query_edit_text)
        val searchResultsView1 =
            mapboxDialog.findViewById<SearchResultsView>(R.id.search_results_view_engine)
        searchResultsView1.initialize(
            SearchResultsView.Configuration(
                commonConfiguration = CommonSearchViewConfiguration(DistanceUnitType.IMPERIAL)
            )
        )
        val searchEngine = SearchEngine.createSearchEngineWithBuiltInDataProviders(
            apiType = ApiType.GEOCODING,
            settings = SearchEngineSettings(accessToken)
        )
        val offlineSearchEngine = OfflineSearchEngine.create(
            OfflineSearchEngineSettings(accessToken)
        )
        val searchEngineUiAdapter = SearchEngineUiAdapter(
            view = searchResultsView1,
            searchEngine = searchEngine,
            offlineSearchEngine = offlineSearchEngine,
        )
        searchEngineUiAdapter.addSearchListener(object : SearchEngineUiAdapter.SearchListener {

            private fun showToast(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

            override fun onSuggestionsShown(
                suggestions: List<SearchSuggestion>,
                responseInfo: ResponseInfo
            ) {
            }

            override fun onCategoryResultsShown(
                suggestion: SearchSuggestion,
                results: List<SearchResult>,
                responseInfo: ResponseInfo
            ) {

            }

            override fun onOfflineSearchResultsShown(
                results: List<OfflineSearchResult>,
                responseInfo: OfflineResponseInfo
            ) {

            }

            override fun onSuggestionSelected(searchSuggestion: SearchSuggestion): Boolean {
                return false
            }

            override fun onSearchResultSelected(
                searchResult: SearchResult,
                responseInfo: ResponseInfo
            ) {
                showToast("SearchResult clicked: ${searchResult.coordinate.latitude()}")
                binding.AddressED.setText(searchResult.fullAddress)
                binding.City.setText(searchResult.address?.place)
                binding.Pin.setText(searchResult.address?.postcode)
                binding.CountryED.setText(searchResult.address?.country)
                mapboxDialog.dismiss()





            }

            override fun onOfflineSearchResultSelected(
                searchResult: OfflineSearchResult,
                responseInfo: OfflineResponseInfo
            ) {
                showToast("OfflineSearchResult clicked: ${searchResult.name}")
            }

            override fun onError(e: Exception) {
                showToast("Error happened: $e")
            }

            override fun onHistoryItemClick(historyRecord: HistoryRecord) {
                showToast("HistoryRecord clicked: ${historyRecord.name}")
                Log.d("history", "onHistoryItemClick: ${historyRecord.coordinate.latitude()}")
                mapboxDialog.dismiss()
//                when (v) {
//                    binding.pickUpLoc -> {
//                        binding.pickUpLoc.setText(pickLoc)
//                        cancelDialog()
//                    }
//
//                    binding.deliverLoc -> {
//                        binding.deliverLoc.setText(pickLoc)
//                    }
//                }

            }

            override fun onPopulateQueryClick(
                suggestion: SearchSuggestion,
                responseInfo: ResponseInfo
            ) {
                queryEditText.setText(suggestion.name)
            }

            override fun onFeedbackItemClick(responseInfo: ResponseInfo) {
// not implemented
            }
        })
        queryEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, after: Int) {
                searchEngineUiAdapter.search(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
// not implemented
            }

            override fun afterTextChanged(e: Editable) { /* not implemented */
            }
        })
        if (requireContext().isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_LOCATION
            )
        }
        mapboxDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mapboxDialog.show()
    }
    private companion object {

        private const val PERMISSIONS_REQUEST_LOCATION = 0

        fun Context.isPermissionGranted(permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}