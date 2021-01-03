package com.example.ikuya.missionalertclock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class SleepDataBase {

    @Database(entities = [Sleepdata::class], version = 1, exportSchema = false)
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
}