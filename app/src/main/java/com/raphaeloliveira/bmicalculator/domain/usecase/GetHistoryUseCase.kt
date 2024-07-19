package com.raphaeloliveira.bmicalculator.domain.usecase

import com.raphaeloliveira.bmicalculator.data.HistoryDao
import com.raphaeloliveira.bmicalculator.domain.entity.HistoryEntity

class GetHistoryUseCase(private val historyDao: HistoryDao) {
    suspend fun execute(): List<HistoryEntity> {
        return historyDao.getAll()
    }
}