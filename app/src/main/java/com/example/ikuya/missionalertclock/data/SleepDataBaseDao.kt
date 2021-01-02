package com.example.ikuya.missionalertclock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SleepDatabaseDao {
    @Insert
    suspend fun insert(night: sleepdata)

    @Update
    suspend fun update(night: sleepdata)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    suspend  fun get(key: Long): sleepdata?

    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

    @Query("DELETE * FROM daily_sleep_quality_table")
    suspend fun allclear()


    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend  fun getTonight(): sleepdata?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<sleepdata>>
}