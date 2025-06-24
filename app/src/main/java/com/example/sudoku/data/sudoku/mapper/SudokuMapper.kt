package com.example.sudoku.data.sudoku.mapper

import com.example.sudoku.data.sudoku.local.SudokuEntity
import com.example.sudoku.domain.model.SudokuDifficulty
import com.example.sudoku.domain.model.SudokuGame
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SudokuMapper(private val gson: Gson = Gson()) {

    fun entityToDomain(entity: SudokuEntity): SudokuGame {
        val completeGrid = gson.fromJson(entity.initialGrid, Array<IntArray>::class.java)
        val visibleMask = gson.fromJson(entity.maskedGrid, Array<Array<Boolean>>::class.java)

        val type = object : TypeToken<List<List<Int?>>>() {}.type
        val userGridList = gson.fromJson<List<List<Int?>>>(entity.userGrid, type)
        val userGrid = userGridList.map { it.toTypedArray() }.toTypedArray()

        val difficulty = SudokuDifficulty.valueOf(entity.difficulty)

        val notesType = object : TypeToken<Array<Array<Set<Int>>>>() {}.type
        val notes = entity.notesJson?.let { gson.fromJson(it, notesType) }
            ?: Array(9) { Array(9) { emptySet<Int>() } }

        return SudokuGame(
            id = entity.id,
            completeGrid = completeGrid,
            visibleMask = visibleMask,
            userGrid = userGrid,
            notes = notes,
            difficulty = difficulty,
            timeSpent = entity.timeSpent,
            isCompleted = entity.isCompleted,
            createdAt = entity.createdAt,
            completedAt = entity.completedAt
        )
    }

    fun domainToEntity(domain: SudokuGame): SudokuEntity {
        val initialGridJson = gson.toJson(domain.completeGrid)
        val maskedGridJson = gson.toJson(domain.visibleMask)
        val userGridJson = gson.toJson(domain.userGrid)
        val notesJson = gson.toJson(domain.notes)

        return SudokuEntity(
            id = domain.id,
            initialGrid = initialGridJson,
            maskedGrid = maskedGridJson,
            userGrid = userGridJson,
            notesJson = notesJson,
            difficulty = domain.difficulty.name,
            timeSpent = domain.timeSpent,
            isCompleted = domain.isCompleted,
            completedAt = domain.completedAt,
            createdAt = domain.createdAt
        )
    }
}
