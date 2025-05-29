package com.example.sudoku.data.repository

import com.example.sudoku.data.local.SudokuDao
import com.example.sudoku.data.mapper.SudokuMapper
import com.example.sudoku.domain.model.SudokuGame

class SudokuRepositoryImpl(
    private val sudokuDao: SudokuDao,
    private val sudokuMapper: SudokuMapper
) : SudokuRepository {

    override suspend fun getUnfinishedSudoku(): SudokuGame? {
        val entity = sudokuDao.getUnfinishedSudoku()
        return entity?.let { sudokuMapper.entityToDomain(it) }
    }

    override suspend fun insertSudoku(sudoku: SudokuGame) {
        val entity = sudokuMapper.domainToEntity(sudoku)
        sudokuDao.insertSudoku(entity)
    }

    override suspend fun getAllCompletedGames(): List<SudokuGame> {
        val entities = sudokuDao.getAllCompletedGames()
        return entities.map { sudokuMapper.entityToDomain(it) }
    }
}
