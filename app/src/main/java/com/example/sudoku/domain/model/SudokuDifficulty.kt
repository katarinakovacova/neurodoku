package com.example.sudoku.domain.model

enum class SudokuDifficulty(val visibleCount: Int) {
    EASY(40),
    MEDIUM(32),
    HARD(24),
    EXTREME(20)
}
