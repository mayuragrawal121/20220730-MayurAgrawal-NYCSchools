package com.nycschools.di.component

import android.app.Application
import com.nycschools.base.MyApplication
import com.nycschools.di.module.ActivityBindingModule
import com.nycschools.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created the ApplicationComponent interface and it extends AndroidInjector with the base class type
 * so that we can avoid writing a bunch of code to inject the application
 * and we can directly say that this base class is the client for DI.
 *
 * To tell dagger that this is the component, added @component annotation at the top.
 * Also included the given module to use those special android dagger classes like AndroidInjector.
 * Then finally when we build the application, Dagger will generate a DaggerApplicationComponent file for us
 * which we can see in the generated folder.
 * */
@Singleton
@Component(modules = [ApplicationModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {
    fun inject(application: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}