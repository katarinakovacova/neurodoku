package com.example.sudoku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.domain.model.SudokuGame
import com.example.sudoku.domain.usecase.GetCompletedSudokuGamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val getCompletedSudokuGamesUseCase: GetCompletedSudokuGamesUseCase
) : ViewModel() {

    private val _statsData = MutableStateFlow<Map<SudokuDifficulty, List<SudokuGame>>>(emptyMap())
    val statsData: StateFlow<Map<SudokuDifficulty, List<SudokuGame>>> = _statsData

    init {
        viewModelScope.launch {
            // Natvrdo testovacie dáta
            val testData = mapOf(
                SudokuDifficulty.EASY to listOf(createTestGame(SudokuDifficulty.EASY)),
                SudokuDifficulty.MEDIUM to listOf(
                    createTestGame(SudokuDifficulty.MEDIUM),
                    createTestGame(SudokuDifficulty.MEDIUM)
                ),
                SudokuDifficulty.HARD to emptyList(),
            )
            _statsData.value = testData

            // Ak chceš použiť reálne dáta, odkomentuj toto:
            // val allGames = getCompletedSudokuGamesUseCase()
            // val grouped = allGames.groupBy { it.difficulty }
            // _statsData.value = grouped
        }
    }

    private fun createTestGame(difficulty: SudokuDifficulty): SudokuGame {
        return SudokuGame(
            completeGrid = Array(9) { IntArray(9) { 1 } },
            visibleMask = Array(9) { Array(9) { true } },
            userGrid = Array(9) { Array<Int?>(9) { 1 } },
            difficulty = difficulty,
            timeSpent = 1000L,
            isCompleted = true,
            createdAt = System.currentTimeMillis(),
            completedAt = System.currentTimeMillis()
        )
    }
}
