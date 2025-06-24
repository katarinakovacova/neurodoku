package com.example.sudoku.data.sudoku.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SudokuEntity::class],
    version = 4,
    exportSchema = false
)
abstract class SudokuDatabase : RoomDatabase() {
    abstract fun sudokuDao(): SudokuDao
}