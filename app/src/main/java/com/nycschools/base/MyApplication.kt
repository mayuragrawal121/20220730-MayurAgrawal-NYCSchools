package com.nycschools.base

import com.nycschools.di.component.ApplicationComponent
import com.nycschools.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: ApplicationComponent =
            DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
        return component
    }
}