package com.example.ikuya.missionalertclock.ui.record

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ikuya.missionalertclock.data.SleepDatabaseDao

//class SleepRecordViewModelFactory(
//    private val dataSource: SleepDatabaseDao,
//    private val application: Application
//) : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SleepRecordViewModel::class.java)) {
//            return SleepRecordViewModel(dataSource, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}