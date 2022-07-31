package com.nycschools.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nycschools.R
import com.nycschools.base.BaseActivity
import com.nycschools.data.SchoolDetails
import com.nycschools.databinding.ActivitySchoolsBinding
import com.nycschools.ui.adapter.ClickListener
import com.nycschools.ui.adapter.SchoolsAdapter
import com.nycschools.util.Loading
import com.nycschools.vm.SchoolViewModel
import javax.inject.Inject

// Created this Activity to display all schools
// extending with BaseActivity as Inject is required for this as per current requirement
class SchoolsActivity : BaseActivity() {
    private lateinit var binding: ActivitySchoolsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SchoolViewModel

    private val loading = Loading(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(binding.toolbar)
        // Remove default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SchoolViewModel::class.java)

        val adapter = SchoolsAdapter(object : ClickListener {
            override fun onClick(dbn: String) {
                // Showing loading when trying to retrieve data from API.
                loading.show()
                // Calling below method to retrieve data from API.
                viewModel.getSchoolDetails(dbn)
            }
        })
        setRecyclerViewAdapter(adapter = adapter)
        // Showing loading when trying to retrieve data from API.
        loading.show()
        // Calling below method to retrieve data from API.
        viewModel.getAllSchools()
        // Observing schools list in below method.
        observeSchoolsData(adapter = adapter)
        // Observing schools details in below method.
        observeSchoolDetailsData()
    }

    // Created this method to set adapter on recyclerview,
    // and add divider line between list items.
    private fun setRecyclerViewAdapter(adapter: SchoolsAdapter) {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL
            )
        )
    }

    // Created this method to observe schools list data and handle error.
    private fun observeSchoolsData(adapter: SchoolsAdapter) {
        viewModel.getSchools().observe(this) {
            // Added this to dismiss loading dialog.
            loading.dismiss()
            // Here checking null because returning null from viewModel if there is an error
            // It can be network or server error
            it?.let {
                adapter.setSchoolList(it)
            } ?: run {
                displayMessage(getString(R.string.generic_error))
            }
        }
    }

    // Created this method to observe school details and handle error.
    private fun observeSchoolDetailsData() {
        viewModel.getSchoolDetails().observe(this) {
            // Added this to dismiss loading dialog.
            loading.dismiss()
            // Here checking null because returning null from viewModel if there is an error
            // It can be network or server error
            it?.let { schoolDetails ->
                navigateToSchoolDetailsActivity(schoolDetails)
            } ?: run {
                displayMessage(getString(R.string.record_not_found))
            }
        }
    }

    // Created this method to navigate on school details activity.
    private fun navigateToSchoolDetailsActivity(schoolDetails: SchoolDetails) {
        startActivity(
            Intent(
                this, SchoolDetailsActivity::class.java
            ).putExtras(Bundle().apply {
                putParcelable(
                    KEY_SCHOOL_DETAILS, schoolDetails
                )
            })
        )
    }

    // Created this method to show SnackBar for error handling.
    private fun displayMessage(message: String) {
        Snackbar.make(
            binding.root, message, Snackbar.LENGTH_SHORT
        ).show()
    }
}