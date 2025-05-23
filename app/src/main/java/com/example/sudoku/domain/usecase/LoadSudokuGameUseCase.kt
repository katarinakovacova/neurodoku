package com.example.sudoku.domain.usecase

import com.example.sudoku.data.repository.SudokuRepository
import com.example.sudoku.domain.model.SudokuGame

class LoadSudokuGameUseCase(private val repository: SudokuRepository) {

    suspend operator fun invoke(): SudokuGame? {
        return repository.getUnfinishedSudoku()
    }
}
