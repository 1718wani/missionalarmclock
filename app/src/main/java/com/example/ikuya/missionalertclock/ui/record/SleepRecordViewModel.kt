package com.example.ikuya.missionalertclock.ui.record

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.*
//import com.example.ikuya.missionalertclock.data.LogRepository
import com.example.ikuya.missionalertclock.data.SleepData
//import com.example.ikuya.missionalertclock.util.formatNights

class SleepRecordViewModel(app:Application):AndroidViewModel(app) {


    val sleepRecordList = MutableLiveData<MutableList<SleepData>>()

    init {
        sleepRecordList.value = mutableListOf()
    }

    @UiThread
    fun addSleepRecord(sleepDataLog: SleepData) {
        val list = sleepRecordList.value ?: return
        //ここはリターンするだけにしなくてもよい
        list.add(sleepDataLog)
        sleepRecordList.value = list
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