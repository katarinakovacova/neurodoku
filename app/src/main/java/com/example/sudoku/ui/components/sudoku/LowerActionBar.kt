package com.example.sudoku.ui.components.sudoku

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LowerActionBar(
    isNotesMode: Boolean,
    onErase: () -> Unit,
    onHint: () -> Unit,
    onToggleNotesMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            text = "Erase",
            icon = Icons.Default.Delete,
            onClick = onErase
        )
        Spacer(modifier = Modifier.width(12.dp))
        ActionButton(
            text = "Hint",
            icon = Icons.Default.Info,
            onClick = onHint
        )
        Spacer(modifier = Modifier.width(12.dp))
        ActionButton(
            text = if (isNotesMode) "Notes: On" else "Notes: Off",
            icon = if (isNotesMode) Icons.Default.Create else Icons.Default.Create,
            onClick = onToggleNotesMode
        )
    }
}
