package com.example.sudoku.domain.model

data class SudokuGame(
    val completeGrid: Array<IntArray>,
    val visibleMask: Array<Array<Boolean>>,
    val userGrid: Array<Array<Int?>>,
    val difficulty: SudokuDifficulty,
    val timeSpent: Long,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val completedAt: Long? = null
)
