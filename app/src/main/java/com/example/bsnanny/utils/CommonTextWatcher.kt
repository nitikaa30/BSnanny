package com.example.bsnanny.utils

import android.text.Editable
import android.text.TextWatcher

class CommonTextWatcher(private val beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
                        private val onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
                        private val afterTextChanged: ((Editable?) -> Unit)? = null) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }
}