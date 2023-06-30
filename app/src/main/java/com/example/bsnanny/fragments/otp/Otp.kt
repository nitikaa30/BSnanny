package com.example.bsnanny.fragments.otp

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentOtpBinding
import com.example.bsnanny.progressDialog.ProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import es.dmoral.toasty.Toasty
import java.util.concurrent.TimeUnit


class Otp : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var phoneNum: String
    private var verificationOTP: String = ""
    private var v: Vibrator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        generateOTP()
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
                        ProgressDialog.cancelProgressDialog()
                        findNavController().navigate(R.id.action_otp_to_chooseProfile)

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
                    Toasty.error(requireContext(), "Wrong OTP entered. Please try again.", Toast.LENGTH_LONG, true).show()

                }
        }
    }

}