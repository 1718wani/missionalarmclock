package com.example.ikuya.missionalertclock.ui.alert

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.timerset_fragment.*
import java.util.*


class TimerPickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
//
    interface OnSelectedTimeListener {
        fun selectedTime(hour: Int, minute: Int)
    }

    private lateinit var listener: OnSelectedTimeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSelectedTimeListener) {
            listener = context
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time
        val context = context // smart cast
        return when {
            context != null -> {
                TimePickerDialog(
                    context,
                    this, // ここでは TimePickerDialog の リスナーを渡す
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true)
            }
            else -> super.onCreateDialog(savedInstanceState)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener.selectedTime(hourOfDay, minute)
    }

    companion object {
        @Suppress("unused")
        private val TAG = TimerPickerFragment::class.java.simpleName
    }
}