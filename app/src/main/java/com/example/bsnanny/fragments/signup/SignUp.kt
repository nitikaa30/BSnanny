package com.example.bsnanny.fragments.signup

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
import com.example.bsnanny.databinding.FragmentSignUpBinding

class SignUp : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SignUpPhoneNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (binding.SignUpPhoneNum.text.toString().isEmpty()) {
                    binding.SignUpHint.visibility = View.INVISIBLE
                } else {
                    binding.SignUpHint.visibility = View.VISIBLE
                }
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.SignUpHint.visibility = View.VISIBLE
                binding.signupCard.strokeColor = R.color.purpleU1
                binding.signupCard.strokeWidth = 1
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.SignUpPhoneNum.text.toString().isEmpty()) {
                    binding.SignUpHint.visibility = View.VISIBLE
                } else {
                    binding.SignUpHint.visibility = View.VISIBLE
                }
            }
        })
        binding.AuthSignUpBtn.setOnClickListener {
            @SuppressLint("ResourceAsColor")
            when {
                binding.SignUpPhoneNum.text.toString().isEmpty() -> {
                    binding.SignUpHintError.text = "Enter the Phone Number"
                    binding.signupCard.strokeColor = Color.RED
                    binding.signupCard.strokeWidth = 4
                }

                binding.SignUpPhoneNum.text.toString().length < 10 -> {
                    binding.SignUpHintError.text = "Enter the Phone Number"
                    binding.signupCard.strokeColor = Color.RED
                    binding.signupCard.strokeWidth = 4
                }

                else -> {
                    binding.SignUpHintError.text = ""
                    binding.signupCard.strokeColor = R.color.purpleU1
                      findNavController().navigate(R.id.action_signIn_to_otp)
                }
            }
            binding.SignUpPhoneNum.addTextChangedListener(object : TextWatcher {
                @SuppressLint("ResourceAsColor")
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if (binding.SignUpPhoneNum.text.toString().isEmpty()) {
                        binding.SignUpHintError.text = "Enter the Phone Number"
                        binding.signupCard.strokeColor = Color.RED
                        binding.signupCard.strokeWidth = 4
                    } else {
                        binding.SignUpHintError.text = ""
                        binding.signupCard.strokeColor = R.color.purpleU1

                    }
                }


                @SuppressLint("ResourceAsColor")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    binding.SignUpHintError.text = ""
                    binding.signupCard.strokeColor = R.color.purpleU1
                }

                @SuppressLint("ResourceAsColor")
                override fun afterTextChanged(s: Editable?) {
                    if (binding.SignUpPhoneNum.text.toString().isEmpty()) {
                        binding.SignUpHintError.text = "Enter the Phone Number"
                        binding.signupCard.strokeColor = Color.RED
                        binding.signupCard.strokeWidth = 4
                    } else {
                        binding.SignUpHintError.text = ""
                        binding.signupCard.strokeColor = R.color.purpleU1
                    }
                }
            })
        }
        binding.SignInIntent.setOnClickListener {
               findNavController().navigate(R.id.action_signUp_to_signIn)
        }
    }

}