package com

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jovita.harrypotter.MainActivityContent
import com.jovita.harrypotter.ui.theme.MyApplicationTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topBarDisplaysCorrectTitleOnMainScreen() {
        composeTestRule.setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                MainActivityContent(navController = navController)
            }
        }

        // Check if the TopAppBar text is "Harry Potter Register" on the main screen
        composeTestRule.onNodeWithText("Harry Potter Register")
            .assertIsDisplayed()
    }

    @Test
    fun topBarDisplaysCorrectTitleOnDetailScreen() {
        composeTestRule.setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                // Simulate navigation to the detail screen
                navController.navigate("detail/{characterId}")
                MainActivityContent(navController = navController)
            }
        }

        // Check if the TopAppBar text is "Character Details" on the detail screen
        composeTestRule.onNodeWithText("Character Details")
            .assertIsDisplayed()
    }
}