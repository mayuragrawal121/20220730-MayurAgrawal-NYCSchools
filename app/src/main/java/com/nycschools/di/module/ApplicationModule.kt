package com.nycschools.di.module

import com.nycschools.service.RetrofitService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created ApplicationModule for some app-level dependencies that do not fall into any category.
 *
 * Added this module in the ApplicationComponent module list to let dagger know about this module.
 * */

@Singleton
@Module(includes = [ViewModelModule::class])
object ApplicationModule {
    private const val BASE_URL = "https://data.cityofnewyork.us/resource/"

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
}