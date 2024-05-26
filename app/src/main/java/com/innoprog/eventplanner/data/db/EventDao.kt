package com.innoprog.eventplanner.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {

    @Query("SELECT * FROM event_items")
    fun getEventList(): LiveData<List<EventItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEventItem(eventItemDbModel: EventItemDbModel)

    @Query("DELETE FROM event_items WHERE id=:eventItemId")
    suspend fun deleteEventItem(eventItemId: Int)

    @Query("SELECT * FROM event_items WHERE id=:eventItemId LIMIT 1")
    suspend fun getEventItem(eventItemId: Int): EventItemDbModel
}