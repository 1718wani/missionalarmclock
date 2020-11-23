package com.example.ikuya.missionalertclock.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build

class AlarmService (private val context: Context){
    private val alarmManager:AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInMills:Long){

    }

    private fun setAlarm(timeInMills: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMills,
                    pendingIntent
                )
            }else{
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMills,
                    pendingIntent
                )
            }
        }
    }


//    private fun getIntent() =
}
