package com.example.bsnanny.views.fragments.feedback

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.bsnanny.R
import com.example.bsnanny.databinding.FragmentFeedbackBinding
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.interceptor.NetworkInterceptor
import com.example.bsnanny.utils.progressDialog.ProgressDialog
import com.example.bsnanny.utils.showSnackBar
import com.example.bsnanny.viewmodels.feedback.FeedbackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment : Fragment() {
    private lateinit var binding : FragmentFeedbackBinding
    private val viewModel:FeedbackViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilFeedback.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.feedbackComment.addTextChangedListener(textWatcher)

        binding.SubmitFeedback.setOnClickListener {
            if (binding.feedbackComment.text.isEmpty() && binding.feedbackStar.rating == 0f) {
                binding.tilFeedback.error = "Please express your views"
                val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                binding.feedbackStar.startAnimation(shakeAnimation)
                Toast.makeText(requireContext(), "Please give a rating", Toast.LENGTH_LONG).show()
            } else {
                val comment = binding.feedbackComment.text.toString().trim()
                val stars = binding.feedbackStar.rating.toInt()
                val bookingId = 1
                val token = "d"
                viewModel.save(comment, stars, bookingId, token)
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
                    if (it.data?.success == true) {
                        Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}