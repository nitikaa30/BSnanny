package com.example.bsnanny.views.fragments.chooseProfile

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.R
import com.example.bsnanny.authUser.AuthUser
import com.example.bsnanny.databinding.FragmentChooseProfileBinding
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.sharedPreferences.SharedPreferences
import com.example.bsnanny.utils.sharedPreferences.SharedPreferences.SAVE_JWT_USER_KEY
import com.example.bsnanny.viewmodels.authentication.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChooseProfile : Fragment() {
    private lateinit var phoneNum: String
    private lateinit var countryCode: String
    private lateinit var binding: FragmentChooseProfileBinding
    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    @Inject lateinit var authUser: AuthUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChooseProfileBinding.inflate(inflater, container, false)
        // return inflater.inflate(R.layout.fragment_choose_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneNum = arguments.let { ChooseProfileArgs.fromBundle(it!!).phoneNumber }
        countryCode = arguments.let { ChooseProfileArgs.fromBundle(it!!).callingCode }

        binding.nannyBtn.setOnClickListener {
            binding.babysitBtn.isEnabled = false
           // findNavController().navigate(R.id.action_chooseProfile_to_dashboard)
            SharedPreferences.saveChooseProfileStatus(CHOOSE_PROFILE_STATUS_KEY, "Nanny")
            val phoneNumber = phoneNum
            val authenticationBody = AuthenticationBody(phoneNumber, 1)
            ProgressDialog.cancelProgressDialog()
            authenticate(authenticationBody)
            subscribeObserver()
        }
        binding.babysitBtn.setOnClickListener {
            binding.nannyBtn.isEnabled = false

            SharedPreferences.saveChooseProfileStatus(CHOOSE_PROFILE_STATUS_KEY, "Parent")
            val phoneNumber = phoneNum
            val authenticationBody = AuthenticationBody(phoneNumber, 2)
            authenticate(authenticationBody)
            ProgressDialog.cancelProgressDialog()
            subscribeObserver()
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

    companion object {
        const val CHOOSE_PROFILE_STATUS_KEY = "ChooseProfileStatusKey"
    }

    private fun subscribeObserver() {
        authenticationViewModel.res.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResults.Error -> {
                    ProgressDialog.cancelProgressDialog()
                }

                is NetworkResults.Loading -> {
                    ProgressDialog.showProgressDialog(requireContext())
                }

                is NetworkResults.Success -> {
                    authUser.saveToken(SAVE_JWT_USER_KEY, it.data?.authenticationData)
                    if (it.data?.authenticationData != null){
                        findNavController().navigate(R.id.action_chooseProfile_to_dashboard)
                    }




                    ProgressDialog.cancelProgressDialog()
                }
            }
        }
    }

    private fun authenticate(authenticationBody: AuthenticationBody) {
        authenticationViewModel.authenticate(authenticationBody)
    }


}