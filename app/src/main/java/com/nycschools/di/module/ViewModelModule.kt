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