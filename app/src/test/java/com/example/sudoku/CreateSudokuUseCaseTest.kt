package com.example.sudoku

import com.example.sudoku.domain.usecase.CreateSudokuUseCase
import com.example.sudoku.domain.usecase.GenerateSudokuUseCase
import com.example.sudoku.domain.usecase.MaskSudokuUseCase
import org.junit.Assert.*
import org.junit.Test

class CreateSudokuUseCaseTest {

    private val generator = GenerateSudokuUseCase()
    private val masker = MaskSudokuUseCase()
    private val createSudoku = CreateSudokuUseCase(generator, masker)

    @Test
    fun `created sudoku has valid complete and masked grids`() {
        val sudoku = createSudoku.createSudokuForSolving()

        assertEquals(9, sudoku.completeGrid.size)
        assertTrue(sudoku.completeGrid.all { it.size == 9 })
        assertEquals(9, sudoku.maskedGrid.size)
        assertTrue(sudoku.maskedGrid.all { it.size == 9 })

        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (sudoku.maskedGrid[i][j] != null) {
                    assertEquals(sudoku.completeGrid[i][j], sudoku.maskedGrid[i][j])
                }
            }
        }
    }
}
