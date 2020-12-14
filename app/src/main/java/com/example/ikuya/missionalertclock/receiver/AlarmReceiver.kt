package com.example.ikuya.missionalertclock.receiver

import android.app.Activity
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.alarm.RingingAlarm
import com.example.ikuya.missionalertclock.service.AlarmService
import com.example.ikuya.missionalertclock.util.Constants
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        //アラームタイムを受け取る
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)

        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                buildNotification(context, "Set Exact Time", convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_EXACT -> {
                setRepetitiveAlarm(AlarmService(context))
                buildNotification(context, "Set Repetitive Exact Time", convertDate(timeInMillis))
            }
        }
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, RingingAlarm::class.java).apply {
            var flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this as Activity, 0, intent as Intent?, 0)

        val snoozeIntent = Intent(this, AlarmReceiver::class.java).apply {
            var snooze = Constants.ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)AlarmReceiver
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this as Context, 0, snoozeIntent, 0)
        val builder = NotificationCompat.Builder(this, "mychannle")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Mission Alart Clock")
            .setContentText("アラームが鳴りました")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                snoozePendingIntent)
    }

    private fun Intent(alarmReceiver: AlarmReceiver, java: Class<RingingAlarm>): Any {
        TODO("Not yet implemented")
    }


    private fun setRepetitiveAlarm(alarmService: AlarmService) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
            Log.d("Time","Set alarm for next week same time - ${convertDate(this.timeInMillis)}")
        }
        alarmService.setExactAlarm(cal.timeInMillis)
    }

    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()
}