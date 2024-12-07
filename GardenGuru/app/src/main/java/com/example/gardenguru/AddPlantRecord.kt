package com.example.gardenguru

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.Instant

class AddPlantRecord : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_record_activity)

        fun closeKeyboard() {
            val view = this.currentFocus
            if(view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

        var plantTitleEmpty: Boolean
        var plantRecordEmpty: Boolean
        var plantWateringEmpty: Boolean

        val plantTitleEntry: TextView = findViewById(R.id.PlantTitleEntry)
        val plantEntry: TextView = findViewById(R.id.WriteRecord)
        val plantWateringEntry: TextView = findViewById(R.id.PlantWateringEntry)
        val plantSunlightEntry: TextView = findViewById(R.id.PlantSunLightEntry)
        val plantSoilTypeEntry: TextView = findViewById(R.id.PlantSoilTypeEntry)
        val saveButton: Button = findViewById(R.id.saveButton)

        class InputTextWatcher: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                plantTitleEmpty = plantTitleEntry.text.toString().isEmpty()
                plantRecordEmpty = plantEntry.text.toString().isEmpty()

                saveButton.isEnabled = !plantTitleEmpty && !plantRecordEmpty
            }

        }

        plantTitleEntry.addTextChangedListener(InputTextWatcher())
        plantEntry.addTextChangedListener(InputTextWatcher())

        saveButton.setOnClickListener {
            lifecycleScope.launch(IO) {
                (applicationContext as? GuruApplication)?.db?.recordDao()?.insert(
                    RecordEntity(
                        plantTitleEntry.text.toString(),
                        Instant.now(),
                        plantEntry.text.toString(),
                        plantWateringEntry.text.toString().toInt(),
                        plantSunlightEntry.text.toString().toInt(),
                        plantSoilTypeEntry.text.toString()
                    )
                )
            }
            closeKeyboard()
            ///wait for insert to complete
            Thread.sleep(500)
            plantTitleEntry.text = ""
            plantEntry.text = ""
            plantWateringEntry.text = ""
            plantSunlightEntry.text = ""
            plantSoilTypeEntry.text = ""
            this.finish()
        }
    }
}