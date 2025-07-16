package com.example.sudoku.ui.components.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuCell(
    number: Int?,
    notes: Set<Int>,
    isSelected: Boolean,
    isOriginal: Boolean,
    hasRightBorder: Boolean,
    hasBottomBorder: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.secondaryContainer
        isOriginal -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.background
    }

    val textColor = if (isOriginal) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.primary
    }

    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(42.dp)
            .background(backgroundColor)
            .clickable(enabled = !isOriginal, onClick = onClick)
            .drawBehind {
                val normalBorder = 1.dp.toPx()
                val thickBorder = 2.5.dp.toPx()

                // Draw normal borders
                drawLine(borderColor, Offset(0f, 0f), Offset(size.width, 0f), normalBorder)
                drawLine(borderColor, Offset(0f, 0f), Offset(0f, size.height), normalBorder)
                drawLine(borderColor, Offset(size.width, 0f), Offset(size.width, size.height), normalBorder)
                drawLine(borderColor, Offset(0f, size.height), Offset(size.width, size.height), normalBorder)

                // Draw thick borders if applicable
                if (hasRightBorder) {
                    drawLine(borderColor, Offset(size.width, 0f), Offset(size.width, size.height), thickBorder)
                }
                if (hasBottomBorder) {
                    drawLine(borderColor, Offset(0f, size.height), Offset(size.width, size.height), thickBorder)
                }
            }
    ) {
        when {
            number != null -> {
                Text(
                    text = number.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
            notes.isNotEmpty() -> {
                NotesDisplay(notes)
            }
        }
    }
}
