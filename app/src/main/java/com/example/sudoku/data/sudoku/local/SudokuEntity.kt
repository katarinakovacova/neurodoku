package com.example.sudoku.data.sudoku.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sudoku")
data class SudokuEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val initialGrid: String,
    val maskedGrid: String,
    val userGrid: String,
    val notesJson: String? = null,
    val difficulty: String,
    val timeSpent: Long,
    val isCompleted: Boolean,
    val completedAt: Long?,
    val createdAt: Long
)
