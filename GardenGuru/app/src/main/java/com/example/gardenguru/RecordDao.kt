package com.example.gardenguru

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM record_table")
    fun getAll(): Flow<List<RecordEntity>>

    @Query("SELECT * FROM record_table ORDER BY date Desc")
    fun getAllByDateDesc(): Flow<List<RecordEntity>>

    @Insert
    fun insert(entity: RecordEntity)

    @Insert
    fun insertAll(entryList: List<RecordEntity>)

    @Query("DELETE FROM record_table WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM record_table")
    fun deleteAll()
}
