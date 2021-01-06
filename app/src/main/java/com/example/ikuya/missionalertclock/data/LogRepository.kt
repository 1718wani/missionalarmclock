package com.example.ikuya.missionalertclock.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
//
//class LogRepository(private val sleepdatabasedao:SleepDatabaseDao ) {
//
//    val allLogs: LiveData<List<SleepData>> = sleepdatabasedao.getAllNights()
//
//    @WorkerThread
//    suspend fun insert(sleeplog: SleepData) {
//        sleepdatabasedao.insert(sleeplog)
//    }
//
//    @WorkerThread
//    suspend fun update(sleeplog: SleepData){
//        sleepdatabasedao.update(sleeplog)
//    }
//
//    @WorkerThread
//    suspend fun deleteAll() {
//        sleepdatabasedao.clear()
//    }
//}