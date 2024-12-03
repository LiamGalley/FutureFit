package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myapplication.data.AnotherViewModel
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Repository
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.navigation.NavigationContent

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector)
{
    object Home: NavDestination(title="Home",route="home_screen",icon= Icons.Default.Home)
    object Workouts: NavDestination(title="Workouts",route="workouts",icon= Icons.Default.List)
    object Other: NavDestination(title="Other",route="other",icon= Icons.Default.Info)
    object Settings: NavDestination(title="Settings",route="settings",icon= Icons.Default.Settings)
}


class MainActivity : ComponentActivity() {

    // Instanciate the db
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "account.db"
        ).build()
    }

    private val dbViewModel by viewModels<AnotherViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AnotherViewModel(Repository(db)) as T
                }
            }
        }
    )
    /*
    private val viewModel by viewModels<AccountViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return AccountViewModel(db.dao) as T
                }
            }
        }
    )
    */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationContent(dbViewModel = dbViewModel)
                }
            }
        }
    }
}


