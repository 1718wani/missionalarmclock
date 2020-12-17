package com.example.ikuya.missionalertclock.ui.alert

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.timerset_fragment.*
import java.util.*


interface OnSelectedTimeListener {
    fun selectedTime(hour: Int, minute: Int)
}

class TimerPickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
//

    var listener: OnSelectedTimeListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        //
        Log.d("-------","$hourOfDay,$minute")
        listener?.selectedTime(hourOfDay,minute)

    }
}