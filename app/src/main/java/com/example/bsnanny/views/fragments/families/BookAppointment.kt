package com.example.bsnanny.views.fragments.families

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentBookAppointmentBinding
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.fragmentTransactions.FragmentTransactions
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.viewmodels.families.BookAppointmentViewModel
import com.example.bsnanny.views.fragments.congratulations.Congratulations
import java.util.*


class BookAppointment : Fragment() {

    private lateinit var binding: FragmentBookAppointmentBinding
    private val viewModel: BookAppointmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBookAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.FromED.setOnClickListener {
            datePicker(binding.FromED)
        }
        binding.TOED.setOnClickListener {
            datePicker(binding.TOED)
        }
        binding.btn.setOnClickListener {
            if(binding.FromED.text?.isEmpty() == true && binding.TOED.text?.isEmpty() == true){
                Toast.makeText(requireContext(), "Please fill the dates", Toast.LENGTH_LONG).show()
            }
            else{
                val parent_id=""
                val start=binding.FromED.text.toString()
                val end=binding.TOED.text.toString()
                viewModel.apply(parent_id,start,end)
                FragmentTransactions.replaceFragment(Congratulations(), requireActivity(), Congratulations().tag)
            }
        }
        viewModel.res.observe(viewLifecycleOwner, Observer {
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
                    if (it.data?.sucess == true) {
                        Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    private fun datePicker(text : EditText) {
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selYear, selMonth, dayOfMonth ->
            val selDate = "$dayOfMonth/${selMonth +1}/$selYear"
            text.setText(selDate)

        },year,month,day)
        datePickerDialog.show()
        datePickerDialog.datePicker.background = AppCompatResources.getDrawable(requireContext(), R.color.white)
        datePickerDialog.datePicker.setBackgroundColor(Color.TRANSPARENT)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 864000
    }



}