package com.innoprog.eventplanner.di


import android.app.Application
import com.innoprog.eventplanner.data.db.AppDataBase
import com.innoprog.eventplanner.data.db.EventDao
import com.innoprog.eventplanner.data.impl.EventListRepositoryImpl
import com.innoprog.eventplanner.domain.repository.EventListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindEventListRepository(impl: EventListRepositoryImpl): EventListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideEventListDao(
            application: Application
        ): EventDao {
            return AppDataBase.getInstance(application).eventDao()
        }
    }
}