package com.nycschools.di.module

import com.nycschools.ui.SchoolsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindSchoolsActivity(): SchoolsActivity
}