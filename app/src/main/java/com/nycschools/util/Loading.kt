package com.nycschools.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.nycschools.R

class Loading constructor(
    private val activity: Activity
) {
    private var dialog: AlertDialog? = null

    @SuppressLint("InflateParams")
    fun show() {
        // adding ALERT Dialog builder object and passing activity as parameter
        val builder = AlertDialog.Builder(activity)

        // layoutInflater object and use activity to get layout inflater
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_view, null))
        builder.setCancelable(true)
        dialog = builder.create()
        dialog?.show()
    }

    // dismiss method
    fun dismiss() {
        dialog?.dismiss()
    }
}