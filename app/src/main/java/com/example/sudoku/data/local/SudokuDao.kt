package com.example.sudoku.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SudokuDao {

    @Query("SELECT * FROM sudoku WHERE isCompleted = 0 ORDER BY createdAt DESC LIMIT 1")
    suspend fun getUnfinishedSudoku(): SudokuEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSudoku(sudoku: SudokuEntity)
}
