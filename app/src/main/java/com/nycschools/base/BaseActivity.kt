package com.nycschools.base

import dagger.android.support.DaggerAppCompatActivity

/**
 * Created this BaseActivity to avoid duplicate code in every Activity.
 * We need to call AndroidInjection.inject() in every activity that we wanted to use dagger,
 * to avoid that using DaggerAppCompatActivity to reduce boilerplate in Activity.
 * */
abstract class BaseActivity : DaggerAppCompatActivity()