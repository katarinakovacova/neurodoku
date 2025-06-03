package com.example.sudoku.domain.usecase

import com.example.sudoku.data.sudoku.repository.SudokuRepository
import com.example.sudoku.domain.model.SudokuGame

class GetCompletedSudokuGamesUseCase(private val repository: SudokuRepository) {
    suspend operator fun invoke(): List<SudokuGame> {
        return repository.getAllCompletedGames()
    }
}