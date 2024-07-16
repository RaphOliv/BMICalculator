package com.raphaeloliveira.bmicalculator.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val classification: String,
    val weight: String,
    val height: String,
    val date: String,
    val result: String
)
