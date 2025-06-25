package com.example.sudoku

import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.domain.usecase.MaskSudokuUseCase
import org.junit.Assert.*
import org.junit.Test

class MaskSudokuUseCaseTest {

    private val maskUseCase = MaskSudokuUseCase()

    @Test
    fun maskHasCorrectSize() {
        val mask = maskUseCase.generateVisibleMask(SudokuDifficulty.EASY)
        assertEquals(9, mask.size)
        mask.forEach { row -> assertEquals(9, row.size) }
    }

    @Test
    fun easyDifficultyHasCorrectVisibleCount() {
        val visibleCount = SudokuDifficulty.EASY.visibleCount
        val mask = maskUseCase.generateVisibleMask(SudokuDifficulty.EASY)

        val actualVisible = mask.sumOf { row -> row.count { it } }
        assertEquals(visibleCount, actualVisible)
    }

    @Test
    fun visibleCellsAreUnique() {
        val mask = maskUseCase.generateVisibleMask(SudokuDifficulty.MEDIUM)
        val visiblePositions = mutableSetOf<Pair<Int, Int>>()

        for (i in mask.indices) {
            for (j in mask[i].indices) {
                if (mask[i][j]) {
                    val added = visiblePositions.add(Pair(i, j))
                    assertTrue("Duplicate visible cell at ($i,$j)", added)
                }
            }
        }
    }

    @Test
    fun differentMasksForSameDifficulty() {
        val mask1 = maskUseCase.generateVisibleMask(SudokuDifficulty.HARD)
        val mask2 = maskUseCase.generateVisibleMask(SudokuDifficulty.HARD)

        val flat1 = mask1.flatten()
        val flat2 = mask2.flatten()

        val sameCells = flat1.zip(flat2).count { (a, b) -> a == b }
        assertTrue("Too many cells are the same, mask may not be random", sameCells < 81)
    }
}
