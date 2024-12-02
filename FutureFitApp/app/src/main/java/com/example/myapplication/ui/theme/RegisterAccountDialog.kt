package com.example.myapplication.ui.theme

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.AccountEvent
import com.example.myapplication.data.AccountState

@Composable
fun RegisterAccount(
    state: AccountState,
    onEvent: (AccountEvent) -> Unit,
    modifier: Modifier = Modifier
){
  AlertDialog(
      modifier = modifier,
      onDismissRequest = {/*TODO*/ },
      title = { Text(text = "Register you account")},
      text = {
          Column(
              verticalArrangement = Arrangement.spacedBy(8.dp)
          )
          {
              TextField(
                  value = state.emailAddress,
                  onValueChange = {
                      onEvent(AccountEvent.SetEmailAddress(it))
                  },
                  placeholder = {
                      Text(text = "Email address")
                  }
              )
              TextField(
                  value = state.firstName,
                  onValueChange = {
                      onEvent(AccountEvent.SetFirstName(it))
                  },
                  placeholder = {
                      Text(text = "First name")
                  }
              )
              TextField(
                  value = state.lastName,
                  onValueChange = {
                      onEvent(AccountEvent.SetLastName(it))
                  },
                  placeholder = {
                      Text(text = "Last name")
                  }
              )
          }
      },
      buttons = {
          Box(
              modifier = Modifier.fillMaxWidth(),
              contentAlignment = Alignment.CenterEnd
              ){
              Button(onClick = {
                  onEvent(AccountEvent.RegisterAccount)
              }){
                  Text(text = "Register")
              }
          }
      }

  )
}