package com.example.ikuya.missionalertclock.ui.alert

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.alarm.DuringAlarmSetClock
import kotlinx.android.synthetic.main.timerset_fragment.*
import java.util.*



class TimersetFragment: Fragment(), TimerPickerFragment.OnSelectedTimeListener {

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
        val nxtbtn : Button = view.findViewById(R.id.timechangebtn) as Button
        nxtbtn.setOnClickListener(object:View.OnClickListener {
            override fun onClick(v: View?) {
                showTimePickerDialog(nxtbtn)
            }
        })
    }

    fun showTimePickerDialog(v: View) {
        val newFragment = TimerPickerFragment()
        newFragment.show(childFragmentManager, "timePicker")
    }


    override fun selectedTime(hour: Int, minute: Int) {
        timersetfragmentcurrenttime.text = "${hour}時${minute}分"
    }


}