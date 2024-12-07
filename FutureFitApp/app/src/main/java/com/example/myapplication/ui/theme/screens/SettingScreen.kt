package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.navigation.User
import java.sql.Time

@Composable
fun SettingScreen(
    user: User,
    toggleTheme: () -> Unit,
    toggleFontSize: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var darkModeButton = rememberSaveable{ mutableStateOf(false) }
        var fontSizeButton = rememberSaveable{ mutableStateOf(false) }
        var measurementSystemButton = rememberSaveable{ mutableStateOf(false) }

        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Account Information",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(8.dp))

        AccountInfoRow(label = "Account ID:", value = user.id.toString())
        AccountInfoRow(label = "Account Email:", value = user.email)
        AccountInfoRow(label = "Account Name:", value = "${user.firstName} + ${user.lastName}")

        Spacer(modifier = Modifier.height(16.dp))
        DrawLine()

        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(8.dp))

        AccountInfoRow("Weight", value = "170 LBs")
        AccountInfoRow("Height", value = "185 CMs")
        AccountInfoRow("Age", value = "24")
        AccountInfoRow("Body Fat", value = "15 %")
        AccountInfoRow("Activity Level", value = "3")
        AccountInfoRow("Measurement System", value = "Metric")


        Spacer(modifier = Modifier.height(16.dp))
        DrawLine()

        Text(
            text = "Account Appearance",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("☀️")
            Switch(checked = darkModeButton.value, onCheckedChange = {
                darkModeButton.value = !darkModeButton.value
                toggleTheme()}
            )
            Text("🌘")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("\uD83E\uDD0F")
            Switch(checked = fontSizeButton.value, onCheckedChange = {
                fontSizeButton.value = !fontSizeButton.value
                toggleFontSize()})
            Text("\uD83D\uDD90")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("\uD83E\uDD85")
            Switch(checked = measurementSystemButton.value, onCheckedChange = {
                measurementSystemButton.value = !measurementSystemButton.value
                toggleTheme()})
            Text("\uD83E\uDED6")
        }

        Spacer(modifier = Modifier.height(16.dp))
        DrawLine()

        Text(
            text = "Help & Support",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(8.dp))

        AccountInfoRow("Help Desk", value = "123-456-7890")
        AccountInfoRow("LinkedIn Support", value = "LinkedIn.com/FutureFitSupport")
        AccountInfoRow("Website Chatline", value = "FutureFit.com/SupportChat")
    }
}

@Composable
fun DrawLine(){
    Spacer(modifier = Modifier.height(24.dp)
        .drawBehind {
            drawLine(
                strokeWidth = 7F,
                color = Color.LightGray,
                start = Offset(-650F, 0F),
                end = Offset(650F, 0F)
            )
        }
    )
}


@Composable
fun AccountInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End
        )
    }
}