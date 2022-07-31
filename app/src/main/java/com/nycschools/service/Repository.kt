package com.nycschools.service

import javax.inject.Inject

class Repository @Inject constructor(private val retrofitService: RetrofitService) {
    fun getAllSchools() = retrofitService.getAllSchools()
    fun getSchoolsDetails() = retrofitService.getSchoolsDetails()
}