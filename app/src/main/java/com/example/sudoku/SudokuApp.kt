package com.example.sudoku

import android.app.Application
import com.example.sudoku.di.databaseModule
import com.example.sudoku.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SudokuApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SudokuApp)
            modules(
                databaseModule,
                viewModelModule
            )
        }
    }
}
