package com.example.sudoku.domain.usecase

import android.annotation.SuppressLint
import androidx.wear.compose.materialcore.currentTimeMillis
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.domain.model.SudokuGame

class GenerateNewSudokuGameUseCase(
    private val generateSudokuUseCase: GenerateSudokuUseCase,
    private val maskSudokuUseCase: MaskSudokuUseCase
) {

    @SuppressLint("RestrictedApi")
    operator fun invoke(difficulty: SudokuDifficulty): SudokuGame {
        val completeGrid = generateSudokuUseCase.generateSudokuGrid()
        val visibleMask = maskSudokuUseCase.generateVisibleMask(difficulty)

        val userGrid: Array<Array<Int?>> = Array(9) { row ->
            Array(9) { col ->
                if (visibleMask[row][col]) completeGrid[row][col] else null
            }
        }

        return SudokuGame(
            completeGrid = completeGrid,
            visibleMask = visibleMask,
            userGrid = userGrid,
            difficulty = difficulty,
            timeSpent = 0L,
            isCompleted = false,
            createdAt = currentTimeMillis(),
            completedAt = null
        )
    }
}
