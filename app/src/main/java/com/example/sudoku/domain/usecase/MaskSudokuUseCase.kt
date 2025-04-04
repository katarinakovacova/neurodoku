package com.example.sudoku.domain.usecase

class MaskSudokuUseCase {

    fun hideNumbersFromSudokuGrid(completeSudoku: Array<IntArray>): Array<Array<Int?>> {

        val maskedSudoku = Array(9) { row -> completeSudoku[row].map { it as Int? }.toTypedArray() }
        val positions = mutableListOf<Pair<Int,Int>>()

        for (row in 0..8) {
            for (column in 0..8) {
                positions.add(Pair(row, column))
            }
        }

        positions.shuffle()
        val visibleCells = positions.take(20).toSet()

        for ((row, column) in positions) {
            if (Pair(row, column) !in visibleCells) {
                maskedSudoku[row][column] = null
            }
        }
        return maskedSudoku
    }
}
