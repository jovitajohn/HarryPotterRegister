package com.jovita.harrypotter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jovita.harrypotter.ui.screen.DetailScreen
import com.jovita.harrypotter.ui.screen.ListingScreen
import com.jovita.harrypotter.ui.theme.AppThemeWithBackground
import com.jovita.harrypotter.ui.theme.Griffindor
import com.jovita.harrypotter.ui.theme.MyApplicationTheme
import com.jovita.harrypotter.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                //Setting Topbar
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = when (currentRoute) {
                                        "main" -> "Harry Potter Characters"
                                        "detail/{characterName}" -> "Character Details"
                                        else -> "Harry Potter App"
                                    },
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Griffindor
                            )
                        )
                    }
                ) { paddingValues ->
                    AppThemeWithBackground {
                        NavHost(
                            navController = navController,
                            startDestination = "main",
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            //Main screen
                            composable("main") {
                                val test: String
                                ListingScreen(navController = navController, viewModel = viewModel)
                            }
                            //Listing screen
                            composable("detail/{characterId}") { backStackEntry ->
                                val characterId = backStackEntry.arguments?.getString("characterId")
                                DetailScreen(characterId = characterId, viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
