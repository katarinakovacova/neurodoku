package com.example.sudoku.domain.usecase

class CreateSudokuUseCase (
    private val generateSudokuUseCase: GenerateSudokuUseCase,
    private val maskSudokuUseCase: MaskSudokuUseCase
) {

    data class Sudoku (
        val completeGrid: Array<IntArray>,
        val maskedGrid: Array<Array<Int?>>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Sudoku

            if (!completeGrid.contentDeepEquals(other.completeGrid)) return false
            if (!maskedGrid.contentDeepEquals(other.maskedGrid)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = completeGrid.contentDeepHashCode()
            result = 31 * result + maskedGrid.contentDeepHashCode()
            return result
        }
    }

    fun createSudokuForSolving(): Sudoku {
        val completeGrid = generateSudokuUseCase.generateSudoku()
        val gridCopyForHint = completeGrid.map { it.clone() }.toTypedArray()
        val maskedGrid = maskSudokuUseCase.hideNumbersFromSudokuGrid(completeGrid)

        return Sudoku(completeGrid, maskedGrid)
    }



}