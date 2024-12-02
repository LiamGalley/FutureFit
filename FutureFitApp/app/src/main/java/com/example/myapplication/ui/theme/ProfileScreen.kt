package com.example.myapplication.ui.theme

import android.security.identity.AccessControlProfile
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.AccountEvent
import com.example.myapplication.data.AccountState
import io.ktor.events.Events

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