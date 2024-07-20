package com.raphaeloliveira.bmicalculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphaeloliveira.bmicalculator.domain.entity.HistoryEntity
import com.raphaeloliveira.bmicalculator.domain.ui.HistoryUiData
import com.raphaeloliveira.bmicalculator.domain.usecase.DeleteHistoryUseCase
import com.raphaeloliveira.bmicalculator.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    fun deleteHistory(history: HistoryUiData) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyEntity = HistoryEntity(
                id = history.id,
                classification = history.classification,
                date = history.date,
                weight = history.weight,
                height = history.height,
                result = history.result
            )
            deleteHistoryUseCase.execute(historyEntity)
        }
    }

    fun getHistoryFromDatabase(callback: (List<HistoryUiData>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyFromDb: List<HistoryEntity> = getHistoryUseCase.execute()
            val historyUiData = historyFromDb.map {
                HistoryUiData(
                    id = it.id,
                    classification = it.classification,
                    date = it.date,
                    weight = it.weight,
                    height = it.height,
                    result = it.result
                )
            }
            withContext(Dispatchers.Main) {
                callback(historyUiData)
            }
        }
    }
}