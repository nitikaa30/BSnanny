package com.example.bsnanny.snackBars

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar


object SnackBars {
    fun showSnackBar(view: View, string: String, color: String){
        val snackBar = Snackbar.make(view,string, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        snackBar.setTextColor(Color.WHITE)
        val tv = snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

        tv.textSize = 16F
        tv.typeface = Typeface.DEFAULT_BOLD

        snackBarView.setBackgroundColor(Color.parseColor(color))
        snackBar.show()
    }
}