package com.example.sudoku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.domain.model.SudokuGame
import com.example.sudoku.domain.usecase.GenerateNewSudokuGameUseCase
import com.example.sudoku.domain.usecase.LoadSudokuGameUseCase
import com.example.sudoku.domain.usecase.SaveSudokuGameUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
    private var autosaveJob: Job? = null

    private val _notes = MutableStateFlow(
        Array(9) { Array(9) { emptySet<Int>() } }
    )
    val notes: StateFlow<Array<Array<Set<Int>>>> = _notes.asStateFlow()

    private val _isNotesMode = MutableStateFlow(false)
    val isNotesMode: StateFlow<Boolean> = _isNotesMode.asStateFlow()

    init {
        viewModelScope.launch {
            val loadedGame = loadSudokuGameUseCase()
            if (loadedGame != null) {
                _sudokuGame.value = loadedGame
                _time.value = loadedGame.timeSpent
                _notes.value = loadedGame.notes
                startTimer()
            } else {
                generateNewGame(SudokuDifficulty.MEDIUM)
            }
        }

        observeAndAutosave()
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

        val newUserGrid = game.userGrid.mapIndexed { r, rowArray ->
            rowArray.mapIndexed { c, value ->
                if (r == row && c == col) number else value
            }.toTypedArray()
        }.toTypedArray()

        updateGame { it.copy(userGrid = newUserGrid) }

        if (number != null) {
            clearNotes(row, col)
        }
    }

    fun getHint(row: Int, col: Int) {
        val game = _sudokuGame.value ?: return
        if (game.visibleMask[row][col]) return

        val hintNumber = game.completeGrid[row][col]

        val newUserGrid = game.userGrid.mapIndexed { r, rowArray ->
            rowArray.mapIndexed { c, value ->
                if (r == row && c == col) hintNumber else value
            }.toTypedArray()
        }.toTypedArray()

        updateGame { it.copy(userGrid = newUserGrid) }
    }

    fun eraseNumber(row: Int, col: Int) {
        setNumber(row, col, null)
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _time.value += 1
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

    private fun updateGame(modifier: (SudokuGame) -> SudokuGame) {
        val game = _sudokuGame.value ?: return
        val updated = modifier(game)

        updated.userGrid.forEach { row ->
            println(row.joinToString(", ") { it?.toString() ?: "." })
        }

        val finalGame = if (isSudokuCompleted(updated)) {
            updated.copy(isCompleted = true, completedAt = System.currentTimeMillis())
        } else {
            updated
        }
        _sudokuGame.value = finalGame
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

    fun toggleNote(row: Int, col: Int, number: Int) {
        val currentNotes = _notes.value
        val newNotes = currentNotes.map { it.copyOf() }.toTypedArray()
        val cellNotes = newNotes[row][col].toMutableSet()

        if (number in cellNotes) {
            cellNotes.remove(number)
        } else {
            cellNotes.add(number)
        }
        newNotes[row][col] = cellNotes.toSet()

        _notes.value = newNotes
    }


    fun toggleNotesMode() {
        _isNotesMode.value = !_isNotesMode.value
    }

    private fun clearNotes(row: Int, col: Int) {
        val currentNotes = _notes.value
        val newNotes = Array(9) { r ->
            Array(9) { c ->
                if (r == row && c == col) emptySet() else currentNotes[r][c]
            }
        }
        _notes.value = newNotes
    }


    @OptIn(FlowPreview::class)
    private fun observeAndAutosave() {
        autosaveJob = viewModelScope.launch {
            combine(_sudokuGame, _time, _notes) { game, time, notes ->
                game?.copy(timeSpent = time, notes = notes)
            }
                .debounce(1000)
                .filterNotNull()
                .collect { game ->
                    saveSudokuGameUseCase(game)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            _sudokuGame.value?.let { game ->
                saveSudokuGameUseCase(game.copy(timeSpent = _time.value))
            }
        }
    }

}
