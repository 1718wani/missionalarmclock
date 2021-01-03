package com.example.ikuya.missionalertclock.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class LogRepository(private val sleepdatabasedao:SleepDatabaseDao ) {

    val allLogs: LiveData<List<Sleepdata>> = sleepdatabasedao.getAllNights()

    @WorkerThread
    suspend fun insert(sleeplog: Sleepdata) {
        sleepdatabasedao.insert(sleeplog)
    }

    @WorkerThread
    suspend fun update(sleeplog: Sleepdata){
        sleepdatabasedao.update(sleeplog)
    }

    @WorkerThread
    suspend fun deleteAll() {
        sleepdatabasedao.clear()
    }
}