package com.example.sudoku

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.sudoku.ui.MainScreen
import com.example.sudoku.ui.theme.NavigationDrawerJetpackComposeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var darkThemeEnabled by rememberSaveable { mutableStateOf(false) }

            NavigationDrawerJetpackComposeTheme(darkTheme = darkThemeEnabled) {
                MainScreen(
                    darkThemeEnabled = darkThemeEnabled,
                    onToggleDarkTheme = { enabled -> darkThemeEnabled = enabled }
                )
            }
        }
    }
}
