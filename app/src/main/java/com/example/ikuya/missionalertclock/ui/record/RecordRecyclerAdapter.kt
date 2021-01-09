package com.example.ikuya.missionalertclock.ui.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.data.SleepData
import com.example.ikuya.missionalertclock.databinding.ItemSleepLogBinding
import kotlinx.android.synthetic.main.item_sleep_log.view.*

class RecordRecyclerAdapter (private var list: List<SleepData>): RecyclerView.Adapter<RecordRecyclerAdapter.LogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val binding: ItemSleepLogBinding = DataBindingUtil.inflate(
            //ここ怪しいぞParentって書いてある
            LayoutInflater.from(parent.context), R.layout.item_sleep_log, parent, false
        )
        return LogViewHolder(binding)
    }

//    このサンプルアプリでは、表示する行のレイアウトもシンプルですし、データ量もたいしたことないので全更新をかけてしまっていますが、View全体に更新をかけるのは、UIスレッドを止めかねないので、避けるべきだということは覚えておいて下さい。

    fun setList(newList:List<SleepData>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        if (position >= list.size) return
        holder.binding.sleepDataLog= list[position]
    }

    class LogViewHolder(val binding: ItemSleepLogBinding)
        : RecyclerView.ViewHolder(binding.root)

}