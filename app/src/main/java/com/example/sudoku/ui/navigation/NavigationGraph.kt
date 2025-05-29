package com.example.sudoku.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sudoku.ui.screens.BlogScreen
import com.example.sudoku.ui.screens.SettingsScreen
import com.example.sudoku.ui.screens.StatisticsScreen
import com.example.sudoku.ui.screens.SudokuScreen
import com.example.sudoku.ui.viewmodel.StatisticsViewModel
import com.example.sudoku.ui.viewmodel.SudokuViewModel
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetUpNavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    NavHost(navController = navController, startDestination = Screens.Sudoku.route) {

        composable(Screens.Blog.route) {
            BlogScreen(innerPadding = innerPadding)
        }

        composable(Screens.Settings.route) {
            SettingsScreen(innerPadding = innerPadding)
        }

        composable(Screens.Statistics.route) {
            val viewModel: StatisticsViewModel = getViewModel()  // import: org.koin.androidx.compose.getViewModel
            StatisticsScreen(viewModel = viewModel)
        }

        composable(Screens.Sudoku.route) {
            val sudokuViewModel: SudokuViewModel = getViewModel()
            SudokuScreen(
                viewModel = sudokuViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}