package com.example.bsnanny.views.fragments.payment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.payment.PaymentAdapter
import com.example.bsnanny.databinding.AddNewCardDialogBinding
import com.example.bsnanny.databinding.FragmentPaymentBinding
import com.example.bsnanny.models.payment.PaymentModel
import com.example.bsnanny.utils.CardFields
import com.example.bsnanny.utils.CommonTextWatcher
import com.example.bsnanny.utils.CreditCardTextFormatter
import com.example.bsnanny.utils.isCreditCardExpired
import com.example.bsnanny.utils.setEditTextCompoundDrawable
import com.example.bsnanny.utils.validateCard
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.showProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var adapter: PaymentAdapter

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
        binding.makePaymentBtn.setOnClickListener {

        }



    }

    private fun addNewCardDialog(adapter: PaymentAdapter, mList: ArrayList<PaymentModel>) {
        val bind: AddNewCardDialogBinding = AddNewCardDialogBinding.inflate(layoutInflater)
        val dialog: AlertDialog? = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.MyRounded_MaterialComponents_MaterialAlertDialog
        )
            .setView(bind.root)
            .show()
        dialog?.setCanceledOnTouchOutside(false)
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )



        bind.dialogAddCardNumber.addTextChangedListener(CreditCardTextFormatter())

        bind.dialogExpiry.doOnTextChanged { text, start, before, _ ->
            var current = text.toString()
            if (current.length == 2 && start == 1) {
                bind.dialogExpiry.setText("$current/")
                bind.dialogExpiry.setSelection(current.length + 1)
            } else if (current.length == 2 && before == 1) {
                current = current.substring(0, 1)
                bind.dialogExpiry.setText(current)
                bind.dialogExpiry.setSelection(current.length)
            }
            bind.expiryTil.error = null
            bind.expiryTil.isErrorEnabled = false
        }
        val cardNumberTextWatcher = CommonTextWatcher(
            beforeTextChanged = null,
            onTextChanged = { s, _, _, _ ->
                val match1 = Pattern.compile("^4[0-9]{6,}\$")
                val match2 = Pattern.compile("^5[1-5][0-9]{5,}\$")
                when {
                    match1.matcher(s.toString().replace("-", "")).matches() -> {
                        bind.dialogAddCardNumber.setEditTextCompoundDrawable(end = R.drawable.ic_visa_card)
                    }

                    match2.matcher(s.toString().replace("-", "")).matches() -> {
                        bind.dialogAddCardNumber.setEditTextCompoundDrawable(end = R.drawable.ic_master_card)
                    }
                }

                validateCard(
                    s.toString(),
                    "Enter the card Number",
                    bind.cardNumberTil,
                    CardFields.CARD_NO
                )
            },
            afterTextChanged = { editable ->
                if (editable.toString().isEmpty()) {
                    bind.dialogAddCardNumber.setEditTextCompoundDrawable(end = 0)
                }
            }
        )
        bind.dialogAddCardNumber.addTextChangedListener(cardNumberTextWatcher)

        val cardNameTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s, _, _, _ ->
                validateCard(
                    s.toString(),
                    "Enter the name",
                    bind.cardNameTil,
                    CardFields.CARDHOLDER_NAME
                )
            },
            onTextChanged = { s, _, _, _ ->
                bind.cardNameTil.error = null
                bind.cardNameTil.isErrorEnabled = false
            },
            afterTextChanged = { editable ->
                validateCard(
                    editable.toString(),
                    "Enter the name",
                    bind.cardNameTil,
                    CardFields.CARDHOLDER_NAME
                )
            }
        )
        bind.dialogAddCardHolderName.addTextChangedListener(cardNameTextWatcher)


        val cvvTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s, _, _, _ ->
                validateCard(s.toString(), "Enter the CVV", bind.cvvTil, CardFields.CVV)
            },
            onTextChanged = { s, _, _, _ ->
                bind.cvvTil.error = null
                bind.cvvTil.isErrorEnabled = false
            },
            afterTextChanged = { editable ->
                validateCard(editable.toString(), "Enter the CVV", bind.cvvTil, CardFields.CVV)
            }
        )
        bind.cardCvv.addTextChangedListener(cvvTextWatcher)

        val expiryTextWatcher = CommonTextWatcher(
            beforeTextChanged = { s, _, _, _ ->
                validateCard(
                    s.toString(),
                    "Enter the expiry of the card",
                    bind.expiryTil,
                    CardFields.EXPIRY
                )
            },
            onTextChanged = null,
            afterTextChanged = { editable ->
                if (editable.toString().isEmpty()) {
                    bind.expiryTil.error = "Enter the Expiry of the Card"

                } else {
                    if (bind.dialogExpiry.length() >= 7) {
                        val expiry1 = bind.dialogExpiry.text.toString()
                        val ex = expiry1.split("/")
                        val year = ex[1].toDouble().toInt()
                        val month = ex[0].toDouble().toInt()

                        val errorMessage = isCreditCardExpired(year, month)
                        if (errorMessage != null) {
                            bind.expiryTil.error = errorMessage
                        }

                    }
                }
            }
        )
        bind.dialogExpiry.addTextChangedListener(expiryTextWatcher)


        bind.addBtn.setOnClickListener {


            val num = bind.dialogAddCardNumber.text.toString()


            val k: String = num.replace("-", " ")
            val name = bind.dialogAddCardHolderName.text.toString()

            when {
                bind.dialogAddCardNumber.text.toString()
                    .isEmpty() || bind.dialogAddCardNumber.text.toString()
                    .replace("-", "").length != 16 -> {


                    if (bind.dialogAddCardNumber.text.toString().isEmpty()) {
                        bind.cardNumberTil.error = "Enter the Card Number"
                    } else {
                        bind.cardNumberTil.error = "Card Number Must be 16 digits"
                    }


                }

                bind.dialogAddCardHolderName.text.toString().isEmpty() -> {
                    bind.cardNameTil.error = "Enter the Card Holder Name"
                }

                bind.cardCvv.text.toString().isEmpty() -> {
                    bind.cvvTil.error = "Enter the CVV"
                }

                bind.dialogExpiry.text.toString().isEmpty() -> {
                    bind.expiryTil.error = "Enter the Expiry of the Card"
                }

                else -> {
                    mList.add(
                        0,
                        PaymentModel.CardModel(
                            R.drawable.master_card,
                            "${bind.dialogAddCardNumber.text}"
                        )
                    )
                    adapter.submitData(mList)
                    binding.paymentRecyclerView.adapter = adapter


                    adapter.notifyDataSetChanged()
                    lifecycleScope.launch {
                        bind.addBtn.attachTextChangeAnimator()
                        bind.addBtn.showProgress {
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