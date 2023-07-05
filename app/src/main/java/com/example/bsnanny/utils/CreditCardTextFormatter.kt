package com.example.bsnanny.utils

import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.TextWatcher

class CreditCardTextFormatter(
    private var separator: String = "-",
    private var divider: Int = 5
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {


        if (s == null) {
            return
        }
        val oldString = s.toString()
        val newString = getNewString(oldString)
        if (newString != oldString) {
            s.replace(0, oldString.length, getNewString(oldString))
        }
    }

    private fun getNewString(value: String): String {


        var newString = value.replace(separator, "")

        var divider = this.divider
        while (newString.length >= divider) {
            newString = newString.substring(
                0,
                divider - 1
            ) + this.separator + newString.substring(divider - 1)
            divider += this.divider + separator.length - 1
        }
        return newString
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}
//class CreditCardTextFormatter(
//    private var separator: String = "-",
//    private var divider: Int = 5
//) : TextWatcher {
//
//    private var isFormatting = false
//
//    override fun afterTextChanged(s: Editable?) {
//        if (isFormatting) {
//            return
//        }
//
//        if (s == null) {
//            return
//        }
//
//        isFormatting = true
//
//        val oldString = s.toString()
//        val newString = getNewString(oldString)
//
//
//        if (newString != oldString) {
//            val filteredString = newString.replace(Regex("[^0-9]"), "")
//            val cursorPosition = s.length - oldString.length + filteredString.length
//
//            s.replace(0, oldString.length, filteredString)
//            s.setSpan(Selection.SELECTION_END, cursorPosition, cursorPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        }
//
//        isFormatting = false
//    }
//
//    private fun getNewString(value: String): String {
//        var newString = value.replace(separator, "")
//
//        var divider = this.divider
//        while (newString.length >= divider) {
//            newString = newString.substring(0, divider - 1) + this.separator + newString.substring(divider - 1)
//            divider += this.divider + separator.length - 1
//        }
//        return newString
//    }
//
//    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//    }
//
//    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//    }
//}

