package com.example.sudoku.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.ui.viewmodel.StatisticsViewModel
import com.example.sudoku.ui.components.statistics.BarChart


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel, modifier: Modifier = Modifier) {
    val stats by viewModel.statsData.collectAsState()
    val difficulties = SudokuDifficulty.entries

    val data = difficulties.associate { difficulty ->
        difficulty.name to (stats[difficulty]?.size ?: 0)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            BarChart(
                data = data,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp)
            )
        }
    }
}
