package com.example.sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    var sudoku by remember { mutableStateOf(generateEmptySudoku()) }
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SudokuGrid(sudoku, selectedCell) { row, col ->
            selectedCell = if (selectedCell == Pair(row, col)) null else Pair(row, col)
        }

        Spacer(modifier = Modifier.height(16.dp))

        NumberSelector { number ->
            selectedCell?.let { (row, col) ->
                sudoku = sudoku.mapIndexed { r, rowList ->
                    rowList.mapIndexed { c, cell ->
                        if (r == row && c == col) number else cell
                    }
                }
            }
        }
    }
}

@Composable
fun SudokuGrid(
    sudoku: List<List<Int?>>,
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
                        isOriginal = sudoku[rowIndex][colIndex] != null,
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
            .background(if (isSelected) Color.Yellow else Color.White)
            .clickable(enabled = !isOriginal, onClick = onClick)
            .drawBehind {
                val borderThickness = 2.dp.toPx()
                val thickBorder = 6.dp.toPx()

                drawLine(
                    Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = borderThickness
                )
                drawLine(
                    Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = borderThickness
                )
                drawLine(
                    Color.Black,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderThickness
                )
                drawLine(
                    Color.Black,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderThickness
                )

                if (hasRightBorder) {
                    drawLine(
                        Color.Black,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = thickBorder
                    )
                }
                if (hasBottomBorder) {
                    drawLine(
                        Color.Black,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = thickBorder
                    )
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
fun NumberSelector(onNumberSelected: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.Center) {
        for (num in 1..9) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray)
                    .clickable { onNumberSelected(num) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = num.toString(), fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

fun generateEmptySudoku(): List<List<Int?>> {
    return List(9) { List(9) { null } }
}

