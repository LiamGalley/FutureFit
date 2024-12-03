package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.AccountEvent
import com.example.myapplication.data.AccountState
import com.example.myapplication.data.AnotherViewModel
import com.example.myapplication.data.Entities.Account

@Composable
fun ProfileScreen(
    state: AccountState,
    onEvent: (AccountEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(AccountEvent.RegisterAccount)
                }
            )
            {

            }
        }
    ){ padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(state.account){ account ->
                Row(modifier =  Modifier.fillMaxWidth()){
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = account.firstName,
                            fontSize = 20.sp
                        )
                        Text(
                            text = account.lastName,
                            fontSize = 16.sp
                        )
                        Text(
                            text = account.emailAddress,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun tempProfilScreen(navController: NavController, viewModel: AnotherViewModel) {
    val email = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }

    val account = Account(
        emailAddress = email.value,
        firstName = firstName.value,
        lastName = lastName.value
    )

    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = { viewModel.upsertAccount(account) }) {
            Text("Register")
        }

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            placeholder = { Text("Email") }
        )

        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            placeholder = { Text("First Name") }
        )

        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            placeholder = { Text("Last Name") }
        )
    }
}
