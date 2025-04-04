package com.example.sudoku

import com.example.sudoku.domain.usecase.GenerateSudokuUseCase
import org.junit.Assert.*
import org.junit.Test

class GenerateSudokuUseCaseTest {

    private val generator = GenerateSudokuUseCase()

    @Test
    fun `generated sudoku is valid`() {
        val grid = generator.generateSudoku()

        assertEquals(9, grid.size)
        assertTrue(grid.all { it.size == 9 })

        for (i in 0 until 9) {
            val rowSet = mutableSetOf<Int>()
            val colSet = mutableSetOf<Int>()
            val boxSet = mutableSetOf<Int>()

            for (j in 0 until 9) {
                val rowVal = grid[i][j]
                val colVal = grid[j][i]
                val boxVal = grid[(i / 3) * 3 + j / 3][(i % 3) * 3 + j % 3]

                assertTrue(rowSet.add(rowVal))
                assertTrue(colSet.add(colVal))
                assertTrue(boxSet.add(boxVal))
            }
        }
    }
}
