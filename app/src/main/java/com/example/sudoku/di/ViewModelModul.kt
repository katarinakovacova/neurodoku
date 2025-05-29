package com.example.sudoku.di

import com.example.sudoku.domain.usecase.*
import com.example.sudoku.ui.viewmodel.StatisticsViewModel
import com.example.sudoku.ui.viewmodel.SudokuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // UseCases
    factory { GenerateSudokuUseCase() }
    factory { MaskSudokuUseCase() }
    factory { GenerateNewSudokuGameUseCase(get(), get()) }
    factory { LoadSudokuGameUseCase(get()) }
    factory { SaveSudokuGameUseCase(get()) }
    factory { GetCompletedSudokuGamesUseCase(get()) }

    // ViewModel
    viewModel {
        SudokuViewModel(
            generateNewSudokuGameUseCase = get(),
            loadSudokuGameUseCase = get(),
            saveSudokuGameUseCase = get()
        )
    }

    viewModel {
        StatisticsViewModel(
            getCompletedSudokuGamesUseCase = get()
        )
    }
}

