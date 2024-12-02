package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myapplication.data.AccountViewModel
import com.example.myapplication.data.AnotherViewModel
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Entities.Account
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
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "account.db"
        ).build()
    }

    private val viewModel by viewModels<AnotherViewModel> (
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

    private val gptViewModel: GPTViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var email by remember {
                        mutableStateOf("")
                    }
                    var firstName by remember {
                        mutableStateOf("")
                    }
                    var lastName by remember {
                        mutableStateOf("")
                    }
                    val account = Account(
                        emailAddress = email,
                        firstName = firstName,
                        lastName = lastName
                    )
                    Column(Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        Button(onClick = {
                                viewModel.upsertAccount(account)
                            }){
                            Text(text = "register")
                        }
                        TextField(value = email, onValueChange = {email = it}, placeholder = { Text(
                            text = "email"
                        )})
                        TextField(value = firstName, onValueChange = {firstName = it}, placeholder = { Text(
                            text = "firstName"
                        )})
                        TextField(value = lastName, onValueChange = {lastName = it}, placeholder = { Text(
                            text = "lastName"
                        )})
                    }





                    //GPTResponseScreen(gptViewModel)
                }
                //NavigationContent()
            }
        }
    }
}


@Composable
fun GPTResponseScreen(viewModel: GPTViewModel) {
    val userQuery = remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = userQuery.value,
                onValueChange = { userQuery.value = it },
                label = { Text("Enter your query") }
            )
            Button(
                onClick = {
                    viewModel.setQuery(userQuery.value)
                    viewModel.fetchGPTResponse()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Send")
            }
            Text(
                text = viewModel.gptResponse,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


