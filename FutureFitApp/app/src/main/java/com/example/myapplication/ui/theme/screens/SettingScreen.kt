package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.navigation.User

@Composable
fun SettingScreen(user: User){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Get ready to workout", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Account Information")

        Spacer(modifier = Modifier.height(16.dp)
            .drawBehind {
                drawLine(
                    strokeWidth = 7F,
                    color = Color.LightGray,
                    start = Offset(-650F, 0F),
                    end = Offset(650F, 0F)
                )
            }
        )

        Text(text = user.email)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "${user.firstName} ${user.lastName}")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Customization")

        Spacer(modifier = Modifier.height(16.dp)
            .drawBehind {
                drawLine(
                    strokeWidth = 7F,
                    color = Color.LightGray,
                    start = Offset(-650F, 0F),
                    end = Offset(650F, 0F)
                )
            }
        )
        var checkedd by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("☀️")
            Switch(
                checked = checkedd,
                onCheckedChange = { checked ->
                    checkedd = !checkedd
                }
            )
            Text("🌘")
        }

    }
}