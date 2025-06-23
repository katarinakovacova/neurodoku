package com.example.sudoku.domain.model

data class SudokuGame(
    val id: Long = 0L,
    val completeGrid: Array<IntArray>,
    val visibleMask: Array<Array<Boolean>>,
    val userGrid: Array<Array<Int?>>,
    val notes: Array<Array<Set<Int>>> = Array(9) { Array(9) { emptySet<Int>() } },
    val difficulty: SudokuDifficulty,
    val timeSpent: Long,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val completedAt: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SudokuGame

        if (id != other.id) return false
        if (!completeGrid.contentDeepEquals(other.completeGrid)) return false
        if (!visibleMask.contentDeepEquals(other.visibleMask)) return false
        if (!userGrid.contentDeepEquals(other.userGrid)) return false
        if (!notes.contentDeepEquals(other.notes)) return false
        if (difficulty != other.difficulty) return false
        if (timeSpent != other.timeSpent) return false
        if (isCompleted != other.isCompleted) return false
        if (createdAt != other.createdAt) return false
        if (completedAt != other.completedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + completeGrid.contentDeepHashCode()
        result = 31 * result + visibleMask.contentDeepHashCode()
        result = 31 * result + userGrid.contentDeepHashCode()
        result = 31 * result + notes.contentDeepHashCode()
        result = 31 * result + difficulty.hashCode()
        result = 31 * result + timeSpent.hashCode()
        result = 31 * result + isCompleted.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (completedAt?.hashCode() ?: 0)
        return result
    }
}
