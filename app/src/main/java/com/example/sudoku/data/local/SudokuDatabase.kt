package com.example.sudoku.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SudokuEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SudokuDatabase : RoomDatabase() {
    abstract fun sudokuDao(): SudokuDao
}