package com.raphaeloliveira.bmicalculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raphaeloliveira.bmicalculator.domain.usecase.DeleteHistoryUseCase
import com.raphaeloliveira.bmicalculator.domain.usecase.GetHistoryUseCase

class HistoryViewModelFactory(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(deleteHistoryUseCase, getHistoryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}