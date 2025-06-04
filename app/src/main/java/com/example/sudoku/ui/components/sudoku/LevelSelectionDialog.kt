package com.example.sudoku.ui.components.sudoku

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
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
        title = {
            Text(
                text = "Select Difficulty Level",
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column {
                SudokuDifficulty.entries.forEach { level ->
                    Button(
                        onClick = {
                            onLevelSelected(level)
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(level.name)
                    }
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text("Cancel")
            }
        }
    )
}
