package com.nycschools.di.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Dagger will create an implicit Map filled with Provider<ViewModel> objects and put it onto the objects graph.
 * Use of that Map by creating a ViewModelFactory by extending ViewModelProvider.
 * Factory and passing the map into it in the following manner:
 * */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T
}