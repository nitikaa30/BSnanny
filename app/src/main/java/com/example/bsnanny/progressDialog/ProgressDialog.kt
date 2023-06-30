package com.example.bsnanny.progressDialog

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import com.example.bsnanny.R

object ProgressDialog {
    private lateinit var progressDialog : Dialog
    fun showProgressDialog(context: Context){
        progressDialog = Dialog(context)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
    fun cancelProgressDialog(){
        progressDialog.dismiss()
    }
}