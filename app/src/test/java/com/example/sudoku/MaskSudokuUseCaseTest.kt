package com.example.sudoku

import com.example.sudoku.domain.usecase.GenerateSudokuUseCase
import com.example.sudoku.domain.usecase.MaskSudokuUseCase
import org.junit.Assert.*
import org.junit.Test

class MaskSudokuUseCaseTest {

    private val generator = GenerateSudokuUseCase()
    private val masker = MaskSudokuUseCase()

    @Test
    fun `masked sudoku has exactly 20 visible numbers`() {
        val completeGrid = generator.generateSudoku()
        val maskedGrid = masker.hideNumbersFromSudokuGrid(completeGrid)

        val visibleNumbers = maskedGrid.sumOf { row -> row.count { it != null } }

        assertEquals(20, visibleNumbers)
    }

    @Test
    fun `masked grid has the same structure as complete grid`() {
        val completeGrid = generator.generateSudoku()
        val maskedGrid = masker.hideNumbersFromSudokuGrid(completeGrid)

        assertEquals(9, maskedGrid.size)
        assertTrue(maskedGrid.all { it.size == 9 })
    }
}
