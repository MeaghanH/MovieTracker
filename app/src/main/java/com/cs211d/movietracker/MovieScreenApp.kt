package com.cs211d.movietracker

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cs211d.movietracker.ui.EnterMovieScreen
import com.cs211d.movietracker.ui.HomeScreen
import com.cs211d.movietracker.ui.MovieRecScreen
import com.cs211d.movietracker.ui.MovieViewModel
import com.cs211d.movietracker.ui.RecViewModel

enum class MovieScreens(var icon : ImageVector) {
    MovieHome(Icons.Filled.Home),
    EnterMovie(Icons.Filled.Add),
    MovieRecommendation(Icons.Filled.Star)
}

@Composable
fun MovieScreenApp(
    movieViewModel: MovieViewModel = viewModel(
        factory = MovieViewModel.Factory
    ),
    recViewModel : RecViewModel = viewModel(
        factory = RecViewModel.Factory
    ),
    navController : NavHostController = rememberNavController()
) {

    Scaffold(
        bottomBar = { MovieNavBar(navController) }
    ) {
            innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MovieScreens.MovieHome.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MovieScreens.MovieHome.name) {
                HomeScreen(
                    onEnterMovieClick = { navController.navigate(MovieScreens.EnterMovie.name) },
                    onMovieRecClick = { navController.navigate(MovieScreens.MovieRecommendation.name)}
                )
            }
            composable(route = MovieScreens.EnterMovie.name) {
                EnterMovieScreen(
                    movieViewModel = movieViewModel
                )
            }
            composable(route = MovieScreens.MovieRecommendation.name) {
                MovieRecScreen(
                    movieViewModel = movieViewModel,
                    recViewModel = recViewModel
                )
            }

        }

        }

    }
@Composable
fun MovieNavBar(
    navController : NavController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        MovieScreens.entries.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute?.endsWith(screen.name) == true,
                onClick = {
                    if (currentRoute != screen.name) {
                        navController.navigate(screen.name) {
                            if (screen == MovieScreens.MovieHome) {
                                navController.popBackStack(MovieScreens.MovieHome.name, false)
                            }
                        }
                    }
                },
                icon = { Icon(screen.icon, contentDescription = null) }
            )
        }
    }
}