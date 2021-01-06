package com.example.ikuya.missionalertclock.ui.fill.today

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikuya.missionalertclock.data.SleepData

class TodayReviewActivityViewModel :ViewModel(){
    private val _sleeplog = MutableLiveData<SleepData>()

    val sleeplog = _sleeplog as LiveData<SleepData>

    @UiThread
    fun changeLog(data :SleepData){
        _sleeplog.value = data
    }



}