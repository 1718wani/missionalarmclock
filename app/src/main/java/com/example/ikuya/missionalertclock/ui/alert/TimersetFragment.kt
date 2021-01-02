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
import com.example.ikuya.missionalertclock.service.AlarmService
import com.example.ikuya.missionalertclock.ui.fillout.today.TodaysReviewActivity
import kotlinx.android.synthetic.main.timerset_fragment.*
import java.text.ParseException
import java.text.SimpleDateFormat
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
        var alarmservice = AlarmService(this.requireContext())
        val appButton: Button = view.findViewById(R.id.timerstartbtn)
        appButton.setOnClickListener {
            val intent = Intent(activity, TodaysReviewActivity::class.java)
            startActivity(intent)
            val timemill = getMilliFromDate(clickListener.toString())
            alarmservice.setExactAlarm(timemill)

        }
        val nxtbtn : Button = view.findViewById(R.id.timechangebtn) as Button
        nxtbtn.setOnClickListener(object:View.OnClickListener {
            override fun onClick(v: View?) {
                showTimePickerDialog(nxtbtn)
            }
        })
    }

    private val clickListener : OnSelectedTimeListener =
        object :OnSelectedTimeListener{
            override fun selectedTime(hour: Int, minute: Int) {
                val str = String.format(Locale.US, "%d:%d", hour, minute)
                // use the plug in of Kotlin Android Extensions
                timersetfragmentcurrenttime.text = str
            }

        }

    fun showTimePickerDialog(v: View) {
        val newFragment = TimerPickerFragment()
        newFragment.listener = clickListener
        newFragment.show(childFragmentManager,"time_picker")
    }




    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

    }

    fun getMilliFromDate(dateFormat: String?): Long {
        var date = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        try {
            date = formatter.parse(dateFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        println("Today is $date")
        return date.getTime()
    }
}