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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.Database.AnotherViewModel
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.ui.theme.utils.AccountState

@Composable
fun ProfileScreen(
    userAccount: AccountState,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // PROFILE EDITOR
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
            items(userAccount.account){ account ->
                Row(modifier =  Modifier.fillMaxWidth()){
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = userAccount.firstName,
                            fontSize = 20.sp
                        )
                        Text(
                            text = userAccount.lastName,
                            fontSize = 16.sp
                        )
                        Text(
                            text = userAccount.emailAddress,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}