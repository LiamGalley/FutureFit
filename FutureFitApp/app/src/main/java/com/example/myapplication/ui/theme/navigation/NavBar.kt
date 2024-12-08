package com.example.myapplication.ui.theme.navigation

import android.annotation.SuppressLint
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.data.ViewModels.GPTViewModel
import com.example.myapplication.data.Database.AnotherViewModel
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Workout
import com.example.myapplication.data.Entities.Exercise
import com.example.myapplication.ui.theme.screens.DisplayWorkouts
import com.example.myapplication.ui.theme.screens.HomeScreen
import com.example.myapplication.ui.theme.screens.LoginScreen
import com.example.myapplication.ui.theme.screens.SettingScreen
import com.example.myapplication.ui.theme.screens.WorkoutDetailsScreen
import com.example.myapplication.ui.theme.screens.WorkoutSelectionPage
import java.time.Instant

@SuppressLint("NewApi")
@Composable
fun NavigationContent(
    navController: NavHostController,
    dbViewModel: AnotherViewModel
) {

//    dbViewModel.upsertAccount(Account(firstName = "Alice",
//        lastName = "Johnson",
//        emailAddress = "alice.johnson@example.com", password = "works"))
    val currentInstant: Instant = Instant.now()
    val workout = Workout(
        name = "Morning Cardio",
        date = currentInstant.toEpochMilli(),
        duration = 30,
        intensity = "Medium",
        accountId = 1
    )
    dbViewModel.upsertWorkout(workout)
//    dbViewModel.upsertExercise(Exercise(name = "test", workoutId = 4))
//    dbViewModel.upsertExercise(Exercise(name ="test", workoutId = 4))

    var isRegistrationValid by remember { mutableStateOf(false)}
    var idUser by remember { mutableStateOf(Account("r","r","r","r", activityLevel = 0, age = 0, bodyFat = 0, height = 0.0, weight = 0.0))}
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
                    HomeScreen(navController = navController,dbViewModel, idUser, gptViewModel = gptViewModel)
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
                        navController.navigate("workoutDetails/${workout.workoutId}")
                    }
                }
            }

            composable("workoutDetails/{workoutId}") { backStackEntry ->
                val workoutName = backStackEntry.arguments?.getString("workoutId") ?: ""
                val id: Int = workoutName.toInt()
                val workout by dbViewModel.getWorkoutById(id).observeAsState()
                val selectedWorkout = workout


                selectedWorkout?.let {
                    WorkoutDetailsScreen(it, dbViewModel) {
                        navController.popBackStack()
                    }
                }

            }

            composable(route = WorkoutCreation.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WorkoutSelectionPage(navController = navController, idUser, dbViewModel, gptViewModel = gptViewModel)
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
                    SettingScreen(idUser)
                }
            }

        }
    }
}


