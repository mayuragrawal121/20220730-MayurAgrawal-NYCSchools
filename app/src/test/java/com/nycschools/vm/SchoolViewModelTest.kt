package com.nycschools.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nycschools.data.School
import com.nycschools.data.SchoolDetails
import com.nycschools.service.Repository
import com.nycschools.service.RetrofitService
import com.nycschools.util.getOrAwaitValue
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class SchoolViewModelTest {

    private lateinit var schoolViewModel: SchoolViewModel
    private lateinit var repository: Repository

    @Mock
    lateinit var retrofitService: RetrofitService

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        MockitoAnnotations.initMocks(this)
        repository = Repository(retrofitService)
        schoolViewModel = SchoolViewModel(repository)
    }

    @Test
    fun getAllSchools_success() {
        val schools = listOf(School("1", "ABC"))
        Mockito.`when`(retrofitService.getAllSchools()).thenReturn(
            Observable.fromArray(
                schools
            )
        )
        schoolViewModel.getAllSchools()
        assertEquals(schools, schoolViewModel.getSchools().getOrAwaitValue())
    }

    @Test
    fun getAllSchools_error() {
        Mockito.`when`(retrofitService.getAllSchools()).thenReturn(
            Observable.error(Throwable())
        )
        schoolViewModel.getAllSchools()
        assertEquals(null, schoolViewModel.getSchools().getOrAwaitValue())
    }

    @Test
    fun getSchoolDetails_success() {
        val details = SchoolDetails("1", "ABC", "", "", "", "")
        Mockito.`when`(retrofitService.getSchoolsDetails()).thenReturn(
            Observable.fromArray(
                listOf(details)
            )
        )
        schoolViewModel.getSchoolDetails("1")
        assertEquals(details, schoolViewModel.getSchoolDetails().getOrAwaitValue())
    }

    @Test
    fun getSchoolDetails_success_data_not_matched() {
        val details = SchoolDetails("1", "ABC", "", "", "", "")
        Mockito.`when`(retrofitService.getSchoolsDetails()).thenReturn(
            Observable.fromArray(
                listOf(details)
            )
        )
        schoolViewModel.getSchoolDetails("2")
        assertNotEquals(details, schoolViewModel.getSchoolDetails().getOrAwaitValue())
    }

    @Test
    fun getSchoolDetails_error() {
        Mockito.`when`(retrofitService.getSchoolsDetails()).thenReturn(
            Observable.error(Throwable())
        )
        schoolViewModel.getSchoolDetails("1")
        assertEquals(null, schoolViewModel.getSchoolDetails().getOrAwaitValue())
    }
}