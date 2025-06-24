package com.example.sudoku.ui.components.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Overlay(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier
                .padding(24.dp)
                .wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 280.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Sudoku paused",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Press Continue to resume.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "🧠 Dementia Prevention Tips",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    val tips = listOf(
                        "Solve puzzles regularly to keep your brain active!",
                        "Stay socially engaged: talk to friends and family.",
                        "Eat a balanced diet with brain-boosting foods.",
                        "Exercise: a healthy body supports a healthy mind.",
                        "Try new activities to challenge your brain!"
                    )

                    tips.forEach { tip ->
                        Text(
                            text = "• $tip",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                ) {
                    Text("Continue")
                }
            }
        }
    }
}
