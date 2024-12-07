package com.example.gardenguru

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "record_table")
data class RecordEntity(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "date") val date: Instant?,
    @ColumnInfo(name = "entry") val entry: String?,
    @ColumnInfo(name = "waterings") val watering: Int = 0,
    @ColumnInfo(name = "sunlight") val sunlight: Int = 0,
    @ColumnInfo(name = "soilType") val soilType: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)