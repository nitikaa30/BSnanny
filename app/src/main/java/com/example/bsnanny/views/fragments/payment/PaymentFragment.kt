package com.example.bsnanny.views.fragments.payment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.payment.PaymentAdapter
import com.example.bsnanny.databinding.FragmentPaymentBinding
import com.example.bsnanny.models.payment.PaymentModel
import com.example.bsnanny.utils.CreditCardTextFormatter
import com.example.bsnanny.utils.isCreditCardExpired
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.showProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var adapter: PaymentAdapter

    // private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mList = ArrayList<PaymentModel>()

        mList.add(PaymentModel.CardModel(R.drawable.master_card, "Adam Smith XXXX 4444"))
        mList.add(PaymentModel.CardModel(R.drawable.master_card, "Adam Smith XXXX 1111"))
        mList.add(PaymentModel.CardModel(R.drawable.master_card, "Adam Smith XXXX 4242"))
        mList.add(PaymentModel.CardModel(R.drawable.master_card, "Adam Smith XXXX 4444"))

        binding.paymentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mList.add(PaymentModel.AddNewCard("Add New Card", R.drawable.add_cards))
        adapter = PaymentAdapter {
            addNewCardDialog(adapter, mList)
        }
        adapter.submitData(mList)
        binding.paymentRecyclerView.adapter = adapter


    }

    private fun addNewCardDialog(adapter: PaymentAdapter, mList: ArrayList<PaymentModel>) {
        val dialog: AlertDialog? = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.MyRounded_MaterialComponents_MaterialAlertDialog
        )
            .setView(R.layout.add_new_card_dialog)
            .show()
        dialog?.setCanceledOnTouchOutside(false)
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val cardNumber = dialog.findViewById<EditText>(R.id.dialog_add_card_number)
        val cardHolderName = dialog.findViewById<EditText>(R.id.dialog_add_card_holder_name)
        val cardNumberTil = dialog.findViewById<TextInputLayout>(R.id.til1)
        val expiry = dialog.findViewById<EditText>(R.id.dialog_expiry)
        val cardNameTil = dialog.findViewById<TextInputLayout>(R.id.til2)
        val cvvTil = dialog.findViewById<TextInputLayout>(R.id.cvvTil)
        val expiryTil = dialog.findViewById<TextInputLayout>(R.id.expiryTil)
        val cardCvv = dialog.findViewById<EditText>(R.id.card_cvv)
        val addCardBtn = dialog.findViewById<Button>(R.id.addBtn)
        cardNumber?.addTextChangedListener(CreditCardTextFormatter())

        expiry!!.doOnTextChanged { text, start, before, _ ->
            var current = text.toString()
            if (current.length == 2 && start == 1) {
                expiry.setText("$current/")
                expiry.setSelection(current.length + 1)
            } else if (current.length == 2 && before == 1) {
                current = current.substring(0, 1)
                expiry.setText(current)
                expiry.setSelection(current.length)
            }
            expiryTil?.error = null
            expiryTil?.isErrorEnabled = false
        }
//        expiry.doAfterTextChanged {
//
//
//        }
        cardNumber?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val match1 = Pattern.compile("^4[0-9]{6,}\$")
                val match2 = Pattern.compile("^5[1-5][0-9]{5,}\$")
                when {
                    match1.matcher(cardNumber.text.toString().replace("-", "")).matches() -> {
                        cardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_visa_card,
                            0
                        )
                    }

                    match2.matcher(cardNumber.text.toString().replace("-", "")).matches() -> {
                        cardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_master_card,
                            0
                        )
                    }
                }

                if (cardNumber.text.toString().isEmpty()) {
                    cardNumberTil?.error = "Enter the Card Number"
                } else {
                    cardNumberTil?.error = null
                    cardNumberTil?.isErrorEnabled = false
                }

            }

            override fun afterTextChanged(s: Editable?) {
                if (cardNumber.text.toString().isEmpty()) {
                    cardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
        })
        cardHolderName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (cardHolderName.text.toString().isEmpty()) {
                    cardNameTil?.error = "Enter the Name"
                } else {
                    cardNameTil?.error = null
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cardNameTil?.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                if (cardHolderName.text.toString().isEmpty()) {
                    cardNameTil?.error = "Enter the Name"
                } else {
                    cardNameTil?.error = null
                }
            }

        })

        cardCvv?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (cardCvv.text.toString().isEmpty()) {
                    cvvTil?.error = "Enter the CVV"
                } else {
                    cvvTil?.error = null
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cvvTil?.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                if (cardCvv.text.toString().isEmpty()) {
                    cvvTil?.error = "Enter the CVV"
                } else {
                    cvvTil?.error = null
                }
            }

        })

        expiry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (expiry.text.toString().isEmpty()) {
                    expiryTil?.error = "Enter the Expiry of the Card"
                } else {
                    expiryTil?.error = null
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (expiry.text.toString().isEmpty()) {
                    expiryTil?.error = "Enter the Expiry of the Card"

                } else {
                    if (expiry.length() >= 7) {
                        val expiry1 = expiry.text.toString()
                        val ex = expiry1.split("/")
                        val year = ex[1].toDouble().toInt()
                        val month = ex[0].toDouble().toInt()

                        val errorMessage = isCreditCardExpired(year, month)
                        if (errorMessage != null) {
                            expiryTil?.error = errorMessage
                        }

                    }
                }
            }

        })
        addCardBtn?.setOnClickListener {


            val num = cardNumber?.text.toString()


            val k: String = num.replace("-", " ")
            val name = cardHolderName?.text.toString()

            when {
                cardNumber?.text.toString().isEmpty() -> {
                    cardNumberTil?.error = "Enter the Card Number"
                }

                cardHolderName?.text.toString().isEmpty() -> {
                    cardNameTil?.error = "Enter the Card Holder Name"
                }

                cardCvv?.text.toString().isEmpty() -> {
                    cvvTil?.error = "Enter the CVV"
                }

                expiry.text.toString().isEmpty() -> {
                    expiryTil?.error = "Enter the Expiry of the Card"
                }

                else -> {
                    mList.add(
                        0,
                        PaymentModel.CardModel(R.drawable.master_card, "${cardNumber?.text}")
                    )
                    adapter.submitData(mList)
                    binding.paymentRecyclerView.adapter = adapter


                    adapter.notifyDataSetChanged()
                    lifecycleScope.launch {
                        addCardBtn.attachTextChangeAnimator()
                        addCardBtn.showProgress {
                            progressColor = Color.WHITE
                            buttonTextRes = R.string.loading

                        }
                        delay(20000)
                        dialog.dismiss()

                    }
                }
            }
        }
    }
}