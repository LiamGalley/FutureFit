package com.example.myapplication.ui.theme.navigation

import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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
import com.example.myapplication.ui.theme.screens.ClickableContainer
import com.example.myapplication.ui.theme.screens.DisplayWorkouts
import com.example.myapplication.ui.theme.screens.HomeScreen
import com.example.myapplication.ui.theme.screens.LoginScreen
import com.example.myapplication.ui.theme.screens.ProfileScreen
import com.example.myapplication.ui.theme.screens.SettingScreen
import com.example.myapplication.ui.theme.screens.WorkoutDetailsScreen
import com.example.myapplication.ui.theme.screens.WorkoutSelectionPage
import com.example.myapplication.ui.theme.screens.tempProfilScreen
import com.example.myapplication.ui.theme.screens.workouts
import kotlin.getValue

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector)
{
    object Home: NavDestination(title="Home",route="home_screen",icon= Icons.Default.Home)
    object CreateWorkout: NavDestination(title="Create Workout",route="createworkout",icon= Icons.Default.Create)
    object Workouts: NavDestination(title="Workouts",route="workouts",icon= Icons.Default.List)
    object Other: NavDestination(title="Other",route="other",icon= Icons.Default.Info)
    object Settings: NavDestination(title="Settings",route="settings",icon= Icons.Default.Settings)
}

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: String
)

val sampleUsers = listOf(
    User(
        id = 1,
        firstName = "Alice",
        lastName = "Johnson",
        email = "alice.johnson@example.com",
        createdAt = "2024-01-05T08:30:00Z"
    ),
    User(
        id = 2,
        firstName = "Bob",
        lastName = "Smith",
        email = "bob.smith@example.com",
        createdAt = "2024-01-06T09:45:00Z"
    ),
    User(
        id = 3,
        firstName = "Charlie",
        lastName = "Lee",
        email = "charlie.lee@example.com",
        createdAt = "2024-01-07T10:15:00Z"
    ),
    User(
        id = 4,
        firstName = "Diana",
        lastName = "Garcia",
        email = "diana.garcia@example.com",
        createdAt = "2024-01-08T11:00:00Z"
    )
)

@Composable
fun NavigationContent(dbViewModel: AnotherViewModel) {
    val navController = rememberNavController()
    val items = listOf(
        NavDestination.Home, NavDestination.CreateWorkout ,NavDestination.Workouts, NavDestination.Settings
    )
    var isRegistrationValid by remember { mutableStateOf(false)}
    var idUser by remember { mutableStateOf(sampleUsers[0])}

    Scaffold(
        bottomBar = {
            if(isRegistrationValid) {
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
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController,
            startDestination = if (isRegistrationValid) NavDestination.Home.route else "register_screen",
            modifier = Modifier.padding(innerPadding)){

            composable(route = "register_screen") {
                LoginScreen(
                    onRegistrationSuccess = {
                        isRegistrationValid = true
                        idUser = it
                        navController.navigate(NavDestination.Home.route) {
                            popUpTo("register_screen") { inclusive = true }
                        }
                    }
                )
            }

            composable(route = NavDestination.Home.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val gptViewModel: GPTViewModel = viewModel()
                    HomeScreen(navController = navController, gptViewModel = gptViewModel)
                }
            }

            composable(route = NavDestination.Workouts.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DisplayWorkouts({ workout ->
                        navController.navigate("workoutDetails/${workout.name}")
                    })
                }
            }

            composable("workoutDetails/{name}") { backStackEntry ->
                val workoutName = backStackEntry.arguments?.getString("name") ?: ""
                val selectedWorkout = workouts.find { it.name == workoutName }
                if (selectedWorkout != null) {
                    WorkoutDetailsScreen(selectedWorkout) { navController.popBackStack() }
                }
            }

            composable(route = NavDestination.CreateWorkout.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WorkoutSelectionPage()
                }
            }

            composable(route = NavDestination.Other.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Other", fontSize = 30.sp)
                }
            }

            composable(route = NavDestination.Settings.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SettingScreen(idUser)
                }
            }

        }
    }
}


