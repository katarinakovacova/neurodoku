package com.example.sudoku.domain.usecase

fun main() {
    val sudokuGenerator = GenerateSudokuUseCase()
    val sudokuPuzzle = sudokuGenerator.generateSudokuGrid()

    val masker = MaskSudokuUseCase()
    val mask = masker.generateVisibleMask()

    println("== Complete Sudoku Grid ==")
    printSudokuPuzzle(sudokuPuzzle)

    val maskedSudokuPuzzle = applyMaskToSudokuGrid(sudokuPuzzle, mask)
    println("\n== Masked Sudoku Grid(with 0 for hidden values) ==")
    printSudokuPuzzle(maskedSudokuPuzzle)
}

fun printSudokuPuzzle(sudokuGrid: Array<IntArray>) {
    for (row in sudokuGrid.indices) {
        if (row % 3 == 0 && row != 0) {
            println("------+-------+------")
        }
        for (column in sudokuGrid[row].indices) {
            if (column % 3 == 0 && column != 0) {
                print("| ")
            }
            print("${sudokuGrid[row][column]} ")
        }
    println()
    }
}

fun applyMaskToSudokuGrid(sudokuPuzzle: Array<IntArray>, mask: Array<Array<Boolean>>): Array<IntArray> {
    val maskedSudokuPuzzle = Array(9) {IntArray(9)}
    for (row in 0..8) {
        for (column in 0..8) {
            maskedSudokuPuzzle[row][column] = if (mask[row][column]) sudokuPuzzle[row][column] else 0
        }
    }
    return maskedSudokuPuzzle
}