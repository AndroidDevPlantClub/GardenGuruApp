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
        val wateringText: TextView = itemView.findViewById(R.id.WateringText)
        val sunlightText: TextView = itemView.findViewById(R.id.SunlightText)
        val soiltypeText: TextView = itemView.findViewById(R.id.SoiltypeText)
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
        holder.recordEntry.text = "Species: " + record.entry
        holder.recordTitle.text = record.title.toString()
        holder.wateringText.text = "Watering: " + record.watering.toString() + " ml"
        holder.sunlightText.text = "Sunlight: " + record.sunlight.toString() + " hours"
        holder.soiltypeText.text = "Soil: " + record.soilType.toString()
    }

    override fun getItemCount(): Int {
        return mRecord.size
    }
}