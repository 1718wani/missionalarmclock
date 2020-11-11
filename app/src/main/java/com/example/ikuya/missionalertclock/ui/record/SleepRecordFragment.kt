package com.example.ikuya.missionalertclock.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.ikuya.missionalertclock.R

class SleepRecordFragment : Fragment() {
    companion object {
        fun newInstance() = SleepRecordFragment()
    }

//    private var viewModel :SleepRecordViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(SleepRecordViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sleeprecord_fragment, container, false)
    }

//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        viewModel = ViewModelProviders.of(this).get(SleepRecordViewModel::class.java)

}