package com.example.sudoku

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.sudoku.ui.MainScreen
import com.example.sudoku.ui.theme.NavigationDrawerJetpackComposeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var darkThemeEnabled by rememberSaveable { mutableStateOf(false) }

            NavigationDrawerJetpackComposeTheme(darkTheme = darkThemeEnabled) {
                var onAppPaused: (() -> Unit)? by remember { mutableStateOf(null) }
                var onAppResumed: (() -> Unit)? by remember { mutableStateOf(null) }

                val lifecycleOwner = LocalLifecycleOwner.current

                DisposableEffect(lifecycleOwner) {
                    val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
                        when (event) {
                            Lifecycle.Event.ON_STOP -> onAppPaused?.invoke()
                            Lifecycle.Event.ON_START -> onAppResumed?.invoke()
                            else -> {}
                        }
                    }

                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }

                MainScreen(
                    darkThemeEnabled = darkThemeEnabled,
                    onToggleDarkTheme = { enabled -> darkThemeEnabled = enabled },
                    onAppPaused = { onAppPaused = it },
                    onAppResumed = { onAppResumed = it }
                )
            }
        }
    }
}
