package com.example.ikuya.missionalertclock.data

import android.provider.SyncStateContract.Helpers.insert
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class LogRepository(private val sleepdatabasedao:SleepDatabaseDao ) {

    val allLogs: LiveData<List<sleepdata>> = sleepdatabasedao.getAllNights()

    @WorkerThread
    suspend fun insert(sleeplog: sleepdata) {
        sleepdatabasedao.insert(sleeplog)
    }

    @WorkerThread
    suspend fun update(sleeplog: sleepdata){
        sleepdatabasedao.update(sleeplog)
    }

    @WorkerThread
    suspend fun deleteAll() {
        sleepdatabasedao.clear()
    }
}