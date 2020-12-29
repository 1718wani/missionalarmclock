package com.example.ikuya.missionalertclock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class sleepdata (
        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,

        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

        @ColumnInfo(name = "whether_todays_goal_developed")
        var whethertodaysgoaldeveloped : Boolean = false,

        @ColumnInfo(name = "done_thing")
        var donething: Int = -1,

        @ColumnInfo(name = "todays_feeling")
        var todaysfeeling: Int = -1,

        @ColumnInfo(name = "most_important_job")
        var mostimportantjob: Int = -1,

        @ColumnInfo(name = "benefit_done")
        var benefitdone : Int = -1,

        @ColumnInfo(name = "expected_feeling")
        var expctedfeeling: Int = -1,

        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1

        )