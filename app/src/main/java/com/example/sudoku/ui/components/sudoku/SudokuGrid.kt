package com.example.sudoku.ui.components.sudoku

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun SudokuGrid(
    sudoku: Array<Array<Int?>>,
    notes: Array<Array<Set<Int>>>,
    originalCells: Array<Array<Boolean>>,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .testTag("sudokuGrid")
            .border(3.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
            .padding(1.dp)
    ) {
        for (rowIndex in sudoku.indices) {
            Row {
                for (colIndex in sudoku[rowIndex].indices) {
                    SudokuCell(
                        number = sudoku[rowIndex][colIndex],
                        notes = notes[rowIndex][colIndex],
                        isSelected = selectedCell == Pair(rowIndex, colIndex),
                        isOriginal = originalCells[rowIndex][colIndex],
                        hasRightBorder = (colIndex + 1) % 3 == 0 && colIndex != sudoku[rowIndex].lastIndex,
                        hasBottomBorder = (rowIndex + 1) % 3 == 0 && rowIndex != sudoku.lastIndex,
                        onClick = { onCellClick(rowIndex, colIndex) },
                        modifier = Modifier.testTag("cell_${rowIndex}_$colIndex")
                    )
                }
            }
        }
    }
}
