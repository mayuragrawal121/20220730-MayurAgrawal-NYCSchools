package com.nycschools.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.nycschools.R

// Created this class to show loading dialog
class Loading constructor(
    private val activity: Activity
) {
    private var dialog: AlertDialog? = null

    /**
     * Building dialog to display loading
     */
    @SuppressLint("InflateParams")
    fun show() {
        // adding ALERT Dialog builder object and passing activity as parameter
        val builder = AlertDialog.Builder(activity)
        // layoutInflater object and use activity to get layout inflater
        builder.setView(activity.layoutInflater.inflate(R.layout.loading_view, null))
        // setting cancelable - true so user can exit the flow at any time.
        builder.setCancelable(true)
        dialog = builder.create()
        // show loading
        dialog?.show()
    }

    /**
     * dismiss loading
     */
    fun dismiss() {
        dialog?.dismiss()
    }
}