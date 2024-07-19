package com.raphaeloliveira.bmicalculator.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphaeloliveira.bmicalculator.R
import com.raphaeloliveira.bmicalculator.domain.entity.HistoryEntity
import com.raphaeloliveira.bmicalculator.domain.usecase.CreateHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultViewModel(private val createHistoryUseCase: CreateHistoryUseCase, private val resources: Resources) : ViewModel() {

    private val _classification = MutableLiveData<String>()
    val classification: LiveData<String> = _classification

    private val _classificationColor = MutableLiveData<Int>()
    val classificationColor: LiveData<Int> = _classificationColor

    fun classifyBmi(bmi: Float) {
        val classificationText = when {
            bmi < 18.5f -> resources.getString(R.string.underweight)
            bmi in 18.5f..24.9f -> resources.getString(R.string.healthyweight)
            bmi in 25f..29.9f -> resources.getString(R.string.overweight)
            bmi in 30f..39.9f -> resources.getString(R.string.obesity)
            else -> resources.getString(R.string.severe_obesity)
        }
        _classification.value = classificationText

        val classificationColor = when {
            bmi < 18.5f -> R.color.colorUnderWeight
            bmi in 18.5f..24.9f -> R.color.colorNormal
            bmi in 25f..29.9f -> R.color.colorOverWeight
            bmi in 30f..39.9f -> R.color.colorObesity
            else -> R.color.colorSevereObesity
        }
        _classificationColor.value = classificationColor
    }

    fun insertBmiDataInDatabase(bmiResult: Float, classification: String, weight: String, height: String) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateCurrent = dateFormat.format(Date())

        val historyEntity = HistoryEntity(
            classification = classification,
            weight = weight,
            height = height,
            date = dateCurrent,
            result = bmiResult.toString()
        )
        viewModelScope.launch(Dispatchers.IO) {
            createHistoryUseCase.execute(historyEntity)
        }
    }
}