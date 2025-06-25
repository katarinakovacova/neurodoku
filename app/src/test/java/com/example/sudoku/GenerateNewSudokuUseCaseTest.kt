package com.example.sudoku

import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.domain.usecase.GenerateNewSudokuGameUseCase
import com.example.sudoku.domain.usecase.GenerateSudokuUseCase
import com.example.sudoku.domain.usecase.MaskSudokuUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GenerateNewSudokuGameUseCaseTest {

    private lateinit var generateSudokuUseCase: GenerateSudokuUseCase
    private lateinit var maskSudokuUseCase: MaskSudokuUseCase
    private lateinit var generateNewGameUseCase: GenerateNewSudokuGameUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        generateSudokuUseCase = mock(GenerateSudokuUseCase::class.java)
        maskSudokuUseCase = mock(MaskSudokuUseCase::class.java)
        generateNewGameUseCase = GenerateNewSudokuGameUseCase(generateSudokuUseCase, maskSudokuUseCase)
    }

    @Test
    fun `invoke returns SudokuGame with correct fields`() {
        val difficulty = SudokuDifficulty.EASY

        val completeGrid = Array(9) { row -> IntArray(9) { col -> row * 9 + col + 1 } }
        val visibleMask = Array(9) { row -> Array(9) { col -> (row + col) % 2 == 0 } }

        `when`(generateSudokuUseCase.generateSudokuGrid()).thenReturn(completeGrid)
        `when`(maskSudokuUseCase.generateVisibleMask(difficulty)).thenReturn(visibleMask)

        val game = generateNewGameUseCase.invoke(difficulty)

        for (row in 0..8) {
            for (col in 0..8) {
                assertEquals(completeGrid[row][col], game.completeGrid[row][col])
            }
        }

        for (row in 0..8) {
            for (col in 0..8) {
                assertEquals(visibleMask[row][col], game.visibleMask[row][col])
            }
        }

        for (row in 0..8) {
            for (col in 0..8) {
                if (visibleMask[row][col]) {
                    assertEquals(completeGrid[row][col], game.userGrid[row][col])
                } else {
                    assertNull(game.userGrid[row][col])
                }
            }
        }

        assertEquals(difficulty, game.difficulty)
        assertEquals(0L, game.timeSpent)
        assertFalse(game.isCompleted)
        assertNotNull(game.createdAt)
        assertNull(game.completedAt)

        val now = System.currentTimeMillis()
        assertTrue(game.createdAt in (now - 1000)..now)
    }
}
