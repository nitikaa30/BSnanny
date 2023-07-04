package com.example.bsnanny.fragments.signin

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentSignInBinding
import com.example.bsnanny.progressDialog.ProgressDialog

class SignIn : Fragment() {
    private lateinit var binding: FragmentSignInBinding
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

}