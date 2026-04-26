package com.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animeapp.ui.navigation.Screen
import com.animeapp.ui.screens.AnimeDetailScreen
import com.animeapp.ui.screens.HomeScreen
import com.animeapp.ui.screens.SearchScreen
import com.animeapp.ui.theme.AnimeAppTheme
import com.animeapp.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimeApp()
                }
            }
        }
    }
}

@Composable
fun AnimeApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                animeList = uiState.animeList,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onAnimeClick = { animeId ->
                    viewModel.selectAnime(animeId)
                    navController.navigate(Screen.AnimeDetail.createRoute(animeId))
                },
                onRefresh = {
                    viewModel.loadTopAnime()
                }
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                animeList = uiState.animeList,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onSearchQueryChange = { query ->
                    viewModel.searchAnime(query)
                },
                onAnimeClick = { animeId ->
                    viewModel.selectAnime(animeId)
                    navController.navigate(Screen.AnimeDetail.createRoute(animeId))
                }
            )
        }
        
        composable(Screen.News.route) {
            // TODO: Implement News Screen
            HomeScreen(
                animeList = emptyList(),
                isLoading = false,
                error = "News feature coming soon!",
                onAnimeClick = {},
                onRefresh = {}
            )
        }
        
        composable(Screen.Profile.route) {
            // TODO: Implement Profile Screen
            HomeScreen(
                animeList = emptyList(),
                isLoading = false,
                error = "Profile feature coming soon!",
                onAnimeClick = {},
                onRefresh = {}
            )
        }
        
        composable(
            route = Screen.AnimeDetail.route,
            arguments = listOf()
        ) {
            AnimeDetailScreen(
                anime = uiState.selectedAnime,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onBackClick = {
                    viewModel.clearSelectedAnime()
                    navController.popBackStack()
                }
            )
        }
    }
}
