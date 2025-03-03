package com.example.sudoku.logic

class Sudoku {

    /**
     * A 9x9 matrix representing Sudoku grid, initialized with 0
     */
    private val grid = Array(9) { IntArray(9) }

    /**
     * Generates a valid Sudoku grid by filling it with numbers from 1 to 9
     *
     * @return A 9x9 matrix representing the completed Sudoku grid
     */
    fun generateSudoku(): Array<IntArray> {
        fillGrid()
        return grid
    }

    /**
     * Recursively fills the Sudoku grid using backtracking
     *
     * @return True if the grid is successfully filled, otherwise false
     */
    private fun fillGrid(): Boolean {
        for (row in 0 until 9) {
            for (column in 0 until 9) {
                if (grid[row][column] == 0) {
                    val numbers = (1..9).shuffled()
                    for (number in numbers) {
                        if (isValidGrid(row, column, number)) {
                            grid[row][column] = number
                            if (fillGrid()) return true
                            grid[row][column] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    /**
     * Checks if placing a given number in a specific cell is valid
     *
     * @param row - The row index
     * @param column - The column index
     * @param number - The number to check
     * @return True if the number can be placed, otherwise false
     */
    private fun isValidGrid(row: Int, column: Int, number: Int): Boolean {
        return !isInRow(row, number) && !isInColumn(column, number) && !isInBox(row, column, number)
    }

    /** Checks if a given number already exists in the specific row
     * @param row - The row index
     * @param number - The number to check
     * @return True if the number exist in the row, otherwise false
     */
    private fun isInRow(row: Int, number: Int): Boolean = grid[row].contains(number)

    /** Checks if a given number already exists in the specific column
     * @param column - The column index
     * @param number - The number to check
     * @return True if the number exist in the column, otherwise false
     */
    private fun isInColumn(column: Int, number: Int): Boolean = grid.any { it[column] == number }

    /** Checks if a given number already exists in the specific 3x3 subgrid
     * @param row - The row index
     * @param column - The column index
     * @param number - The number to check
     * @return True if the number exist in the subgrid, otherwise false
     */
    private fun isInBox(row: Int, column: Int, number: Int): Boolean {
        val startRow = row - row % 3
        val startColumn = column - column % 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (grid[startRow + i][startColumn + j] == number) {
                    return true
                }
            }
        }
        return false
    }
}