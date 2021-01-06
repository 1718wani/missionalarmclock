package com.example.ikuya.missionalertclock.ui.record

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.*
//import com.example.ikuya.missionalertclock.data.LogRepository
import com.example.ikuya.missionalertclock.data.SleepDataBase.SleepDatabase.Companion.getDatabase
import com.example.ikuya.missionalertclock.data.SleepData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
//import com.example.ikuya.missionalertclock.util.formatNights
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SleepRecordViewModel(app:Application):AndroidViewModel(app) {


    val donethingList = MutableLiveData<MutableList<SleepData>>()

    init {
        donethingList.value = mutableListOf()
    }

    @UiThread
    fun adddonething(sleepDataLog: SleepData) {
        val list = donethingList.value ?: return
        list.add(sleepDataLog)
        donethingList.value = list
    }

//    // データ操作用のリポジトリクラス
//    val repository: LogRepository
//    // 全データリスト
//    val stepCountList: LiveData<List<SleepData>>
//
//    // coroutine用
//    private var parentJob = Job()
//
//    private val coroutineContext: CoroutineContext
//        get() = parentJob + Dispatchers.Main
//
//    private val scope = CoroutineScope(coroutineContext)
//
//    init {
//        val logDao = getDatabase(app).sleepDatabaseDao()
//        repository = LogRepository(logDao)
//        stepCountList = repository.allLogs
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        parentJob.cancel()
//    }
//
//    fun addStepCount(stepLog: SleepData) = scope.launch(Dispatchers.IO){
//        repository.insert(stepLog)
//    }


}