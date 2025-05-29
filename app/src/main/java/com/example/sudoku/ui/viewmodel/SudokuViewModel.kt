package com.example.sudoku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.domain.model.SudokuGame
import com.example.sudoku.domain.usecase.GenerateNewSudokuGameUseCase
import com.example.sudoku.domain.usecase.LoadSudokuGameUseCase
import com.example.sudoku.domain.usecase.SaveSudokuGameUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SudokuViewModel(
    private val generateNewSudokuGameUseCase: GenerateNewSudokuGameUseCase,
    private val loadSudokuGameUseCase: LoadSudokuGameUseCase,
    private val saveSudokuGameUseCase: SaveSudokuGameUseCase
) : ViewModel() {

    private val _sudokuGame = MutableStateFlow<SudokuGame?>(null)
    val sudokuGame: StateFlow<SudokuGame?> = _sudokuGame.asStateFlow()

    private val _selectedCell = MutableStateFlow<Pair<Int, Int>?>(null)
    val selectedCell: StateFlow<Pair<Int, Int>?> = _selectedCell.asStateFlow()

    private val _time = MutableStateFlow(0L)
    val time: StateFlow<Long> = _time.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            val loadedGame = loadSudokuGameUseCase()
            if (loadedGame != null) {
                _sudokuGame.value = loadedGame
                _time.value = loadedGame.timeSpent
                startTimer()
            } else {
                generateNewGame(SudokuDifficulty.MEDIUM)
            }
        }
    }

    fun generateNewGame(difficulty: SudokuDifficulty) {
        val newGame = generateNewSudokuGameUseCase(difficulty)
        _sudokuGame.value = newGame
        _selectedCell.value = null
        _time.value = 0L
        resetTimer()
    }

    fun selectCell(row: Int, col: Int) {
        _selectedCell.value = Pair(row, col)
    }

    fun setNumber(row: Int, col: Int, number: Int?) {
        val game = _sudokuGame.value ?: return
        if (game.visibleMask[row][col]) return

        val newUserGrid = Array(9) { r ->
            Array(9) { c ->
                if (r == row && c == col) number else game.userGrid[r][c]
            }
        }

        var updatedGame = game.copy(userGrid = newUserGrid)

        if (isSudokuCompleted(updatedGame)) {
            updatedGame = updatedGame.copy(isCompleted = true, completedAt = System.currentTimeMillis())
        }

        _sudokuGame.value = updatedGame

        viewModelScope.launch {
            saveSudokuGameUseCase(updatedGame)
        }
    }


    fun getHint(row: Int, col: Int) {
        val game = _sudokuGame.value ?: return
        if (game.visibleMask[row][col]) return

        val hintNumber = game.completeGrid[row][col]

        val newUserGrid = Array(9) { r ->
            Array(9) { c ->
                if (r == row && c == col) hintNumber else game.userGrid[r][c]
            }
        }

        var updatedGame = game.copy(userGrid = newUserGrid)

        if (isSudokuCompleted(updatedGame)) {
            updatedGame = updatedGame.copy(isCompleted = true, completedAt = System.currentTimeMillis())
        }

        _sudokuGame.value = updatedGame

        viewModelScope.launch {
            saveSudokuGameUseCase(updatedGame)
        }
    }


    fun eraseNumber(row: Int, col: Int) {
        setNumber(row, col, null)
    }

    fun restartGame() {
        val game = _sudokuGame.value ?: return
        generateNewGame(game.difficulty)
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _time.value += 1
                val game = _sudokuGame.value
                if (game != null) {
                    val updatedGame = game.copy(timeSpent = _time.value)
                    _sudokuGame.value = updatedGame
                    saveSudokuGameUseCase(updatedGame)
                }
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer() {
        _time.value = 0L
        stopTimer()
    }

    private fun isSudokuCompleted(game: SudokuGame): Boolean {
        for (r in 0..8) {
            for (c in 0..8) {
                val userValue = game.userGrid[r][c]
                val correctValue = game.completeGrid[r][c]
                if (userValue == null || userValue != correctValue) {
                    return false
                }
            }
        }
        return true
    }
}
