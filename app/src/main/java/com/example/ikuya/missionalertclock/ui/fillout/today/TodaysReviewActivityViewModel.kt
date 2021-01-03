package com.example.ikuya.missionalertclock.ui.fillout.today

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikuya.missionalertclock.data.Sleepdata

class TodaysReviewActivityViewModel :ViewModel(){
    private val _donethinglog = MutableLiveData<Sleepdata>()

    val donethinglog = _donethinglog as LiveData<Sleepdata>

    @UiThread
    fun changeLog(data :Sleepdata){
        _donethinglog.value = data
    }



}