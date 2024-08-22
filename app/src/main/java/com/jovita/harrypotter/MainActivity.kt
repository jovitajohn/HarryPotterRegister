package com.jovita.harrypotter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
import com.jovita.myapplication.R


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                MainActivityContent(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityContent(navController: NavHostController) {
    //val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val appName = stringResource(id = R.string.harry_potter_register)
    val detailScreenName = stringResource(id = R.string.detail_screen_title)
    //Setting Top bar
    var topBarTitle by remember { mutableStateOf(appName) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
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
                    topBarTitle = appName
                    ListingScreen(navController = navController, viewModel = viewModel)
                }
                //Listing screen
                composable("detail/{characterId}") { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getString("characterId")
                    characterId?.let {
                        topBarTitle =
                            viewModel.getCharacterName(characterId).toString()
                    } ?: run { topBarTitle = detailScreenName }
                    DetailScreen(characterId = characterId, viewModel = viewModel)
                }
            }
        }
    }
}
