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
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.myapplication.data.Database.DatabaseViewModel
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Workout
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
import java.time.Instant

//#region SAMPLE DATA (DELETE WHEN IMPLEMENTING DATABASE)
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val height: Double,
    val weight: Double,
    val age: Int,
    val bodyFat: Double,
    val activityLevel: Int,
    val metricSystem: Boolean,
)

val sampleUsers = listOf(
    User(
        id = 1,
        firstName = "Bob",
        lastName = "Smith",
        email = "bob.smith@example.com",
        height = 185.0,
        weight = 170.5,
        age = 24,
        bodyFat = 15.2,
        activityLevel = 3,
        metricSystem = true
    ),
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
    var idUser by remember { mutableStateOf(Account("r","r","r","r",
        activityLevel = 0, age = 0, bodyFat = 0, height = 0.0, weight = 0.0))}
    val gptViewModel: GPTViewModel = viewModel()

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
                LoginScreen(dbViewModel,
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
                    HomeScreen(navController = navController, dbViewModel, idUser,
                        gptViewModel = gptViewModel)
                }
            }

            composable(route = WorkoutHistory.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val workoutList by dbViewModel.getWorkoutByAccountId(idUser.id).observeAsState(emptyList())

                    DisplayWorkouts(workoutList) { workout ->
                        navController.navigate("workoutDetails/${workout.workoutId}") }
                }
            }

            composable("workoutDetails/{workoutId}") { backStackEntry ->
                val workoutName = backStackEntry.arguments?.getString("workoutId") ?: ""
                val id: Int = workoutName.toInt()
                val workout by dbViewModel.getWorkoutById(id).observeAsState()
                val selectedWorkout = workout


                selectedWorkout?.let {
                    WorkoutDetailsScreen(it, dbViewModel) {
                        navController.popBackStack() } }
            }

            composable(route = WorkoutCreation.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WorkoutSelectionPage(
                        navController,
                        idUser,
                        dbViewModel,
                        gptViewModel,
                        profileViewModel.height,
                        profileViewModel.weight,
                        profileViewModel.age,
                        profileViewModel.bodyFat,
                        profileViewModel.activityLevel,
                        { profileViewModel.initializeFromDb(idUser) }
                    )
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
                        profileViewModel.userId,
                        profileViewModel.userName,
                        profileViewModel.userEmail,
                        settingsViewModel.height,
                        settingsViewModel.weight,
                        settingsViewModel.darkTheme,
                        settingsViewModel.metricSystem,
                        settingsViewModel.largeFontSize,
                        profileViewModel.age,
                        profileViewModel.bodyFat,
                        profileViewModel.activityLevel,
                        { settingsViewModel.initializeFromDb(idUser) },
                        { settingsViewModel.toggleTheme() },
                        { settingsViewModel.toggleLargeFontSize() },
                        { settingsViewModel.toggleMeasurementSystem() }
                    )
                }
            }

        }
    }
}


