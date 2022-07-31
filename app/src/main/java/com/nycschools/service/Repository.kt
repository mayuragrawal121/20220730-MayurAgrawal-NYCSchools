package com.nycschools.service

import javax.inject.Inject

/**
 * The repository pattern is an abstraction used to hide the multiple data sources that we may have in our application.
 * */
class Repository @Inject constructor(private val retrofitService: RetrofitService) {
    fun getAllSchools() = retrofitService.getAllSchools()
    fun getSchoolsDetails() = retrofitService.getSchoolsDetails()
}