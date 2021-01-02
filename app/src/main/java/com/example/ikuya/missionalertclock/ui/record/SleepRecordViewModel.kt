package com.example.ikuya.missionalertclock.ui.record

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.ikuya.missionalertclock.data.LogRepository
import com.example.ikuya.missionalertclock.data.SleepDataBase
import com.example.ikuya.missionalertclock.data.SleepDataBase.SleepDatabase.Companion.getDatabase
import com.example.ikuya.missionalertclock.data.SleepDatabaseDao
import com.example.ikuya.missionalertclock.data.sleepdata
import  com.example.ikuya.missionalertclock.databinding.SleeprecordFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
//import com.example.ikuya.missionalertclock.util.formatNights
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SleepRecordViewModel:ViewModel() {

    // データ操作用のリポジトリクラス
    val repository: LogRepository
    // 全データリスト
    val stepCountList: LiveData<List<sleepdata>>

    // coroutine用
    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val logDao = getDatabase(this as Context).sleepDatabaseDao()
        repository = LogRepository(logDao)
        stepCountList = repository.allLogs
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun addStepCount(stepLog: sleepdata) = scope.launch(Dispatchers.IO){
        repository.insert(stepLog)
    }


}