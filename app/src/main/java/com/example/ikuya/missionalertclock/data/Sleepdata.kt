package com.example.ikuya.missionalertclock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ikuya.missionalertclock.R


enum class FEELING(val drawableRes: Int){
        HAPPY(R.drawable.happyface),
        RELIEVED (R.drawable.releavedface),
        ANGRY(R.drawable.angryface),
        SAD (R.drawable.sadface),
        OWATA (R.drawable.owataface),
        TIRED(R.drawable.tiredface),
}

enum class DEVELOPEDGOAL(val drawableRes: Int){
        GOALYES(R.drawable.check),
        GOALNO(R.drawable.batsudayo)
}

@Entity(tableName = "daily_sleep_quality_table")
data class Sleepdata (
        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,

        @ColumnInfo(name = "set_time_milli")
        val SetTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

        @ColumnInfo(name = "whether_todays_goal_developed")
        var whethertodaysgoaldeveloped : Boolean = false,

        @ColumnInfo(name = "done_thing")
        var donething: String ,

        @ColumnInfo(name = "todays_feeling")
        var todaysfeeling: Int = -1,

        @ColumnInfo(name = "most_important_job")
        var mostimportantjob: String = "",

        @ColumnInfo(name = "benefit_done")
        var benefitdone : String = "",

        @ColumnInfo(name = "expected_feeling")
        var expctedfeeling: Int = -1,

        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1

        )