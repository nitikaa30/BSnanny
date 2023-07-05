package com.example.bsnanny.views.fragments.signin

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<<<< Temporary merge branch 1:app/src/main/java/com/example/bsnanny/fragments/signin/SignIn.kt
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentSignInBinding
import com.example.bsnanny.progressDialog.ProgressDialog
=========
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentSignInBinding
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.viewmodels.CheckUserViewModel
import com.example.bsnanny.viewmodels.authentication.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
>>>>>>>>> Temporary merge branch 2:app/src/main/java/com/example/bsnanny/views/fragments/signin/SignIn.kt

class SignIn : Fragment() {
    private lateinit var binding: FragmentSignInBinding
<<<<<<<<< Temporary merge branch 1:app/src/main/java/com/example/bsnanny/fragments/signin/SignIn.kt
=========
    private val checkUserViewModel: CheckUserViewModel by viewModels()
    private val authenticationViewModel : AuthenticationViewModel by viewModels()
>>>>>>>>> Temporary merge branch 2:app/src/main/java/com/example/bsnanny/views/fragments/signin/SignIn.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_sign_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.phoneNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (binding.phoneNum.text.toString().isEmpty()) {
                    binding.hint.visibility = View.INVISIBLE
                } else {
                    binding.hint.visibility = View.VISIBLE
                }
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.hint.visibility = View.VISIBLE
                binding.card1.strokeColor = R.color.purpleU1
                binding.card1.strokeWidth = 1
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.phoneNum.text.toString().isEmpty()) {
                    binding.hint.visibility = View.VISIBLE
                } else {
                    binding.hint.visibility = View.VISIBLE
                }
            }
        })
        binding.AuthSignInBtn.setOnClickListener {
            @SuppressLint("ResourceAsColor")
            when {
                binding.phoneNum.text.toString().isEmpty() -> {
                    binding.hintError.text = "Enter the Phone Number"
                    binding.card1.strokeColor = Color.RED
                    binding.card1.strokeWidth = 4
                }

                binding.phoneNum.text.toString().length < 10 -> {
                    binding.hintError.text = "Enter the Phone Number"
                    binding.card1.strokeColor = Color.RED
                    binding.card1.strokeWidth = 4
                }

                else -> {
                    binding.hintError.text = ""
                    binding.card1.strokeColor = R.color.purpleU1
                    val countryCode = binding.countryCode.selectedCountryCode
                    val phoneNum = "+" + countryCode + binding.phoneNum.text.toString()
                    val action = SignInDirections.actionSignInToOtp(phoneNum)
                    findNavController().navigate(action)
                }
            }
            binding.phoneNum.addTextChangedListener(object : TextWatcher {
                @SuppressLint("ResourceAsColor")
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if (binding.phoneNum.text.toString().isEmpty()) {
                        binding.hintError.text = "Enter the Phone Number"
                        binding.card1.strokeColor = Color.RED
                        binding.card1.strokeWidth = 4
                    } else {
                        binding.hintError.text = ""
                        binding.card1.strokeColor = R.color.purpleU1

                    }
                }


                @SuppressLint("ResourceAsColor")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    binding.hintError.text = ""
                    binding.card1.strokeColor = R.color.purpleU1
                }

                @SuppressLint("ResourceAsColor")
                override fun afterTextChanged(s: Editable?) {
                    if (binding.phoneNum.text.toString().isEmpty()) {
                        binding.hintError.text = "Enter the Phone Number"
                        binding.card1.strokeColor = Color.RED
                        binding.card1.strokeWidth = 4
                    } else {
                        binding.hintError.text = ""
                        binding.card1.strokeColor = R.color.purpleU1
                    }
                }
            })
        }
        binding.signUpIntent.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }

    }

<<<<<<<<< Temporary merge branch 1:app/src/main/java/com/example/bsnanny/fragments/signin/SignIn.kt
=========
    private fun subscribeObservers() {
        checkUserViewModel.res.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResults.Error -> {
                    ProgressDialog.cancelProgressDialog()
                    showSnackBar(binding.root, it.errorMessage.toString(), "#AA4A44")
                }

                is NetworkResults.Loading -> {
                    ProgressDialog.showProgressDialog(requireContext())
                }

                is NetworkResults.Success -> {
                    ProgressDialog.cancelProgressDialog()
                    if (it.data?.success == true) {

                        val type = "Login"
                        val countryCode = binding.countryCode.selectedCountryCode
                        val phoneNum = "+" + countryCode + binding.phoneNum.text.toString()
                        val action = SignInDirections.actionSignInToOtp(phoneNum, countryCode, type)
                        findNavController().navigate(action)
                    } else {
                        showSnackBar(binding.root, it.data!!.msg, "#AA4A44")
                    }
                }
            }
        }

    }

    private fun checkValidations() {

    }

    private fun checkUser(checkUserBody: CheckUserBody) {
        checkUserViewModel.checkUserData(checkUserBody)
    }
    private fun authenticate(authenticationBody: AuthenticationBody){
        authenticationViewModel.authenticate(authenticationBody)
    }
>>>>>>>>> Temporary merge branch 2:app/src/main/java/com/example/bsnanny/views/fragments/signin/SignIn.kt
}