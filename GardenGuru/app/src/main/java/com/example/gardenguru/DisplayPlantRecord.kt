package com.example.gardenguru
import java.time.Instant

data class DisplayPlantRecord(
    val id: Long?,
    val title: String?,
    val date: Instant?,
    val entry: String?
)