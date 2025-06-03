package com.example.sudoku.data.sudoku.repository

import com.example.sudoku.domain.model.SudokuGame

interface SudokuRepository {
    suspend fun getUnfinishedSudoku(): SudokuGame?

    suspend fun insertSudoku(sudoku: SudokuGame)

    suspend fun getAllCompletedGames(): List<SudokuGame>
}