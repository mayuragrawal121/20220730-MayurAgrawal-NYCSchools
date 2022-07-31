package com.nycschools.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nycschools.data.School
import com.nycschools.data.SchoolDetails
import com.nycschools.service.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchoolViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val schools = MutableLiveData<List<School>>()
    private val schoolDetails = MutableLiveData<SchoolDetails>()
    private val schoolsDetailsData = mutableListOf<SchoolDetails>()

    fun getSchools(): LiveData<List<School>> {
        return schools
    }

    fun getSchoolDetails(): LiveData<SchoolDetails> {
        return schoolDetails
    }

    fun getAllSchools() {
        compositeDisposable.add(repository.getAllSchools()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                schools.postValue(it)
            }, {
                schools.postValue(null)
            }
            )
        )
    }

    fun getSchoolDetails(dbn: String) {
        if (schoolsDetailsData.isNotEmpty()) {
            parseSchoolsDataAndReturnSchoolDetails(dbn)
            return
        }
        compositeDisposable.add(repository.getSchoolsDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                schoolsDetailsData.addAll(it)
                parseSchoolsDataAndReturnSchoolDetails(dbn)
            }, {
                schoolDetails.postValue(null)
            }
            )
        )
    }

    private fun parseSchoolsDataAndReturnSchoolDetails(dbn: String) {
        val details = schoolsDetailsData.find { it.dbn == dbn }
        details?.let { schoolDetails.postValue(it) } ?: run { schoolDetails.postValue(null) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}