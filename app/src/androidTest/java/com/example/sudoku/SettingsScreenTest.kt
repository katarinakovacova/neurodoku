package com.example.sudoku

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.example.sudoku.ui.screens.SettingsScreen
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDarkModeSwitch_toggleChangesState() {
        var isDarkMode = false

        composeTestRule.setContent {
            SettingsScreen(
                innerPadding = PaddingValues(0.dp),
                isDarkTheme = isDarkMode,
                onToggleDarkTheme = { isDarkMode = it }
            )
        }

        composeTestRule.onNodeWithTag("settings_title")
            .assertExists()
            .assertTextEquals("Settings")

        composeTestRule.onNodeWithTag("dark_mode_label")
            .assertExists()
            .assertTextEquals("Dark Mode")

        composeTestRule.onNodeWithTag("dark_mode_switch")
            .assertExists()
            .assertIsOff()

        composeTestRule.onNodeWithTag("dark_mode_switch")
            .performClick()

        assertTrue(isDarkMode)
    }
}
