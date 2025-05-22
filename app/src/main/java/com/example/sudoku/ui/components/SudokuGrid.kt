package com.example.sudoku.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SudokuGrid(
    sudoku: List<List<Int?>>,
    originalCells: List<List<Boolean>>,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .border(3.dp, Color.Black)
            .padding(1.dp)
    ) {
        for (rowIndex in 0..8) {
            Row {
                for (colIndex in 0..8) {
                    SudokuCell(
                        number = sudoku[rowIndex][colIndex],
                        isSelected = selectedCell == Pair(rowIndex, colIndex),
                        isOriginal = originalCells[rowIndex][colIndex],
                        hasRightBorder = (colIndex + 1) % 3 == 0 && colIndex != 8,
                        hasBottomBorder = (rowIndex + 1) % 3 == 0 && rowIndex != 8,
                        onClick = { onCellClick(rowIndex, colIndex) }
                    )
                }
            }
        }
    }
}
