package com.example.myapplication.ui.theme.navigation

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.GPTViewModel
import com.example.myapplication.data.AnotherViewModel
import com.example.myapplication.ui.theme.screens.HomeScreen
import com.example.myapplication.ui.theme.screens.ProfileScreen
import com.example.myapplication.ui.theme.screens.tempProfilScreen
import kotlin.getValue

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector)
{
    object Home: NavDestination(title="Home",route="home_screen",icon= Icons.Default.Home)
    object Workouts: NavDestination(title="Workouts",route="workouts",icon= Icons.Default.List)
    object Other: NavDestination(title="Other",route="other",icon= Icons.Default.Info)
    object Settings: NavDestination(title="Settings",route="settings",icon= Icons.Default.Settings)
}

@Composable
fun NavigationContent(dbViewModel: AnotherViewModel) {
    val navController = rememberNavController()
    val items = listOf(
        com.example.myapplication.NavDestination.Home, com.example.myapplication.NavDestination.Workouts,
        com.example.myapplication.NavDestination.Other, com.example.myapplication.NavDestination.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(imageVector = screen.icon, contentDescription = null)
                        },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController,
            startDestination = "home_screen",
            modifier = Modifier.padding(innerPadding)){
            composable(route = "home_screen") {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val gptViewModel: GPTViewModel = viewModel()
                    HomeScreen(navController = navController, gptViewModel = gptViewModel)
                }
            }

            composable(route = com.example.myapplication.NavDestination.Workouts.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text="Workout", fontSize = 62.sp)
                }
            }

            composable(route = com.example.myapplication.NavDestination.Other.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text="Other", fontSize = 62.sp)
                }
            }

            composable(route = com.example.myapplication.NavDestination.Settings.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    tempProfilScreen(navController = navController, viewModel = dbViewModel)
                }
            }
        }
    }
}

