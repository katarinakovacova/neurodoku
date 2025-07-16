package com.example.sudoku

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SudokuScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun newGameAndSetNumber() {
        composeTestRule
            .onNodeWithText("New", substring = true)
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("LevelButton_HARD").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag("LevelButton_HARD")
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("sudokuGrid").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag("sudokuGrid")
            .assertIsDisplayed()

        val row = 0
        val col = 0

        composeTestRule
            .onNodeWithTag("cell_${row}_$col")
            .performClick()

        composeTestRule
            .onNodeWithTag("num5")
            .performClick()

        composeTestRule
            .onNodeWithTag("cell_${row}_$col")
            .assertTextEquals("5")
    }
}
