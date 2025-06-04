package com.example.sudoku.ui.components.sudoku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sudoku.domain.model.SudokuDifficulty

@Composable
fun UpperActionBar(
    onNewGame: () -> Unit,
    minutes: String,
    seconds: String,
    onStop: () -> Unit,
    onStart: () -> Unit,
    difficulty: SudokuDifficulty,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimerDisplay(
            minutes = minutes,
            seconds = seconds,
            onStop = onStop,
            onStart = onStart
        )

        Text(
            text = difficulty.name,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        ActionButton(
            text = "New",
            icon = Icons.Default.Add,
            onClick = onNewGame,
            modifier = Modifier
        )
    }
}
