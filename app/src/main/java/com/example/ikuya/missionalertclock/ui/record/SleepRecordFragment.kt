package com.example.ikuya.missionalertclock.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.data.Sleepdata
import com.example.ikuya.missionalertclock.databinding.SleeprecordFragmentBinding
import com.google.android.gms.common.api.internal.ActivityLifecycleObserver.of
import kotlinx.android.synthetic.main.sleeprecord_fragment.*
import java.time.format.DecimalStyle.of
import java.util.EnumSet.of
import java.util.List.of
import java.util.Map.of
import java.util.Optional.of
import java.util.OptionalDouble.of
import java.util.OptionalInt.of
import java.util.OptionalLong.of
import java.util.Set.of
import java.util.stream.Collector.of

class SleepRecordFragment : Fragment() {

//    private var viewModel :SleepRecordViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(SleepRecordViewModel::class.java) }
    private lateinit var binding :SleeprecordFragmentBinding
    private lateinit var adapter : RecordRecyclerAdapter
    private lateinit var viewModel : SleepRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.sleeprecord_fragment)
        viewModel = ViewModelProvider(this).get(SleepRecordViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        return binding.root
        recordlist.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = RecordRecyclerAdapter(viewModel.donethingList.value as List<Sleepdata>)
        recordlist.adapter = adapter

        val decor = DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        recordlist.addItemDecoration(decor)

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

    }

}