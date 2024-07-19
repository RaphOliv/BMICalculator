package com.raphaeloliveira.bmicalculator.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raphaeloliveira.bmicalculator.domain.usecase.CreateHistoryUseCase

class ResultViewModelFactory(private val createHistoryUseCase: CreateHistoryUseCase, private val resources: Resources) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(createHistoryUseCase, resources) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}