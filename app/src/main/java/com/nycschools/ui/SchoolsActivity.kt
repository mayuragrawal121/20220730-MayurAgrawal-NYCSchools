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
                loading.show()
                viewModel.getSchoolDetails(dbn)
            }
        })
        setRecyclerViewAdapter(adapter = adapter)
        loading.show()
        viewModel.getAllSchools()
        observeSchoolsData(adapter = adapter)
        observeSchoolDetailsData()
    }

    private fun setRecyclerViewAdapter(adapter: SchoolsAdapter) {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun observeSchoolsData(adapter: SchoolsAdapter) {
        viewModel.getSchools().observe(this) {
            loading.dismiss()
            it?.let {
                adapter.setSchoolList(it)
            } ?: run {
                displayMessage(getString(R.string.generic_error))
            }
        }
    }

    private fun observeSchoolDetailsData() {
        viewModel.getSchoolDetails().observe(this) {
            loading.dismiss()
            it?.let { schoolDetails ->
                navigateToSchoolDetailsActivity(schoolDetails)
            } ?: run {
                displayMessage(getString(R.string.record_not_found))
            }
        }
    }

    private fun navigateToSchoolDetailsActivity(schoolDetails: SchoolDetails) {
        startActivity(
            Intent(
                this,
                SchoolDetailsActivity::class.java
            ).putExtras(Bundle().apply {
                putParcelable(
                    KEY_SCHOOL_DETAILS, schoolDetails
                )
            })
        )
    }

    private fun displayMessage(message: String) {
        Snackbar.make(
            binding.root, message, Snackbar.LENGTH_SHORT
        ).show()
    }
}