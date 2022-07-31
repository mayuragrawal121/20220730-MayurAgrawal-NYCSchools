package com.nycschools.service

import com.nycschools.data.School
import com.nycschools.data.SchoolDetails
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitService {

    @GET("s3k6-pzi2.json")
    fun getAllSchools(): Observable<List<School>>

    @GET("f9bf-2cp4.json")
    fun getSchoolsDetails(): Observable<List<SchoolDetails>>
}