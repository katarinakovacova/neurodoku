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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.ui.components.sudoku.LevelSelectionDialog
import com.example.sudoku.ui.components.sudoku.LowerActionBar
import com.example.sudoku.ui.components.sudoku.NumberSelector
import com.example.sudoku.ui.components.sudoku.Overlay
import com.example.sudoku.ui.components.sudoku.SudokuGrid
import com.example.sudoku.ui.components.sudoku.UpperActionBar
import com.example.sudoku.ui.viewmodel.SudokuViewModel

@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel,
    modifier: Modifier = Modifier
) {
    val sudokuGame by viewModel.sudokuGame.collectAsState()
    val selectedCell by viewModel.selectedCell.collectAsState()
    val time by viewModel.time.collectAsState()
    val isNotesMode by viewModel.isNotesMode.collectAsState()

    var isOverlayVisible by remember { mutableStateOf(false) }
    var showLevelDialog by remember { mutableStateOf(false) }

    val minutes = (time / 60).toString().padStart(2, '0')
    val seconds = (time % 60).toString().padStart(2, '0')

    val notes by viewModel.notes.collectAsState()

    val startTimer = {
        viewModel.startTimer()
        isOverlayVisible = false
    }
    val stopTimer = {
        viewModel.stopTimer()
        isOverlayVisible = true
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    androidx.compose.runtime.DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> stopTimer()
                Lifecycle.Event.ON_RESUME -> startTimer()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
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

        sudokuGame?.let { game ->
            SudokuGrid(
                sudoku = game.userGrid,
                originalCells = game.visibleMask,
                selectedCell = selectedCell,
                notes = notes,
                onCellClick = { row, col ->
                    if (!game.visibleMask[row][col]) {
                        viewModel.selectCell(row, col)
                    }
                }
            )
        } ?: run {
            androidx.compose.material3.Text(text = "Loading Sudoku…")
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
            onToggleNotesMode = {
                viewModel.toggleNotesMode()
            },
            isNotesMode = isNotesMode
        )

        Spacer(modifier = Modifier.height(16.dp))

        NumberSelector { number ->
            selectedCell?.let { (row, col) ->
                if (isNotesMode) {
                    viewModel.toggleNote(row, col, number)
                } else {
                    viewModel.setNumber(row, col, number)
                }
            }
        }
    }
}
