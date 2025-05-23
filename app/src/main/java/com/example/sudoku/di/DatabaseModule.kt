package com.example.sudoku.di

import androidx.room.Room
import com.example.sudoku.data.local.SudokuDatabase
import org.koin.android.ext.koin.androidContext

import com.example.sudoku.data.local.SudokuDao
import com.example.sudoku.data.mapper.SudokuMapper
import com.example.sudoku.data.repository.SudokuRepositoryImpl
import com.example.sudoku.data.repository.SudokuRepository

import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            SudokuDatabase::class.java,
            "sudoku_db"
        ).build()
    }

    single<SudokuDao> {
        get<SudokuDatabase>().sudokuDao()
    }

    single<SudokuMapper> {
        SudokuMapper()
    }

    single<SudokuRepository> {
        SudokuRepositoryImpl(get(), get())
    }
}
