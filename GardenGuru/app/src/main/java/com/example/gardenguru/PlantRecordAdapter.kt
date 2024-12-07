package com.example.gardenguru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantRecordAdapter(private val mRecord: List<DisplayPlantRecord>) : RecyclerView.Adapter<PlantRecordAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recordTitle: TextView = itemView.findViewById(R.id.RecordTitleText)
        val recordDate: TextView = itemView.findViewById(R.id.RecordDateText)
        val recordEntry: TextView = itemView.findViewById(R.id.RecordText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.plant_record_detail, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = mRecord[position]
        holder.recordDate.text = record.date.toString().substring(0, record.date.toString().indexOf("T"))
        holder.recordEntry.text = record.entry
        holder.recordTitle.text = record.title.toString()
    }

    override fun getItemCount(): Int {
        return mRecord.size
    }
}