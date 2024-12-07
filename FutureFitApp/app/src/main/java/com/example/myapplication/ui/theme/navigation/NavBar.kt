package com.example.myapplication.ui.theme.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.data.ViewModels.DatabaseViewModel
import com.example.myapplication.data.ViewModels.GPTViewModel
import com.example.myapplication.data.ViewModels.HomeViewModel
import com.example.myapplication.data.ViewModels.ProfileViewModel
import com.example.myapplication.data.ViewModels.SettingsViewModel
import com.example.myapplication.data.ViewModels.TrainingViewModel
import com.example.myapplication.ui.theme.screens.DisplayWorkouts
import com.example.myapplication.ui.theme.screens.HomeScreen
import com.example.myapplication.ui.theme.screens.LoginScreen
import com.example.myapplication.ui.theme.screens.SettingScreen
import com.example.myapplication.ui.theme.screens.WorkoutDetailsScreen
import com.example.myapplication.ui.theme.screens.WorkoutSelectionPage
import com.example.myapplication.ui.theme.screens.workouts

//#region SAMPLE DATA (DELETE WHEN IMPLEMENTING DATABASE)
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
//#endregion

@Composable
fun NavigationContent(
    navController: NavHostController,
    dbViewModel: DatabaseViewModel,
    homeViewModel: HomeViewModel,
    profileViewModel: ProfileViewModel,
    settingsViewModel: SettingsViewModel,
    trainingViewModel: TrainingViewModel
) {
    var isRegistrationValid by remember { mutableStateOf(false)}
    var idUser by remember { mutableStateOf(sampleUsers[0])}

    Scaffold(
        bottomBar = {
            if(isRegistrationValid) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    appScreens.forEach { screen ->
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
        NavHost(
            navController = navController,
            startDestination = if (isRegistrationValid) Home.route else "register_screen",
            modifier = Modifier.padding(innerPadding)){

            composable(route = "register_screen") {
                LoginScreen(
                    onRegistrationSuccess = {
                        isRegistrationValid = true
                        idUser = it
                        navController.navigate(Home.route) {
                            popUpTo("register_screen") { inclusive = true }
                        }
                    }
                )
            }

            composable(route = Home.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val gptViewModel: GPTViewModel = viewModel()
                    HomeScreen(navController = navController, gptViewModel = gptViewModel)
                }
            }

            composable(route = WorkoutHistory.route) {
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

            composable(route = WorkoutCreation.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WorkoutSelectionPage()
                }
            }

            composable(route = Other.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Other", fontSize = 30.sp)
                }
            }

            composable(route = Settings.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SettingScreen(
                        idUser,
                        { settingsViewModel.toggleTheme() },
                        { settingsViewModel.toggleLargeFontSize() }
                    )
                }
            }

        }
    }
}


