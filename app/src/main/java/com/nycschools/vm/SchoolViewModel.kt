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
    // Created this to add observable.
    private val compositeDisposable = CompositeDisposable()

    // Created this to post schools list and observe on ui.
    private val schools = MutableLiveData<List<School>>()

    // Created this to post school details and observe on ui.
    private val schoolDetails = MutableLiveData<SchoolDetails>()

    // Created this to hold/cache schools details and use further.
    private val schoolsDetailsData = mutableListOf<SchoolDetails>()

    fun getSchools(): LiveData<List<School>> {
        return schools
    }

    fun getSchoolDetails(): LiveData<SchoolDetails> {
        return schoolDetails
    }

    /**
     * Here trying to retrieve schools list from API and post live data value.
     */
    fun getAllSchools() {
        compositeDisposable.add(
            repository.getAllSchools().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    schools.value = it
                }, {
                    schools.value = null
                })
        )
    }

    /**
     * Here trying to retrieve schoolsDetailsData using dbn from API,
     * and caching it for further use also post live data value.
     * @param - dbn - unique school id
     *
     * Note: we will not call API if data is already available in schoolsDetailsData.
     */
    fun getSchoolDetails(dbn: String) {
        if (schoolsDetailsData.isNotEmpty()) {
            parseSchoolsDataAndReturnSchoolDetails(dbn)
            return
        }
        compositeDisposable.add(
            repository.getSchoolsDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    schoolsDetailsData.addAll(it)
                    parseSchoolsDataAndReturnSchoolDetails(dbn)
                }, {
                    schoolDetails.value = null
                })
        )
    }

    // Created this method to parse schoolsDetailsData using dbn and post live data value
    // also used to return null if dbn is not found in schoolsDetailsData
    private fun parseSchoolsDataAndReturnSchoolDetails(dbn: String) {
        schoolDetails.value = schoolsDetailsData.find { it.dbn == dbn }
    }

    // Overriding this method to clear compositeDisposable
    // This method will be called when this ViewModel is no longer used and will be destroyed.
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}