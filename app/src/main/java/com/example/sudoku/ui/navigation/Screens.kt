package com.example.sudoku.ui.navigation

sealed class Screens(var route: String) {
    data object Blog: Screens("blog")
    data object Settings: Screens("settings")
    data object Statistics: Screens("statistics")
    data object Sudoku: Screens("sudoku")
}