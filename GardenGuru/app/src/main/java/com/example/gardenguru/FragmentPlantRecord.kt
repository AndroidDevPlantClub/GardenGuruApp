package com.example.gardenguru
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FragmentPlantRecord: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plant_record, container, false)
        val addRecord: ImageButton = view.findViewById(R.id.addButton)
        val recordRV: RecyclerView = view.findViewById(R.id.recordRV)
        val recordList: MutableList<DisplayPlantRecord> = listOf<DisplayPlantRecord>().toMutableList()
        val recordAdapter = PlantRecordAdapter(recordList)

        recordRV.adapter = recordAdapter
        recordRV.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            (activity?.application as GuruApplication).db.recordDao().getAllByDateDesc().collect{ databaseList ->
                databaseList.map { entity ->
                    DisplayPlantRecord(
                        entity.id,
                        entity.title,
                        entity.date,
                        entity.entry
                    )
                }.also { mappedList ->
                    recordList.clear()
                    recordList.addAll(mappedList)
                    recordAdapter.notifyDataSetChanged()
                }
            }
        }

        addRecord.setOnClickListener {
            val context = it.context
            val intent = Intent(context, AddPlantRecord::class.java)
            context.startActivity(intent)
        }
        return view
    }

    companion object {
        fun newInstance(): FragmentPlantRecord {
            return FragmentPlantRecord()
        }
    }

}