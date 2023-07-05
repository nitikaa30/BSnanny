package com.example.bsnanny.views.fragments.profile

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
import androidx.lifecycle.lifecycleScope
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


@AndroidEntryPoint
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
        viewLifecycleOwner.lifecycleScope.launch {
            val mid = async {
                viewModel.doSomethingSomething()
            }
           val res = async { viewModel.doSomethingSomethingSomething("123") }
           val re1 = async { viewModel.doSomethingSomethingSomething("111") }
           val re2 = async { viewModel.doSomethingSomethingSomething("222") }
            println(res)
            println(re1)
            println(re2)
        }

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
                binding.ProfileName.requestFocusFromTouch()
                binding.ProfileName.isFocusableInTouchMode = true
            } else {
                hideSoftInput()
            }
            binding.ProfilePhoneNumber.isEnabled = editable
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
                if (editable) View.INVISIBLE else View.VISIBLE
            binding.profileGenderBoy.visibility =
                if (editable) View.INVISIBLE else View.VISIBLE
            binding.profileChildTIL.visibility =
                if (editable) View.INVISIBLE else View.VISIBLE
            binding.AgeTil.visibility = if (editable) View.VISIBLE else View.GONE
            binding.genderSelector.visibility = if (editable) View.VISIBLE else View.GONE
            binding.profileGenderSelectorGirl.visibility =
                if (editable) View.VISIBLE else View.GONE
            binding.profileGenderSelectorBoy.visibility =
                if (editable) View.VISIBLE else View.GONE
            binding.AgeTil.visibility = if (editable) View.VISIBLE else View.GONE
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
                    binding.profileGenderGirl.visibility = View.GONE
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
