package com.example.sudoku.ui.components.sudoku

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LowerActionBar(
    onErase: () -> Unit,
    onHint: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(text = "Erase", icon = Icons.Default.Delete, onClick = onErase)
        Spacer(modifier = Modifier.width(12.dp))
        ActionButton(text = "Hint", icon = Icons.Default.Info, onClick = onHint)
        Spacer(modifier = Modifier.width(12.dp))
        ActionButton(text = "Restart", icon = Icons.Default.Refresh, onClick = onRestart)
    }
}
