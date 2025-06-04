package com.example.sudoku.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.ui.navigation.NavigationItem
import com.example.sudoku.ui.navigation.NavigationBarBody
import com.example.sudoku.ui.navigation.NavigationBarHeader
import com.example.sudoku.ui.navigation.Screens
import com.example.sudoku.ui.navigation.SetUpNavigationGraph
import com.example.sudoku.ui.viewmodel.SudokuViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    darkThemeEnabled: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    onAppPaused: ((() -> Unit) -> Unit),
    onAppResumed: ((() -> Unit) -> Unit)
) {
    val viewModel: SudokuViewModel = org.koin.androidx.compose.getViewModel()

    LaunchedEffect(Unit) {
        onAppPaused { viewModel.stopTimer() }
        onAppResumed { viewModel.startTimer() }
    }
    val items = remember {
        listOf(
            NavigationItem(
                title = "Sudoku",
                route = Screens.Sudoku.route,
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.Favorite,
            ),
            NavigationItem(
                title = "Statistics",
                route = Screens.Statistics.route,
                selectedIcon = Icons.Filled.Info,
                unselectedIcon = Icons.Outlined.Info,
            ),
            NavigationItem(
                title = "Blog",
                route = Screens.Blog.route,
                selectedIcon = Icons.Filled.Star,
                unselectedIcon = Icons.Outlined.Star,
            ),
            NavigationItem(
                title = "Settings",
                route = Screens.Settings.route,
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
            ),
        )
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topBarTitle = when {
        currentRoute == Screens.Blog.route -> "Blog"
        currentRoute?.startsWith("blogDetail") == true -> "Blog Detail"
        else -> items.firstOrNull { it.route == currentRoute }?.title ?: items[0].title
    }

    val showBackButton = currentRoute?.startsWith("blogDetail") == true

    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationBarHeader()
                Spacer(modifier = Modifier.height(8.dp))
                NavigationBarBody(items = items, currentRoute = currentRoute) { navigationItem ->
                    if (navigationItem.route == "share") {
                        Toast.makeText(context, "Share Clicked", Toast.LENGTH_LONG).show()
                    } else {
                        navController.navigate(navigationItem.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    scope.launch { drawerState.close() }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = topBarTitle) },
                    navigationIcon = {
                        if (showBackButton) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        } else {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            SetUpNavigationGraph(
                navController = navController,
                innerPadding = innerPadding,
                darkThemeEnabled = darkThemeEnabled,
                onToggleDarkTheme = onToggleDarkTheme
            )
        }
    }
}
