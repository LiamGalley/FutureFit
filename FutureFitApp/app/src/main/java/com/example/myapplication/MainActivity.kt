package com.example.myapplication

import FutureFitTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.myapplication.data.DataStores.DataStoreManager
import com.example.myapplication.data.Database.AppDatabase
import com.example.myapplication.data.Database.Repository
import com.example.myapplication.data.ViewModels.DatabaseViewModel
import com.example.myapplication.data.ViewModels.HomeViewModel
import com.example.myapplication.data.ViewModels.ProfileViewModel
import com.example.myapplication.data.ViewModels.SettingsViewModel
import com.example.myapplication.data.ViewModels.TrainingViewModel
import com.example.myapplication.ui.theme.navigation.NavigationContent

class MainActivity : ComponentActivity() {
    private val db by lazy { Room.databaseBuilder(applicationContext, AppDatabase::class.java,
        "account.db").build() }

    private val dbViewModel by viewModels<DatabaseViewModel> (
        factoryProducer = { object : ViewModelProvider.Factory{ override fun <T : ViewModel>
                create(modelClass: Class<T>): T { return DatabaseViewModel(Repository(db)) as T } } }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutureFitApp(dbViewModel)
        }
    }
}

@Composable
fun FutureFitApp(
    dbViewModel: DatabaseViewModel
){
    val homeViewModel = HomeViewModel(DataStoreManager(LocalContext.current))
    val profileViewModel = ProfileViewModel(DataStoreManager(LocalContext.current))
    val settingsViewModel = SettingsViewModel(DataStoreManager(LocalContext.current))
    val trainingViewModel = TrainingViewModel(DataStoreManager(LocalContext.current))

    val darkTheme by settingsViewModel.darkTheme.collectAsState()
    val largeFont by settingsViewModel.largeFontSize.collectAsState()

    FutureFitTheme(
        darkTheme,
        largeFont
    ) {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavigationContent(
                navController,
                dbViewModel = dbViewModel,
                homeViewModel,
                profileViewModel,
                settingsViewModel,
                trainingViewModel
            )
        }
    }
}

