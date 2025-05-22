package com.example.sudoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuCell(
    number: Int?,
    isSelected: Boolean,
    isOriginal: Boolean,
    hasRightBorder: Boolean,
    hasBottomBorder: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> Color(0xFFDCE3FF)
        isOriginal -> Color(0xFFEEEEEE)
        else -> Color.White
    }

    val textColor = if (isOriginal) Color.Black else MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(42.dp)
            .background(backgroundColor)
            .clickable(enabled = !isOriginal, onClick = onClick)
            .drawBehind {
                val normalBorder = 1.dp.toPx()
                val thickBorder = 2.5.dp.toPx()
                val black = Color.Black

                drawLine(black, Offset(0f, 0f), Offset(size.width, 0f), normalBorder)
                drawLine(black, Offset(0f, 0f), Offset(0f, size.height), normalBorder)
                drawLine(black, Offset(size.width, 0f), Offset(size.width, size.height), normalBorder)
                drawLine(black, Offset(0f, size.height), Offset(size.width, size.height), normalBorder)

                if (hasRightBorder) {
                    drawLine(black, Offset(size.width, 0f), Offset(size.width, size.height), thickBorder)
                }
                if (hasBottomBorder) {
                    drawLine(black, Offset(0f, size.height), Offset(size.width, size.height), thickBorder)
                }
            }
    ) {
        Text(
            text = number?.toString() ?: "",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}
