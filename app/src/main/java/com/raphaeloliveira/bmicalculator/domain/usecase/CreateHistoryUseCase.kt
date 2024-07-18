package com.raphaeloliveira.bmicalculator.domain.usecase

import com.raphaeloliveira.bmicalculator.data.HistoryDao
import com.raphaeloliveira.bmicalculator.domain.entity.HistoryEntity

class CreateHistoryUseCase(private val historyDao: HistoryDao) {
    suspend fun execute(history: HistoryEntity) {
        historyDao.insert(history)
    }
}