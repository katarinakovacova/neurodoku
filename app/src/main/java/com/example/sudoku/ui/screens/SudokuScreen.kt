package com.example.sudoku.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.ui.components.LevelSelectionDialog
import com.example.sudoku.ui.components.LowerActionBar
import com.example.sudoku.ui.components.NumberSelector
import com.example.sudoku.ui.components.Overlay
import com.example.sudoku.ui.components.SudokuGrid
import com.example.sudoku.ui.components.UpperActionBar
import com.example.sudoku.ui.viewmodel.SudokuViewModel

@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel,
    modifier: Modifier = Modifier
) {
    val sudokuGame by viewModel.sudokuGame.collectAsState()
    val selectedCell by viewModel.selectedCell.collectAsState()
    val time by viewModel.time.collectAsState()

    var isOverlayVisible by remember { mutableStateOf(false) }
    var showLevelDialog by remember { mutableStateOf(false) }

    val minutes = (time / 60).toString().padStart(2, '0')
    val seconds = (time % 60).toString().padStart(2, '0')

    val startTimer = {
        viewModel.startTimer()
        isOverlayVisible = false
    }
    val stopTimer = {
        viewModel.stopTimer()
        isOverlayVisible = true
    }

    if (showLevelDialog) {
        LevelSelectionDialog(
            onDismissRequest = { showLevelDialog = false },
            onLevelSelected = { selectedDifficulty ->
                showLevelDialog = false
                viewModel.generateNewGame(selectedDifficulty)
                startTimer()
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        UpperActionBar(
            onNewGame = { showLevelDialog = true },
            minutes = minutes,
            seconds = seconds,
            onStop = stopTimer,
            onStart = startTimer,
            difficulty = sudokuGame?.difficulty ?: SudokuDifficulty.MEDIUM
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isOverlayVisible) {
            Overlay(onDismiss = {
                isOverlayVisible = false
                startTimer()
            })
        }

        if (sudokuGame != null) {
            SudokuGrid(
                sudoku = sudokuGame!!.userGrid,
                originalCells = sudokuGame!!.visibleMask,
                selectedCell = selectedCell,
                onCellClick = { row, col ->
                    if (!sudokuGame!!.visibleMask[row][col]) {
                        viewModel.selectCell(row, col)
                    }
                }
            )
        } else {
            Spacer(modifier = Modifier.height(200.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LowerActionBar(
            onErase = {
                selectedCell?.let { (row, col) ->
                    viewModel.eraseNumber(row, col)
                }
            },
            onHint = {
                selectedCell?.let { (row, col) ->
                    viewModel.getHint(row, col)
                }
            },
            onRestart = {
                sudokuGame?.let {
                    viewModel.generateNewGame(it.difficulty)
                    startTimer()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        NumberSelector { number ->
            selectedCell?.let { (row, col) ->
                viewModel.setNumber(row, col, number)
            }
        }
    }
}
