package com.nycschools.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nycschools.di.vms.ViewModelFactory
import com.nycschools.di.vms.ViewModelKey
import com.nycschools.vm.SchoolViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Injecting ViewModel directly is difficult because their instances are created by ViewModelProvider.
 * Using the concept of Multibinding to inject dependencies in ViewModels.
 *
 * After creating the ViewModelKey added a bind method for a specific ViewModel in the following manner:
 *
 * Note: the return type of the bind method is ViewModel and not SchoolViewModel.
 * It’s intentional. @IntoMap annotation says that Provider object for this service will be inserted into Map,
 * and @ViewModelKey annotation specifies under which key it will reside.
 * */

@Singleton
@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SchoolViewModel::class)
    abstract fun bindListViewModel(schoolViewModel: SchoolViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}