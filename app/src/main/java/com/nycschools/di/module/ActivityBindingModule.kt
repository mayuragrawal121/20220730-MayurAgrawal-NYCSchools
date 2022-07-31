package com.nycschools.di.module

import com.nycschools.ui.SchoolsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * After doing basic dagger setup so started with injecting dependencies.
 * Created an ActivityBindingModule which is responsible for injecting activities.
 * For we use contributesAndroidInjector to let dagger know that a given activity is a potential client
 * so there is no need for any other injection code in the Activity.
 *
 * Added this module in the ApplicationComponent module list to let dagger know about this module.
 * */
@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindSchoolsActivity(): SchoolsActivity
}