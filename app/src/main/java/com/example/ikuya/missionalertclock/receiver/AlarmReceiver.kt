package com.example.ikuya.missionalertclock.receiver

import android.app.Activity
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.text.format.DateFormat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
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
                builder(context, "Set Exact Time", convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_EXACT -> {
                setRepetitiveAlarm(AlarmService(context))
                buildNotification(context, "Set Repetitive Exact Time", convertDate(timeInMillis))
            }
        }


        val CHANNEL_ID = "channel_id"
        val channel_name = "channel_name"
        val channel_description = "channel_description "

        fun createNotificationChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val descriptionText = channel_description
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, channel_name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        var builder = NotificationCompat.Builder(this as Context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        fun buildNotification(context: Context, title: String, message: String) {
            // Create an explicit intent for an Activity in your app
            val intent = Intent(this, RingingAlarm::class.java).apply {
                var flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, intent as Intent?, 0)

            val snoozeIntent = Intent(context, AlarmReceiver::class.java).apply {
                var snooze = Constants.ACTION_SNOOZE
                putExtra(EXTRA_NOTIFICATION_ID, 0)
            }
            val snoozePendingIntent: PendingIntent =
                PendingIntent.getBroadcast(this as Context, 0, snoozeIntent, 0)
            val builder = NotificationCompat.Builder(this, channel_name)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Mission Alart Clock")
                .setContentText("アラームが鳴りました")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(
                    R.drawable.ic_snooze, getString(R.string.snooze),
                    snoozePendingIntent
                )
        }


        fun setRepetitiveAlarm(alarmService: AlarmService) {
            val cal = Calendar.getInstance().apply {
                this.timeInMillis = timeInMillis + TimeUnit.MINUTES.toMillis(30)

                Log.d(
                    "Time",
                    "Set alarm for next 30minutes same time - ${convertDate(this.timeInMillis)}"
                )
            }
            alarmService.setExactAlarm(cal.timeInMillis)
        }
    }

    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()
}