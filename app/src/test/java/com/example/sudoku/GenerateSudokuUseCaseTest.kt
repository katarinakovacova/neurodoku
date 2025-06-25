package com.example.sudoku

import com.example.sudoku.domain.usecase.GenerateSudokuUseCase
import org.junit.Assert.*
import org.junit.Test

class GenerateSudokuUseCaseTest {

    private val generator = GenerateSudokuUseCase()

    @Test
    fun generatedGridHasCorrectSize() {
        val grid = generator.generateSudokuGrid()
        assertEquals(9, grid.size)
        grid.forEach { row -> assertEquals(9, row.size) }
    }

    @Test
    fun generatedGridContainsOnlyNumbers1to9() {
        val grid = generator.generateSudokuGrid()
        for (row in grid) {
            for (cell in row) {
                assertTrue("Cell value $cell is out of range", cell in 1..9)
            }
        }
    }

    @Test
    fun eachRowContainsUniqueNumbers() {
        val grid = generator.generateSudokuGrid()
        for (row in grid) {
            assertEquals("Row has duplicates", 9, row.toSet().size)
        }
    }

    @Test
    fun eachColumnContainsUniqueNumbers() {
        val grid = generator.generateSudokuGrid()
        for (col in 0 until 9) {
            val column = (0 until 9).map { row -> grid[row][col] }.toSet()
            assertEquals("Column $col has duplicates", 9, column.size)
        }
    }

    @Test
    fun eachBoxContainsUniqueNumbers() {
        val grid = generator.generateSudokuGrid()
        for (boxRow in 0 until 3) {
            for (boxCol in 0 until 3) {
                val box = mutableSetOf<Int>()
                for (i in 0 until 3) {
                    for (j in 0 until 3) {
                        box.add(grid[boxRow * 3 + i][boxCol * 3 + j])
                    }
                }
                assertEquals("Box ($boxRow,$boxCol) has duplicates", 9, box.size)
            }
        }
    }
}
