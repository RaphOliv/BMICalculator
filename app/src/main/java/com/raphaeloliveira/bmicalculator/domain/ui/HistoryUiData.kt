package com.raphaeloliveira.bmicalculator.domain.ui

data class HistoryUiData(
    val id: Long,
    val classification: String,
    val weight: String,
    val height: String,
    val date: String,
    val result: String
)
