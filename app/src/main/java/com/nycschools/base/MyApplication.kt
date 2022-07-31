package com.nycschools.base

import com.nycschools.di.component.ApplicationComponent
import com.nycschools.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * As we know that the application component is scoped with the application lifetime so it must be created in the Application class,
 * so created an application class but using dagger-android we extend it from DaggerApplication
 * and implement its method applicationInjector() which is used for creating ApplicationComponent.
 * Note: using DaggerApplication provided by dagger-android support
 * */
class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: ApplicationComponent =
            DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
        return component
    }
}