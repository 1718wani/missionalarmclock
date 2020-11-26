package com.example.ikuya.missionalertclock.alarm

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ikuya.missionalertclock.R
import kotlinx.android.synthetic.main.during_alarm_set_clock.*
import java.util.*
import kotlin.concurrent.timer

class DuringAlarmSetClock: AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.during_alarm_set_clock)

        val handler = Handler()
        timer(name = "testTimer", period = 1000) {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)
            handler.post {
                timerdisplay.text = "${hour}時 ${minute}分 ${second}秒"
            }
        }
    }

//    private fun Moveto
}