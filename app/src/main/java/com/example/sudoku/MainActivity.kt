package com.example.sudoku

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.sudoku.ui.MainScreen
import com.example.sudoku.ui.theme.NavigationDrawerJetpackComposeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationDrawerJetpackComposeTheme {
                MainScreen()
            }
        }
    }
}
