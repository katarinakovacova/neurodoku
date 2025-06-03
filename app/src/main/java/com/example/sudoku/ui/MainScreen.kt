package com.example.sudoku.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.ui.navigation.NavigationItem
import com.example.sudoku.ui.navigation.Screens
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sudoku.ui.navigation.NavigationBarBody
import com.example.sudoku.ui.navigation.NavigationBarHeader
import com.example.sudoku.ui.navigation.SetUpNavigationGraph

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
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
            SetUpNavigationGraph(navController = navController, innerPadding = innerPadding)
        }
    }
}
