package com.innoprog.eventplanner.di

import android.app.Application
import com.innoprog.eventplanner.presentation.editEvent.EditEventFragment
import com.innoprog.eventplanner.presentation.event.EventFragment
import com.innoprog.eventplanner.presentation.main.MainFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        ViewModelModule::class,
        WeatherModule::class,
        ContextModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: EventFragment)

    fun inject(fragment: MainFragment)

    fun inject(fragment: EditEventFragment)


    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}