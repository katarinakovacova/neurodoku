package com.example.sudoku.ui.screen

//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.sudoku.ActionBar
//import com.example.sudoku.Overlay
//import com.example.sudoku.SudokuGrid
//import com.example.sudoku.TimerDisplay
//import com.example.sudoku.domain.usecase.GenerateSudokuUseCase
//import com.example.sudoku.maskSudoku
//import kotlinx.coroutines.delay
//
//@Composable
//fun SudokuScreen() {
//    val sudokuSolver = remember { GenerateSudokuUseCase() }
//
//    var completeSudoku by remember { mutableStateOf(sudokuSolver.generateSudoku()) }
//    var (sudoku, initialOriginalCells) = remember { maskSudoku(completeSudoku) }
//
//    var initialSudokuState by remember { mutableStateOf(sudoku.map { it.toList() }) }
//    var sudokuState by remember { mutableStateOf(initialSudokuState) }
//    var originalCells by remember { mutableStateOf(initialOriginalCells) }
//    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
//
//    // Stav pre časovač
//    var time by remember { mutableStateOf(0) }
//    var isTimerRunning by remember { mutableStateOf(false) }
//
//    // Stav pre overlay
//    var isOverlayVisible by remember { mutableStateOf(false) }
//
//    // Funkcia na zastavenie časovača
//    val stopTimer = {
//        isTimerRunning = false
//        isOverlayVisible = true // Zobraz overlay
//    }
//
//    // Funkcia na spustenie časovača
//    val startTimer = {
//        isTimerRunning = true
//        isOverlayVisible = false // Skryje overlay
//    }
//
//    // Na začiatku sa časovač spustí
//    LaunchedEffect(key1 = Unit) {
//        startTimer() // Automatické spustenie časovača pri načítaní obrazovky
//    }
//
//    // Na začiatku sa časovač spustí, ale je možné ho zastaviť a pokračovať
//    LaunchedEffect(key1 = isTimerRunning) {
//        if (isTimerRunning) {
//            while (true) {
//                delay(1000L) // Časovač beží každú sekundu
//                time += 1
//            }
//        }
//    }
//
//    // Formátovanie času do podoby 00:00
//    val minutes = (time / 60).toString().padStart(2, '0')
//    val seconds = (time % 60).toString().padStart(2, '0')
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp), // Pridaj odskok zhora
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        // Zobrazenie časovača nad mriežkou
//        TimerDisplay(minutes, seconds, onStop = stopTimer, onStart = startTimer)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Zobraziť overlay, ak je aktivovaný
//        if (isOverlayVisible) {
//            Overlay(
//                onDismiss = {
//                    isOverlayVisible = false
//                    startTimer() // Pokračovanie časovača
//                }
//            )
//        }
//
//        // Sudoku grid
//        SudokuGrid(sudokuState, originalCells, selectedCell) { row, col ->
//            if (!originalCells[row][col]) {
//                selectedCell = if (selectedCell == Pair(row, col)) null else Pair(row, col)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        ActionBar(
//            onErase = {
//                selectedCell?.let { (row, col) ->
//                    if (!originalCells[row][col]) {
//                        sudokuState = sudokuState.mapIndexed { r, rowList ->
//                            rowList.mapIndexed { c, cell ->
//                                if (r == row && c == col) null else cell
//                            }
//                        }
//                    }
//                }
//            },
//            onHint = {
//                selectedCell?.let { (row, col) ->
//                    sudokuState = sudokuState.mapIndexed { r, rowList ->
//                        rowList.mapIndexed { c, cell ->
//                            if (r == row && c == col) completeSudoku[row][col] else cell
//                        }
//                    }
//                }
//            },
//            onGetNewSudoku = {
//                // Reset časovača pri generovaní nového sudoku
//                time = 0 // Reset na 00:00
//                startTimer() // Spustí časovač od začiatku
//
//                completeSudoku = sudokuSolver.generateSudoku()
//                val (newSudoku, newOriginalCells) = maskSudoku(completeSudoku)
//
//                initialSudokuState = newSudoku.map { it.toList() }
//                sudokuState = newSudoku
//                originalCells = newOriginalCells
//                selectedCell = null
//            },
//            onRestart = {
//                sudokuState = initialSudokuState.map { it.toList() }
//                selectedCell = null
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        NumberSelector { number ->
//            selectedCell?.let { (row, col) ->
//                if (!originalCells[row][col]) {
//                    sudokuState = sudokuState.mapIndexed { r, rowList ->
//                        rowList.mapIndexed { c, cell ->
//                            if (r == row && c == col) number else cell
//                        }
//                    }
//                }
//            }
//        }
//    }
//}