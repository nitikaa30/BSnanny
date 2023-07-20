package com.example.bsnanny.views.fragments.jobCard

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.authUser.AuthUser
import com.example.bsnanny.databinding.FragmentJobCardBinding
import com.example.bsnanny.models.findNanny.FindNannyApiItems
import com.example.bsnanny.utils.CommonTextWatcher
import com.example.bsnanny.utils.JobCardFields
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.countDigits
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.utils.validateJobCardFields
import com.example.bsnanny.viewmodels.bookingStatus.BookingStatusViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
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
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class JobCard : Fragment() {
    private lateinit var mapboxDialog: Dialog
    private lateinit var binding: FragmentJobCardBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var price: String = ""
    private val bookingStatusViewModels: BookingStatusViewModels by viewModels()

    @Inject
    lateinit var authUser: AuthUser

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

        subscribeObservers()
        getBookingStatus()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
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

        binding.JobCardAddressTIL.setEndIconOnClickListener {
            getLocation()
        }
        Log.d("token", "onViewCreated: ${authUser.getUser()?.token}")

        val addressTextWatcher = CommonTextWatcher(

            onTextChanged = { _, _, _, _ ->
                binding.JobCardAddressTIL.error = null
                binding.JobCardAddressTIL.isErrorEnabled = false
            },
            afterTextChanged = {
                if (it.toString().isEmpty()) {
                    binding.JobCardAddressTIL.error = "Enter the location or Detect Location"
                } else {
                    binding.JobCardAddressTIL.error = null
                    binding.JobCardAddressTIL.isErrorEnabled = false
                }
            }
        )
        binding.AddressED.addTextChangedListener(addressTextWatcher)

        val cityTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the City",
                    binding.CityTIL,
                    JobCardFields.CITY
                )
            },
            onTextChanged = { _, _, _, _ ->
                binding.CityTIL.error = null
                binding.CityTIL.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the City",
                    binding.CityTIL,
                    JobCardFields.CITY
                )
            }
        )
        binding.City.addTextChangedListener(cityTextWatcher)

        val pinTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the PIN",
                    binding.PINTIL,
                    JobCardFields.PIN
                )
            },
            onTextChanged = { s: CharSequence?, _, _, _ ->
                binding.PINTIL.error = null
                binding.PINTIL.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the PIN",
                    binding.PINTIL,
                    JobCardFields.PIN
                )
            }
        )
        binding.Pin.addTextChangedListener(pinTextWatcher)

        val countryTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the Country",
                    binding.CountryTIL,
                    JobCardFields.COUNTRY
                )
            },
            onTextChanged = { s: CharSequence?, _, _, _ ->
                binding.CountryTIL.error = null
                binding.CountryTIL.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the Country",
                    binding.CountryTIL,
                    JobCardFields.COUNTRY
                )
            }
        )
        binding.CountryED.addTextChangedListener(countryTextWatcher)

        val noOfChildrenTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the no. of children",
                    binding.NoChildTIL,
                    JobCardFields.CHILD_COUNT
                )
            },
            onTextChanged = { s: CharSequence?, _, _, _ ->
                binding.NoChildTIL.error = null
                binding.NoChildTIL.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the no. of children",
                    binding.NoChildTIL,
                    JobCardFields.CHILD_COUNT
                )
            }
        )
        binding.NoChildED.addTextChangedListener(noOfChildrenTextWatcher)

        val fromTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the Time",
                    binding.FromTILJob,
                    JobCardFields.START_DATE
                )
            },
            onTextChanged = { s: CharSequence?, _, _, _ ->
                binding.FromTILJob.error = null
                binding.FromTILJob.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the Time",
                    binding.FromTILJob,
                    JobCardFields.START_DATE
                )
            }
        )
        binding.FromEDJob.addTextChangedListener(fromTextWatcher)

        val toTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the Time",
                    binding.TOTIL,
                    JobCardFields.END_DATE
                )
            },
            onTextChanged = { s: CharSequence?, _, _, _ ->
                binding.TOTIL.error = null
                binding.TOTIL.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the Time",
                    binding.TOTIL,
                    JobCardFields.END_DATE
                )

            }
        )
        binding.TOED.addTextChangedListener(toTextWatcher)

        val startDateTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the Start Date",
                    binding.startDateTILJob,
                    JobCardFields.START_DATE
                )
            },
            onTextChanged = { s: CharSequence?, _, _, _ ->
                binding.startDateTILJob.error = null
                binding.startDateTILJob.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the Start Date",
                    binding.startDateTILJob,
                    JobCardFields.START_DATE
                )

            }
        )
        binding.startDateEDJob.addTextChangedListener(startDateTextWatcher)

        val endDateTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s: CharSequence?, _, _, _ ->
                validateJobCardFields(
                    s.toString(),
                    "Enter the End Date",
                    binding.EndDateTILJob,
                    JobCardFields.END_DATE
                )
            },
            onTextChanged = { _, _, _, _ ->
                binding.EndDateTILJob.error = null
                binding.EndDateTILJob.isErrorEnabled = false
            },
            afterTextChanged = {
                validateJobCardFields(
                    it.toString(),
                    "Enter the End Date",
                    binding.EndDateTILJob,
                    JobCardFields.END_DATE
                )

            }
        )

        binding.EndDateEDJob.addTextChangedListener(endDateTextWatcher)




        binding.SearchNannyBtn.setOnClickListener {
            when {
                binding.AddressED.text.toString().isEmpty() -> {
                    binding.JobCardAddressTIL.errorIconDrawable = null
                    binding.JobCardAddressTIL.error = "Enter the location or Detect Location"
                }

                binding.City.text.toString().isEmpty() -> {
                    binding.CityTIL.error = "Enter the City"
                }

                binding.Pin.text.toString().isEmpty() -> {
                    binding.PINTIL.error = "Enter the PIN "
                }

                binding.CountryED.text.toString().isEmpty() -> {
                    binding.CountryTIL.error = "Enter the Country"
                }

                binding.NoChildED.text.toString().isEmpty() -> {
                    binding.NoChildTIL.error = "Enter the No. of  Children"
                }

                binding.seekbar.progress <= 0 -> {
                    Toasty.error(requireContext(), "Set the Price", Toast.LENGTH_SHORT, true).show()
                }

                binding.FromEDJob.text.toString().isEmpty() -> {
                    binding.FromTILJob.error = "Choose the Time"
                }

                binding.TOED.text.toString().isEmpty() -> {
                    binding.TOTIL.error = "choose the Time"
                }

                binding.startDateEDJob.text.toString().isEmpty() -> {
                    binding.startDateTILJob.errorIconDrawable = null
                    binding.startDateTILJob.error = "Choose the start Date"
                }

                binding.EndDateEDJob.text.toString().isEmpty() -> {
                    binding.EndDateTILJob.errorIconDrawable = null
                    binding.EndDateTILJob.error = "Choose the end Date"
                }

                !checkDates() -> {
                    Toasty.error(
                        requireContext(),
                        "Choose end Date Correctly",
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }

                else -> {
                    binding.JobCardAddressTIL.error = null
                    binding.JobCardAddressTIL.isErrorEnabled = false
                    val bundle = Bundle()
                    val findNannyApiItems = FindNannyApiItems(
                        latitude = latitude,
                        longitude = longitude,
                        price = price,
                        noOfChildren = binding.NoChildED.text.toString(),
                        from = binding.FromEDJob.text.toString(),
                        to = binding.TOED.text.toString(),
                        startDate = binding.startDateEDJob.text.toString(),
                        endDate = binding.EndDateEDJob.text.toString(),
                        address = binding.AddressED.text.toString(),
                        city = binding.City.text.toString(),
                        pin = binding.Pin.text.toString(),
                        country = binding.CountryED.text.toString()
                    )

                    bundle.putParcelable("findNannyItems", findNannyApiItems)



                    findNavController().navigate(R.id.nav_graph_pro, bundle)


                }
            }

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

                price = seekBar?.progress.toString()
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

                val currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                //  val selDate = "$dayOfMonth/${selMonth + 1}/$selYear"
                var selDate = ""
                selDate = if (countDigits(selMonth) <= 1)
                    "$selYear-0${selMonth + 1}-$dayOfMonth"
                else
                    "$selYear-${selMonth + 1}-$dayOfMonth"


                when (v) {

                    binding.startDateEDJob -> {
                        binding.startDateEDJob.setText(selDate)
                        if (!checkDates()) {
                            Toasty.error(
                                requireContext(),
                                "Choose end Date Correctly",
                                Toast.LENGTH_LONG,
                                true
                            ).show()
                        }

                    }

                    binding.EndDateEDJob -> {
                        binding.EndDateEDJob.setText(selDate)

                        if (!checkDates()) {
                            Toasty.error(
                                requireContext(),
                                "Choose end Date Correctly",
                                Toast.LENGTH_LONG,
                                true
                            ).show()
                        }

                    }

                }


            }, year, month, day)
        datePickerDialog.show()
        datePickerDialog.datePicker.background =
            AppCompatResources.getDrawable(requireContext(), R.color.white)
        datePickerDialog.datePicker.setBackgroundColor(Color.TRANSPARENT)
        // datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 864000
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    }

    @SuppressLint("ResourceAsColor")
    private fun pickTime(text: EditText) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)


        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->

                val hourFormatted = if (hourOfDay > 12) hourOfDay - 12 else hourOfDay
                if (minute <= 10) {
                    text.setText("$hourOfDay:0$minute:00")
                } else {
                    text.setText("$hourOfDay:$minute:00")
                }


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
            true
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
                latitude = searchResult.coordinate.latitude()
                longitude = searchResult.coordinate.longitude()
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
                Log.d("history", "onHistoryItemClick: ${historyRecord.coordinate.latitude()}")
                if (historyRecord.address?.postcode == null) {
                    binding.AddressED.setText(historyRecord.name)
                } else {
                    binding.AddressED.setText(historyRecord.name + ", " + historyRecord.address?.postcode)
                }

                latitude = historyRecord.coordinate.latitude()
                longitude = historyRecord.coordinate.longitude()
                binding.City.setText(historyRecord.address?.place)
                binding.Pin.setText(historyRecord.address?.postcode)
                binding.CountryED.setText(historyRecord.address?.country)
                mapboxDialog.dismiss()

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


    private fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

        val address = addresses?.get(0)?.getAddressLine(0)
        val city = addresses?.get(0)?.locality
        val state = addresses?.get(0)?.adminArea
        val country = addresses?.get(0)?.countryName
        val postalCode = addresses?.get(0)?.postalCode
        val knownName = addresses?.get(0)?.featureName

        binding.AddressED.setText(address.toString())
        binding.City.setText(city)
        binding.Pin.setText(postalCode)
        binding.CountryED.setText(country)

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


            return
        }

        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {

                latitude = it.latitude
                longitude = it.longitude

                // Log.d("Lat Lng", "$textLatitude, $textLongitude")
                getAddressFromLocation(requireContext(), it.latitude, it.longitude)
            }
        }
    }

    private fun checkDates(): Boolean {
        val startDate = binding.startDateEDJob.text.toString()
        val endDate = binding.EndDateEDJob.text.toString()

        if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDateObj = sdf.parse(startDate)
            val endDateObj = sdf.parse(endDate)

            if (startDateObj != null && endDateObj != null) {
                return !endDateObj.before(startDateObj)
            }
        }

        return true
    }

    private fun subscribeObservers() {
        bookingStatusViewModels.res.observe(viewLifecycleOwner){
            when(it){
                is NetworkResults.Error -> {

                }
                is NetworkResults.Loading -> {

                }
                is NetworkResults.Success -> {

                    if (it.data?.msg != null){
                        Snackbar.make(binding.root, "You already have a pending booking", Snackbar.LENGTH_LONG).show()
                    }

                    if (it.data?.success == true){
                        binding.SearchNannyBtn.visibility = View.INVISIBLE
                    }else{
                        binding.SearchNannyBtn.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
    private fun getBookingStatus(){
        bookingStatusViewModels.getBookingStatus()
    }
}
