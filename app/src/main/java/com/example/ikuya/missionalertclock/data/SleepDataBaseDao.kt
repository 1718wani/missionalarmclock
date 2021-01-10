package com.example.ikuya.missionalertclock.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SleepDatabaseDao {
    @Insert
    suspend fun insert(night: SleepData)

    @Update
    suspend fun update(night: SleepData)

    @Delete
    fun delete(night: SleepData)

    @Query("SELECT * from daily_sleep_quality_table WHERE whether_todays_goal_developed = :key")
    suspend  fun get(key: Long): SleepData?

    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY whether_todays_goal_developed DESC LIMIT 1")
    suspend  fun getTonight(): SleepData?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY whether_todays_goal_developed DESC")
    fun getAllNights(): LiveData<List<SleepData>>
}

const val DATABASE_NAME = "sleeplog_database"

@Database(entities = [SleepData::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    abstract fun sleepDatabaseDao(): SleepDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getDatabase(context: Context): SleepDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}