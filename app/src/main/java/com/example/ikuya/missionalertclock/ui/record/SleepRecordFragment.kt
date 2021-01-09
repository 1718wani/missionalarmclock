package com.example.ikuya.missionalertclock.ui.record

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.data.SleepData
import com.example.ikuya.missionalertclock.databinding.SleeprecordFragmentBinding
import com.example.ikuya.missionalertclock.ui.MainActivity.Companion.REQUEST_CODE_LOGITEM
import com.example.ikuya.missionalertclock.ui.fill.today.TodayReviewActivity

class SleepRecordFragment : Fragment() {



//    private var viewModel :SleepRecordViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(SleepRecordViewModel::class.java) }
    private lateinit var binding :SleeprecordFragmentBinding
    private lateinit var adapter : RecordRecyclerAdapter
    private lateinit var viewModel : SleepRecordViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(this.layoutInflater, R.layout.sleeprecord_fragment,container,false)
        viewModel = ViewModelProvider(this).get(SleepRecordViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


        binding.recordlist.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = RecordRecyclerAdapter(viewModel.sleepRecordList.value!!)
        viewModel.sleepRecordList.observe(this.viewLifecycleOwner, { list ->
            adapter.setList(list)
        })
        Log.d("TAG" , "$binding.recordlist")
        binding.recordlist.adapter = adapter

        //val record = binding.recordlist
        //        record.layoutManager = LinearLayoutManager(this.requireContext())
        //        adapter = RecordRecyclerAdapter(viewModel.sleepRecordList.value!!)
        //        record.adapter = adapter

        val decor = DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        binding.recordlist.addItemDecoration(decor)
//        これいるかどうかわからない。Rootの意味を調べたい。
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel : SleepRecordViewModel by lazy {
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(SleepRecordViewModel::class.java)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_LOGITEM -> {
                onNewSleepCountLog(resultCode, data)
                return
            }
        }


    }

    private fun onNewSleepCountLog(resultCode: Int, data: Intent?) {
        when (resultCode) {
            RESULT_OK -> {
                val log = data!!.getSerializableExtra(TodayReviewActivity.EXTRA_KEY_DATA) as SleepData
                viewModel.addSleepRecord(log)
            }
        }
    }

}