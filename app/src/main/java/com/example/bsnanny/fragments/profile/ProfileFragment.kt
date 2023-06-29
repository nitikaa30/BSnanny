package com.example.bsnanny.fragments.profile

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private var genderSelect: String = "Boy"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val age = resources.getStringArray(R.array.Age)
        val ageArrayAdapter = context?.let {
            ArrayAdapter(it, R.layout.dropdown_item_pricing, age)
        }
        binding.autoCompleteTextViewAge.setAdapter(ageArrayAdapter)

        binding.editProfileBtn.setOnClickListener {
            viewModel.setProfileEditable(true)
            editProfileListener()
        }

        binding.SVProfileBtn.setOnClickListener {
            viewModel.setProfileEditable(false)
            saveProfileListener()
        }

        viewModel.isProfileEditable.observe(viewLifecycleOwner) { editable ->
            if (editable) {
                binding.ProfileName.requestFocusFromTouch()
                binding.ProfileName.isFocusableInTouchMode = true
                showSoftInput(binding.ProfileName)
                binding.ProfileEmail.requestFocusFromTouch()
                binding.ProfileEmail.isFocusableInTouchMode = true
                showSoftInput(binding.ProfileEmail)
                binding.ProfileAddress.requestFocusFromTouch()
                binding.ProfileAddress.isFocusableInTouchMode = true
                showSoftInput(binding.ProfileAddress)
                binding.ProfileDescription.isFocusableInTouchMode = true
                binding.ProfileDescription.requestFocusFromTouch()
                showSoftInput(binding.ProfileDescription)
                binding.ProfilePhoneNumber.isFocusableInTouchMode = true
                binding.ProfilePhoneNumber.requestFocusFromTouch()
                showSoftInput(binding.ProfilePhoneNumber)
            } else {
                hideSoftInput()
            }

            binding.ProfileName.isEnabled = editable
            binding.ProfileEmail.isEnabled = editable
            binding.ProfileDescription.isEnabled = editable
            binding.ProfileAddress.isEnabled = editable
            binding.ProfileChildDetails.isEnabled = editable
            binding.autoCompleteTextViewAge.isEnabled = editable
            binding.addUploadImage1.visibility = if (editable) View.VISIBLE else View.GONE
            binding.ProfileToolbar.title = if (editable) "Edit Profile" else "Profile"
            binding.editProfileBtn.visibility = if (editable) View.GONE else View.VISIBLE
            binding.SVProfileBtn.visibility = if (editable) View.VISIBLE else View.GONE
            binding.profileGenderGirl.visibility =
                if (editable) View.VISIBLE else View.GONE
            binding.profileGenderBoy.visibility =
                if (editable) View.VISIBLE else View.GONE
            binding.profileChildTIL.visibility =
                if (editable) View.VISIBLE else View.GONE
            binding.AgeTil.visibility = if (editable) View.VISIBLE else View.GONE
            binding.genderSelector.visibility = if (editable) View.VISIBLE else View.GONE
            binding.profileGenderSelectorGirl.visibility =
                if (editable) View.VISIBLE else View.GONE
            binding.profileGenderSelectorBoy.visibility =
                if (editable) View.VISIBLE else View.GONE
        }

        binding.profileGenderSelectorBoy.setOnClickListener {
            genderSelect = binding.profileGenderSelectorBoy.text.toString()
        }

        binding.profileGenderSelectorGirl.setOnClickListener {
            genderSelect = binding.profileGenderSelectorGirl.text.toString()
        }

        // Observe gender selection and update UI accordingly
        viewModel.isProfileEditable.observe(viewLifecycleOwner) { editable ->
            if (!editable) {
                if (genderSelect == "Boy") {
                    binding.profileGenderBoy.visibility = View.VISIBLE
                    binding.profileGenderGirl.visibility=View.GONE
                } else {
                    binding.profileGenderGirl.visibility = View.VISIBLE
                    binding.profileGenderBoy.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun showSoftInput(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideSoftInput() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveProfileListener() {
        viewModel.setProfileEditable(false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun editProfileListener() {
        viewModel.setProfileEditable(true)
    }
}
//
//class ProfileFragment : Fragment() {
//
//    private lateinit var binding: FragmentProfileBinding
//    private var genderSelect: String = "Boy"
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val age = resources.getStringArray(R.array.Age)
//        val ageArrayAdapter = context?.let {
//            ArrayAdapter(it, R.layout.dropdown_item_pricing, age)
//        }
//        binding.autoCompleteTextViewAge.setAdapter(ageArrayAdapter)
//
//        binding.editProfileBtn.setOnClickListener {
//            editProfileListener(view)
//        }
//
//        binding.SVProfileBtn.setOnClickListener {
//            saveProfileListener()
//
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun saveProfileListener() {
//        binding.editProfileBtn.visibility = View.VISIBLE
//        binding.ProfileName.focusable = View.NOT_FOCUSABLE
//        binding.ProfileEmail.focusable = View.NOT_FOCUSABLE
//        binding.ProfileDescription.focusable = View.NOT_FOCUSABLE
//        binding.ProfileAddress.focusable = View.NOT_FOCUSABLE
//        binding.profileChildTIL.visibility = View.VISIBLE
//        binding.ProfileChildDetails.focusable = View.NOT_FOCUSABLE
//        binding.AgeTil.visibility = View.INVISIBLE
//        binding.profileGenderSelectorGirl.visibility = View.INVISIBLE
//        binding.profileGenderSelectorBoy.visibility = View.INVISIBLE
//        binding.ProfileToolbar.title = "Profile"
//        binding.genderSelector.visibility = View.VISIBLE
//        binding.SVProfileBtn.visibility = View.GONE
//        binding.profileGenderSelectorBoy.setOnClickListener {
//            genderSelect = binding.profileGenderSelectorBoy.text.toString()
//        }
//        binding.profileGenderSelectorGirl.setOnClickListener {
//            genderSelect = binding.profileGenderSelectorGirl.text.toString()
//        }
//        if (genderSelect == "Boy") {
//            binding.profileGenderBoy.visibility = View.VISIBLE
//        } else {
//            binding.profileGenderGirl.visibility = View.VISIBLE
//            binding.profileGenderBoy.visibility = View.INVISIBLE
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun editProfileListener(view: View) {
//        binding.ProfileName.focusable = View.FOCUSABLE
//        binding.ProfileDescription.focusable = View.FOCUSABLE
//        binding.ProfileName.requestFocusFromTouch()
//        binding.ProfileName.isFocusableInTouchMode = true
//        val imm =
//            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//        imm!!.showSoftInput(binding.ProfileName, InputMethodManager.SHOW_IMPLICIT)
//        binding.ProfileEmail.focusable = View.FOCUSABLE
//        binding.ProfileEmail.requestFocusFromTouch()
//        binding.ProfileEmail.isFocusableInTouchMode = true
//        val imm1 =
//            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//        imm1!!.showSoftInput(binding.ProfileEmail, InputMethodManager.SHOW_IMPLICIT)
//        binding.ProfileAddress.focusable = View.FOCUSABLE
//        binding.ProfileAddress.requestFocusFromTouch()
//        binding.ProfileAddress.isFocusableInTouchMode = true
//        val imm2 =
//            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//        imm2!!.showSoftInput(binding.ProfileAddress, InputMethodManager.SHOW_IMPLICIT)
//        binding.ProfileDescription.isFocusableInTouchMode = true
//        binding.ProfileDescription.requestFocusFromTouch()
//        val imm3 = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//        imm3!!.showSoftInput(binding.ProfileDescription, InputMethodManager.SHOW_IMPLICIT)
//        binding.addUploadImage1.visibility = View.VISIBLE
//        binding.ProfileToolbar.title = "Edit Profile"
//
//        binding.editProfileBtn.visibility = View.GONE
//        binding.SVProfileBtn.visibility = View.VISIBLE
//
//        binding.profileGenderGirl.visibility = View.INVISIBLE
//        binding.profileGenderBoy.visibility = View.INVISIBLE
//        binding.profileChildTIL.visibility = View.INVISIBLE
//        binding.genderSelector.visibility = View.VISIBLE
//        binding.profileGenderSelectorBoy.visibility = View.VISIBLE
//        binding.profileGenderSelectorGirl.visibility = view.visibility
//        binding.AgeTil.visibility = View.VISIBLE
//    }
//}