package com.example.ikuya.missionalertclock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SleepDatabaseDao {
    @Insert
    suspend fun insert(night: Sleepdata)

    @Update
    suspend fun update(night: Sleepdata)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    suspend  fun get(key: Long): Sleepdata?

    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend  fun getTonight(): Sleepdata?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<Sleepdata>>
}