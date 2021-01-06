package com.example.ikuya.missionalertclock.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.data.DEVELOPEDGOAL
import com.example.ikuya.missionalertclock.data.FEELING
import com.example.ikuya.missionalertclock.data.SleepData
import com.example.ikuya.missionalertclock.ui.record.RecordRecyclerAdapter


@BindingAdapter("android:src")
fun setImageLevel(view: ImageView, level: FEELING) {
    val res =
        when (level) {
            FEELING.HAPPY -> R.drawable.happyface
            FEELING.RELIEVED -> R.drawable.releavedface
            FEELING.ANGRY -> R.drawable.angryface
            FEELING.SAD -> R.drawable.sadface
            FEELING.OWATA -> R.drawable.owataface
            else -> R.drawable.tiredface
        }
    view.setImageResource(res)
}

@BindingAdapter("android:src")
fun setImageWeather(view: ImageView, level: DEVELOPEDGOAL) {
    val res =
        when (level) {
            DEVELOPEDGOAL.GOALYES -> R.drawable.check
            else -> R.drawable.batsudayo
        }
    view.setImageResource(res)
}

@BindingAdapter("app:items")
fun setLogItems(view: RecyclerView, logs: List<SleepData>?) {
    val adapter = view.adapter as RecordRecyclerAdapter? ?: return

    logs?.let {
        adapter.setList(logs)
    }
}

