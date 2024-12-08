package com.example.myapplication.ui.theme.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface AppDestinations {
    val title: String
    val route: String
    val icon: ImageVector
}

data object Home: AppDestinations {
    override val title = "Home"
    override val route = "home_screen"
    override val icon = Icons.Default.Home
}

data object WorkoutHistory: AppDestinations {
    override val title = "Workouts"
    override val route = "workouts"
    override val icon = Icons.Default.List}

data object Other: AppDestinations {
    override val title = "Other"
    override val route = "other"
    override val icon = Icons.Default.Info}

data object Settings: AppDestinations {
    override val title = "Settings"
    override val route = "settings"
    override val icon = Icons.Default.Settings}

data object WorkoutCreation: AppDestinations {
    override val title = "Builder"
    override val route = "create_workouts"
    override val icon = Icons.Default.Create}

val appScreens = listOf(
    Home, WorkoutHistory, WorkoutCreation, Settings
)