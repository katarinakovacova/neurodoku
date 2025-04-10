package com.example.sudoku.domain.usecase

class MaskSudokuUseCase {
    fun generateVisibleMask(visibleCount: Int = 20): Array<Array<Boolean>> {
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
