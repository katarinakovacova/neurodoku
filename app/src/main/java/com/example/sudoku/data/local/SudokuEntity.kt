package com.example.sudoku.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sudoku")
data class SudokuEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val initialGrid: String,
    val maskedGrid: String,
    val userGrid: String,
    val difficulty: String,
    val timeSpent: Long,
    val isCompleted: Boolean,
    val completedAt: Long?,
    val createdAt: Long
)