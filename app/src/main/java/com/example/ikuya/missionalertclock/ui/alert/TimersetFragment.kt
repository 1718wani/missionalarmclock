package com.example.ikuya.missionalertclock.ui.alert

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.alarm.DuringAlarmSetClock
import java.util.*


class TimersetFragment: Fragment() {

//    private lateinit var binding: TimersetFragmentBinding
//    private lateinit var viewModel: TimersetFragmentViewModel


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

    fun showTimePickerDialog(v: View) {
        val newFragment = TimePickerFragment()
        newFragment.show(supportFragmentManager, "timePicker")

    }

}