package com.example.sudoku.domain.usecase

import com.example.sudoku.domain.model.SudokuDifficulty

class MaskSudokuUseCase {
    fun generateVisibleMask(sudokuDifficulty: SudokuDifficulty): Array<Array<Boolean>> {
        val visibleCount = sudokuDifficulty.visibleCount
        val maskForSudoku = Array(9) { Array(9) { false } }
        val positions = mutableListOf<Pair<Int, Int>>()

        for (row in 0..8) {
            for (column in 0..8) {
                positions.add(Pair(row, column))
            }
        }

        positions.shuffle()

        val visibleCells = positions.take(visibleCount)

        for ((row, column) in visibleCells) {
            maskForSudoku[row][column] = true
        }

        return maskForSudoku
    }
}
