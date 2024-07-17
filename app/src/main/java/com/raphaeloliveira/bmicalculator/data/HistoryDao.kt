package com.raphaeloliveira.bmicalculator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphaeloliveira.bmicalculator.domain.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM historyentity")
    fun getAll(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(taskEntity: List<HistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: HistoryEntity)

    @Delete
    fun delete(taskEntity: HistoryEntity)

}