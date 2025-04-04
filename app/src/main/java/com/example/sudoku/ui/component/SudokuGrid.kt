package com.example.sudoku.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

class SudokuGrid {
}




//@Composable
//fun SudokuGrid(
//    sudoku: List<List<Int?>>,
//    originalCells: List<List<Boolean>>,
//    selectedCell: Pair<Int, Int>?,
//    onCellClick: (Int, Int) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .border(4.dp, Color.Black)
//            .background(Color.Black)
//    ) {
//        for (rowIndex in 0..8) {
//            Row {
//                for (colIndex in 0..8) {
//                    SudokuCell(
//                        number = sudoku[rowIndex][colIndex],
//                        isSelected = selectedCell == Pair(rowIndex, colIndex),
//                        isOriginal = originalCells[rowIndex][colIndex],
//                        hasRightBorder = (colIndex + 1) % 3 == 0 && colIndex != 8,
//                        hasBottomBorder = (rowIndex + 1) % 3 == 0 && rowIndex != 8,
//                        onClick = { onCellClick(rowIndex, colIndex) }
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun SudokuCell(
//    number: Int?,
//    isSelected: Boolean,
//    isOriginal: Boolean,
//    hasRightBorder: Boolean,
//    hasBottomBorder: Boolean,
//    onClick: () -> Unit
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .size(40.dp)
//            .background(
//                when {
//                    isOriginal -> Color.LightGray
//                    isSelected -> Color.Yellow
//                    else -> Color.White
//                }
//            )
//            .clickable(enabled = !isOriginal, onClick = onClick)
//            .drawBehind {
//                val borderThickness = 2.dp.toPx()
//                val thickBorder = 6.dp.toPx()
//
//                drawLine(Color.Black, Offset(0f, 0f), Offset(size.width, 0f), borderThickness)
//                drawLine(Color.Black, Offset(0f, 0f), Offset(0f, size.height), borderThickness)
//                drawLine(
//                    Color.Black,
//                    Offset(size.width, 0f),
//                    Offset(size.width, size.height),
//                    borderThickness
//                )
//                drawLine(
//                    Color.Black,
//                    Offset(0f, size.height),
//                    Offset(size.width, size.height),
//                    borderThickness
//                )
//
//                if (hasRightBorder) {
//                    drawLine(
//                        Color.Black,
//                        Offset(size.width, 0f),
//                        Offset(size.width, size.height),
//                        thickBorder
//                    )
//                }
//                if (hasBottomBorder) {
//                    drawLine(
//                        Color.Black,
//                        Offset(0f, size.height),
//                        Offset(size.width, size.height),
//                        thickBorder
//                    )
//                }
//            }
//    ) {
//        Text(
//            text = number?.toString() ?: "",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = if (isOriginal) Color.Black else Color.Blue
//        )
//    }
//}