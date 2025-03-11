package com.example.sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.logic.Sudoku

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuScreen()
        }
    }
}


@Composable
fun SudokuScreen() {
    val sudokuSolver = remember { Sudoku() }
    val completeSudoku = sudokuSolver.generateSudoku()
    val (sudoku, initialOriginalCells) = remember { maskSudoku(completeSudoku) }
    var sudokuState by remember { mutableStateOf(sudoku) }
    var originalCells by remember { mutableStateOf(initialOriginalCells) }
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SudokuGrid(sudokuState, originalCells, selectedCell) { row, col ->
            if (!originalCells[row][col]) {
                selectedCell = if (selectedCell == Pair(row, col)) null else Pair(row, col)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ActionBar(
            onErase = {
                selectedCell?.let { (row, col) ->
                    if (!originalCells[row][col]) {
                        sudokuState = sudokuState.mapIndexed { r, rowList ->
                            rowList.mapIndexed { c, cell ->
                                if (r == row && c == col) null else cell
                            }
                        }
                    }
                }
            },
            onHint = {
                selectedCell?.let { (row, col) ->
                    sudokuState = sudokuState.mapIndexed { r, rowList ->
                        rowList.mapIndexed { c, cell ->
                            if (r == row && c == col) completeSudoku[row][col] else cell
                        }
                    }
                }
            },
            onRestart = {
                val (newSudoku, newOriginalCells) = maskSudoku(completeSudoku)
                sudokuState = newSudoku
                originalCells = newOriginalCells
                selectedCell = null
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        NumberSelector { number ->
            selectedCell?.let { (row, col) ->
                if (!originalCells[row][col]) {
                    sudokuState = sudokuState.mapIndexed { r, rowList ->
                        rowList.mapIndexed { c, cell ->
                            if (r == row && c == col) number else cell
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SudokuGrid(
    sudoku: List<List<Int?>>,
    originalCells: List<List<Boolean>>,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(4.dp, Color.Black)
            .background(Color.Black)
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

@Composable
fun SudokuCell(
    number: Int?,
    isSelected: Boolean,
    isOriginal: Boolean,
    hasRightBorder: Boolean,
    hasBottomBorder: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(
                when {
                isOriginal -> Color.LightGray
                isSelected -> Color.Yellow
                else -> Color.White
                }
            )
            .clickable(enabled = !isOriginal, onClick = onClick)
            .drawBehind {
                val borderThickness = 2.dp.toPx()
                val thickBorder = 6.dp.toPx()

                drawLine(Color.Black, Offset(0f, 0f), Offset(size.width, 0f), borderThickness)
                drawLine(Color.Black, Offset(0f, 0f), Offset(0f, size.height), borderThickness)
                drawLine(Color.Black, Offset(size.width, 0f), Offset(size.width, size.height), borderThickness)
                drawLine(Color.Black, Offset(0f, size.height), Offset(size.width, size.height), borderThickness)

                if (hasRightBorder) {
                    drawLine(Color.Black, Offset(size.width, 0f), Offset(size.width, size.height), thickBorder)
                }
                if (hasBottomBorder) {
                    drawLine(Color.Black, Offset(0f, size.height), Offset(size.width, size.height), thickBorder)
                }
            }
    ) {
        Text(
            text = number?.toString() ?: "",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isOriginal) Color.Black else Color.Blue
        )
    }
}

@Composable
fun ActionBar(
    onErase: () -> Unit,
    onHint: () -> Unit,
    onRestart: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        ActionButton(text = "Erase", onClick = onErase)
        Spacer(modifier = Modifier.width(8.dp))
        ActionButton(text = "Hint", onClick = onHint)
        Spacer(modifier = Modifier.width(8.dp))
        ActionButton(text = "Restart", onClick = onRestart)
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp, 40.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.White)
    }
}

@Composable
fun NumberSelector(onNumberSelected: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.Center) {
        for (num in 1..9) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                    .clickable { onNumberSelected(num) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = num.toString(), fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

fun maskSudoku(completeSudoku: Array<IntArray>): Pair<List<List<Int?>>, List<List<Boolean>>> {
    val maskedSudoku: List<MutableList<Int?>> = completeSudoku.map { row ->
        row.map { it as Int? }.toMutableList()
    }
    val originalCells = List(9) { MutableList(9) { true } }

    val positions = mutableListOf<Pair<Int, Int>>()
    for (row in 0..8) {
        for (col in 0..8) {
            positions.add(Pair(row, col))
        }
    }

    positions.shuffle()
    val visibleCells = positions.take(20)

    for ((row, col) in positions) {
        if (!visibleCells.contains(Pair(row, col))) {
            maskedSudoku[row][col] = null
            originalCells[row][col] = false
        }
    }

    return Pair(maskedSudoku, originalCells)
}
