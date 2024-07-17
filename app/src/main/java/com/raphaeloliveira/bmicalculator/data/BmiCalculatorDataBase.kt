package com.raphaeloliveira.bmicalculator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raphaeloliveira.bmicalculator.domain.entity.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class BmiCalculatorDataBase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}

