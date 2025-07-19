package com.example.sudoku.ui.navigation

sealed class Screens(var route: String) {
    data object Blog: Screens("blog")
    data object Settings: Screens("settings")
    data object Statistics: Screens("statistics")
    data object Sudoku: Screens("sudoku")
    data object Auth: Screens("authentication")
    data object Profile: Screens("profile")
    data object Support: Screens("support")
}