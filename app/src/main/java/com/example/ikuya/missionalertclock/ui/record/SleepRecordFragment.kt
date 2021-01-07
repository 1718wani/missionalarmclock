package com.example.ikuya.missionalertclock.ui.record

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.sleeprecord_fragment.*

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
            DataBindingUtil.setContentView(this.requireActivity(), R.layout.sleeprecord_fragment)
        viewModel = ViewModelProvider(this).get(SleepRecordViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
//        これいるかどうかわからない。Rootの意味を調べたい。


        recordlist.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = RecordRecyclerAdapter(viewModel.sleepRecordList.value!!)
        recordlist.adapter = adapter

        val decor = DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        recordlist.addItemDecoration(decor)

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

        when (requestCode) {
            REQUEST_CODE_LOGITEM -> {
                onNewStepCountLog(resultCode, data)
                return
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onNewStepCountLog(resultCode: Int, data: Intent?) {
        when (resultCode) {
            RESULT_OK -> {
                val log = data!!.getSerializableExtra(TodayReviewActivity.EXTRA_KEY_DATA) as SleepData
                viewModel.addSleepRecord(log)
            }
        }
    }

}