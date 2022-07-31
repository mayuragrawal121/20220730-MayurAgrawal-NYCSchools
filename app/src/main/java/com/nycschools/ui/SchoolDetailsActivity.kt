package com.nycschools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nycschools.R
import com.nycschools.data.SchoolDetails
import com.nycschools.databinding.ActivitySchoolDetailsBinding

// Created this key to pass SchoolDetails data from #SchoolsActivity using bundle.
const val KEY_SCHOOL_DETAILS = "KEY_SCHOOL_DETAILS"

// Created this Activity to display school SAT scores.
// Not extending with BaseActivity as Inject is not required for this as per current requirement.
class SchoolDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchoolDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Using Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(binding.toolbar)
        // Remove default title text.
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // Retrieving data from bundle to show on ui.
        intent.getParcelableExtra<SchoolDetails>(KEY_SCHOOL_DETAILS)?.let {
            parseAndAddViews(it)
        }
    }

    // Parsing bundle SchoolDetails object data and displaying on ui.
    // We can use different approaches here but currently using this,
    // as I feel this reduces lot of duplicate code in xml layout file,
    // we can use custom component approach as well but that is preferred if using same ui on multiple activities.
    private fun parseAndAddViews(schoolDetails: SchoolDetails) {
        createAndAddViewToParent(
            getString(R.string.sat_test_takers), schoolDetails.num_of_sat_test_takers
        )
        createAndAddViewToParent(
            getString(R.string.sat_avg_math_score), schoolDetails.sat_math_avg_score
        )
        createAndAddViewToParent(
            getString(R.string.sat_avg_reading_score), schoolDetails.sat_critical_reading_avg_score
        )
        createAndAddViewToParent(
            getString(R.string.sat_writing_score), schoolDetails.sat_writing_avg_score
        )
    }

    // Building ui dynamically at runtime and adding into parent view.
    private fun createAndAddViewToParent(label: String, value: String) {
        val view = LayoutInflater.from(this@SchoolDetailsActivity)
            .inflate(R.layout.label_value_layout, binding.root, false)
        view.findViewById<TextView>(R.id.label).text = label
        view.findViewById<TextView>(R.id.value).text = value
        binding.container.addView(view)
    }
}