package com.innoprog.eventplanner.presentation

import android.app.Application
import com.innoprog.eventplanner.di.DaggerApplicationComponent


class EventApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}