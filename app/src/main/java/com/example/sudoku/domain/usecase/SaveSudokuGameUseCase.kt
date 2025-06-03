package com.example.sudoku.domain.usecase

import com.example.sudoku.data.sudoku.repository.SudokuRepository
import com.example.sudoku.domain.model.SudokuGame

class SaveSudokuGameUseCase(private val repository: SudokuRepository) {

    suspend operator fun invoke(game: SudokuGame) {
        repository.insertSudoku(game)
    }
}
