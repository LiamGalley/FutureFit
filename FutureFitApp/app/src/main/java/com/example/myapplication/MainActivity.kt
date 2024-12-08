package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.myapplication.data.Database.AnotherViewModel
import com.example.myapplication.data.Database.AppDatabase
import com.example.myapplication.data.Database.Repository
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.navigation.NavigationContent

class MainActivity : ComponentActivity() {
    private val db by lazy { Room.databaseBuilder(applicationContext, AppDatabase::class.java,
        "final.db").build() }


    private val dbViewModel by viewModels<AnotherViewModel> (
        factoryProducer = { object : ViewModelProvider.Factory{ override fun <T : ViewModel>
                create(modelClass: Class<T>): T { return AnotherViewModel(Repository(db)) as T } } }
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
    dbViewModel: AnotherViewModel
){
    val darkMode = false



    MyApplicationTheme(darkMode) {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavigationContent(
                navController,
                dbViewModel = dbViewModel
            )
        }
    }
}

