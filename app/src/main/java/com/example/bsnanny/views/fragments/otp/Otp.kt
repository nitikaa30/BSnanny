package com.example.bsnanny.views.fragments.otp

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.authToken.AuthUser
import com.example.bsnanny.databinding.FragmentOtpBinding
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.sharedPreferences.SharedPreferences.SAVE_JWT_USER_KEY
import com.example.bsnanny.viewmodels.authentication.AuthenticationViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class Otp : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var phoneNum: String
    private lateinit var countryCode: String
    private var verificationOTP: String = ""
    private lateinit var type: String
    private var v: Vibrator? = null
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    @Inject
    lateinit var authUser: AuthUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        //  return inflater.inflate(R.layout.fragment_otp, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.pinView.setAnimationEnable(true)
        phoneNum = arguments.let {
            OtpArgs.fromBundle(it!!).phoneNum
        }
        countryCode = arguments.let {
            OtpArgs.fromBundle(it!!).callingCode
        }
        type = arguments.let {
            OtpArgs.fromBundle(it!!).type
        }



        generateOTP()
        subscribeObserver()
        ProgressDialog.showProgressDialog(requireContext())

        v = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

        binding.pinView.doOnTextChanged { text, start, before, count ->
            v!!.vibrate(37)
        }
        binding.verificationBtn.setOnClickListener {
            if (binding.pinView.text.toString().isNotEmpty()) {
                otpVerify()
                ProgressDialog.showProgressDialog(requireContext())
            } else {

                binding.pinView.error = "Enter the OTP"
                binding.pinView.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
            }
        }


    }

    private fun generateOTP() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNum)
            .setTimeout(45L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("TAG", "onVerificationCompleted:$credential")

        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w("TAG", "onVerificationFailed", e)

            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    // Invalid request
                }

                is FirebaseTooManyRequestsException -> {
                    // The SMS quota for the project has been exceeded
                }

                is FirebaseAuthMissingActivityForRecaptchaException -> {
                    // reCAPTCHA verification attempted with null Activity
                }
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            Log.d("TAG", "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            //  storedVerificationId = verificationId
            // resendToken = token
            Log.d("verificationId", "onCodeSent: $verificationId ")
            ProgressDialog.cancelProgressDialog()
            verificationOTP = verificationId
        }
    }

    private fun otpVerify() {
        val otp = binding.pinView.text.toString()

        val credential = PhoneAuthProvider.getCredential(verificationOTP, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success")
                        val user = task.result?.user
                        val phoneNumber = "$phoneNum"
                        if (type == "Login") {
                            ProgressDialog.cancelProgressDialog()
                            val authenticationBody = AuthenticationBody(phoneNumber)
                            authenticate(authenticationBody)
                        } else {
                            ProgressDialog.cancelProgressDialog()
                            val action =
                                OtpDirections.actionOtpToChooseProfile(phoneNum, countryCode)
                            findNavController().navigate(action)
                        }


                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w("TAG1", "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            binding.pinView.startAnimation(
                                AnimationUtils.loadAnimation(
                                    requireContext(),
                                    R.anim.shake
                                )
                            )
                            binding.pinView.setText("")
                            v!!.vibrate(100)
                            ProgressDialog.cancelProgressDialog()
                        }

                    }
                }.addOnFailureListener { e ->
                    Toasty.error(
                        requireContext(),
                        "Wrong OTP entered. Please try again.",
                        Toast.LENGTH_LONG,
                        true
                    ).show()

                }
        }
    }

    private fun authenticate(authenticationBody: AuthenticationBody) {
        authenticationViewModel.authenticate(authenticationBody)
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
                    ProgressDialog.cancelProgressDialog()
                    authUser.saveToken(
                        SAVE_JWT_USER_KEY, it.data?.authenticationData
                    )
                    if (type == "Login") {

                        findNavController().navigate(R.id.action_otp_to_dashboard)
                    } else {
                        val action = OtpDirections.actionOtpToChooseProfile(phoneNum, countryCode)
                        findNavController().navigate(action)
                    }

                }
            }
        }
    }

}