package com.example.myapplication

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import com.example.myapplication.ui.theme.navigation.NavigationContent

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector)
{
    object Home: NavDestination(title="Home",route="home_screen",icon= Icons.Default.Home)
    object Workouts: NavDestination(title="Workouts",route="workouts",icon= Icons.Default.List)
    object Other: NavDestination(title="Other",route="other",icon= Icons.Default.Info)
    object Settings: NavDestination(title="Settings",route="settings",icon= Icons.Default.Settings)
}


class MainActivity : ComponentActivity() {
    private val gptViewModel: GPTViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GPTResponseScreen(gptViewModel)
                }
                NavigationContent()
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


