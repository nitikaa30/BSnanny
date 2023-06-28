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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

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
        setupAutoCompleteTextView()
        setupButtonListeners()
        observeViewModel()
    }

    private fun setupAutoCompleteTextView() {
        val age = resources.getStringArray(R.array.Age)
        val ageArrayAdapter = context?.let {
            ArrayAdapter(it, R.layout.dropdown_item_pricing, age)
        }
        binding.autoCompleteTextViewAge.setAdapter(ageArrayAdapter)
    }

    private fun setupButtonListeners() {
        binding.editProfileBtn.setOnClickListener {
            viewModel.editMode.value = true
        }

        binding.SVProfileBtn.setOnClickListener {
            viewModel.editMode.value = false
        }

        binding.profileGenderSelectorBoy.setOnClickListener {
            viewModel.genderSelect.value = binding.profileGenderSelectorBoy.text.toString()
        }

        binding.profileGenderSelectorGirl.setOnClickListener {
            viewModel.genderSelect.value = binding.profileGenderSelectorGirl.text.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeViewModel() {
        viewModel.editMode.observe(viewLifecycleOwner) { isEditMode ->
            val visibility = if (isEditMode) View.GONE else View.VISIBLE
            binding.editProfileBtn.visibility = visibility
            binding.SVProfileBtn.visibility = visibility
            binding.ProfileName.focusable = if (isEditMode) View.FOCUSABLE else View.NOT_FOCUSABLE
            binding.ProfileEmail.focusable = if (isEditMode) View.FOCUSABLE else View.NOT_FOCUSABLE
            binding.ProfileAddress.focusable = if (isEditMode) View.FOCUSABLE else View.NOT_FOCUSABLE
            binding.ProfileDescription.focusable = if (isEditMode) View.FOCUSABLE else View.NOT_FOCUSABLE
            binding.ProfileChildDetails.focusable = View.NOT_FOCUSABLE
            binding.AgeTil.visibility = if (isEditMode) View.VISIBLE else View.INVISIBLE
            binding.profileChildTIL.visibility = if (isEditMode) View.INVISIBLE else View.VISIBLE
            binding.profileGenderSelectorBoy.visibility = if (isEditMode) View.VISIBLE else View.INVISIBLE
            binding.profileGenderSelectorGirl.visibility = if (isEditMode) View.VISIBLE else View.INVISIBLE
            binding.ProfileToolbar.title = if (isEditMode) "Edit Profile" else "Profile"
        }

        viewModel.genderSelect.observe(viewLifecycleOwner) { selectedGender ->
            val isBoySelected = selectedGender == "Boy"
            binding.profileGenderSelectorBoy.visibility = if (isBoySelected) View.VISIBLE else View.INVISIBLE
            binding.profileGenderSelectorGirl.visibility = if (isBoySelected) View.INVISIBLE else View.VISIBLE
        }
    }
}