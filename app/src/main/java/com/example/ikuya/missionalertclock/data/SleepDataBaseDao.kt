package com.example.ikuya.missionalertclock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


//@Dao
//interface SleepDatabaseDao {
//    @Insert
//    suspend fun insert(night: SleepData)
//
//    @Update
//    suspend fun update(night: SleepData)
//
//    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
//    suspend  fun get(key: Long): SleepData?
//
//    @Query("DELETE FROM daily_sleep_quality_table")
//    suspend fun clear()
//
//    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
//    suspend  fun getTonight(): SleepData?
//
//    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
//    fun getAllNights(): LiveData<List<SleepData>>
//}