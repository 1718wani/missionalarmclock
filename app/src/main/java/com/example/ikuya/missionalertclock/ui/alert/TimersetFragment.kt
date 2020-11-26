package com.example.ikuya.missionalertclock.ui.alert

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.alarm.DuringAlarmSetClock
import kotlinx.android.synthetic.main.timerset_fragment.*
import java.util.*


class TimersetFragment: Fragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timerset_fragment, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appButton: Button = view.findViewById(R.id.timerstartbtn)
        appButton.setOnClickListener {
            val intent = Intent(activity, DuringAlarmSetClock::class.java)
            startActivity(intent)
        }
    }



    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val str = String.format(Locale.US, "%d:%d", hourOfDay, minute)
        // use the plug in of Kotlin Android Extensions
        timersetfragmentcurrenttime.text = str
    }

    fun showTimePickerDialog(v: View) {
        val newFragment = TimePickerFragment()
        newFragment.show(childFragmentManager, "timePicker")
    }

}