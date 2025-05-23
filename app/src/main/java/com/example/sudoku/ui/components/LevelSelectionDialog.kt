package com.example.sudoku.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sudoku.domain.model.SudokuDifficulty

@Composable
fun LevelSelectionDialog(
    onDismissRequest: () -> Unit,
    onLevelSelected: (SudokuDifficulty) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Select Difficulty Level") },
        text = {
            Column {
                SudokuDifficulty.entries.forEach { level ->
                    Button(
                        onClick = {
                            onLevelSelected(level)
                            onDismissRequest()
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text(level.name)
                    }
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
