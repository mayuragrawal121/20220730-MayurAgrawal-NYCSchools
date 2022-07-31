package com.nycschools.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created SchoolDetails data class to handle SchoolDetails data.
 * And extending with Parcelable to pass data class from one Activity to another Activity.
 * */
@Parcelize
data class SchoolDetails(
    val dbn: String,
    val school_name: String? = null,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String
) : Parcelable
