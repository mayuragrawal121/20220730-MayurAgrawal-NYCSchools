package com.nycschools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nycschools.R
import com.nycschools.data.SchoolDetails
import com.nycschools.databinding.ActivitySchoolDetailsBinding

const val KEY_SCHOOL_DETAILS = "KEY_SCHOOL_DETAILS"

class SchoolDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchoolDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(binding.toolbar)
        // Remove default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)
        intent.getParcelableExtra<SchoolDetails>(KEY_SCHOOL_DETAILS)?.let {
            parseAndAddViews(it)
        }
    }

    private fun parseAndAddViews(schoolDetails: SchoolDetails) {
        schoolDetails.num_of_sat_test_takers?.let {
            createAndAddViewToParent(
                getString(R.string.sat_test_takers), it
            )
        }
        schoolDetails.sat_math_avg_score?.let {
            createAndAddViewToParent(
                getString(R.string.sat_avg_math_score), it
            )
        }
        schoolDetails.sat_critical_reading_avg_score?.let {
            createAndAddViewToParent(
                getString(R.string.sat_avg_reading_score), it
            )
        }
        schoolDetails.sat_writing_avg_score?.let {
            createAndAddViewToParent(
                getString(R.string.sat_writing_score), it
            )
        }
    }

    private fun createAndAddViewToParent(label: String, value: String) {
        val view = LayoutInflater.from(this@SchoolDetailsActivity)
            .inflate(R.layout.label_value_layout, binding.root, false)

        view.findViewById<TextView>(R.id.label).text = label
        view.findViewById<TextView>(R.id.value).text = value
        binding.container.addView(view)
    }
}