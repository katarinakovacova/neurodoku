package com.example.sudoku.domain.usecase

class GenerateSudokuUseCase {

    fun generateSudokuGrid(): Array<IntArray> {
        val sudokuGrid = Array(9) { IntArray(9) }
        fillSudokuGrid(sudokuGrid)
        return sudokuGrid
    }

    private fun fillSudokuGrid(sudokuGrid: Array<IntArray>): Boolean {
        for (row in 0..8) {
            for (column in 0..8) {
                if (sudokuGrid[row][column] == 0) {
                    val numbers = (1..9).shuffled()
                    for (number in numbers) {
                        if (isSudokuGridValid(sudokuGrid, row, column, number)) {
                            sudokuGrid[row][column] = number
                            if (fillSudokuGrid(sudokuGrid)) return true
                            sudokuGrid[row][column] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun isSudokuGridValid(sudokuGrid: Array<IntArray>, row: Int, column: Int, number: Int): Boolean {
        return !isNumberInRow(sudokuGrid, row, number) &&
                !isNumberInColumn(sudokuGrid, column, number) &&
                !isNumberInBox(sudokuGrid, row, column, number)
    }

    private fun isNumberInRow(sudokuGrid: Array<IntArray>, row: Int, number: Int): Boolean {
        return sudokuGrid[row].contains(number)
    }

    private fun isNumberInColumn(sudokuGrid: Array<IntArray>, column: Int, number: Int): Boolean {
        return sudokuGrid.any { it [column] == number }
    }

    private fun isNumberInBox(sudokuGrid: Array<IntArray>, row: Int, column: Int, number: Int): Boolean {
        val startRow = row - row % 3
        val startColumn = column - column % 3

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (sudokuGrid[startRow + i][startColumn + j] == number) {
                    return true
                }
            }
        }
        return false
    }
}