package com.example.ikuya.missionalertclock.ui.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.data.Sleepdata
import kotlinx.android.synthetic.main.item_sleep_log.view.*

class RecordRecyclerAdapter (private var list: List<Sleepdata>): RecyclerView.Adapter<RecordRecyclerAdapter.LogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.item_sleep_log, parent, false)
        return LogViewHolder(rowView)
    }

//    このサンプルアプリでは、表示する行のレイアウトもシンプルですし、データ量もたいしたことないので全更新をかけてしまっていますが、View全体に更新をかけるのは、UIスレッドを止めかねないので、避けるべきだということは覚えておいて下さい。

    fun setList(newList:List<Sleepdata>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        if (position < list.size) return
        val sleepdatalogg = list[position]
        holder.donethings.text = sleepdatalogg.donething
        holder.goaldeveloped.setImageResource(sleepdatalogg.goaldeveloped.drawableRes)
        holder.feeling.setImageResource(sleepdatalogg.feeling.drawableRes)

    }

    class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val donethings = itemView.varibledonething!!
        val goaldeveloped = itemView.goaldevelopedView!!
        val feeling = itemView.feelingImageView!!
    }
}