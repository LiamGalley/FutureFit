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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SettingScreen(
    userId: StateFlow<Int>,
    userName: StateFlow<String>,
    userEmail: StateFlow<String>,
    height: StateFlow<Double>,
    weight: StateFlow<Double>,
    darkTheme: StateFlow<Boolean>,
    metricSystem: StateFlow<Boolean>,
    largeFontSize: StateFlow<Boolean>,
    age: Int,
    bodyFat: Double,
    activityLevel: Int,
    toggleTheme: () -> Unit,
    toggleFontSize: () -> Unit,
    toggleMeasurementSystem: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title()

        AccountInformation(userId, userName, userEmail)

        if (metricSystem.collectAsState().value){
            PersonalInformation(height, weight, metricSystem.collectAsState().value,
                "Metric", age, bodyFat, activityLevel)
        } else{
            PersonalInformation(height, weight, metricSystem.collectAsState().value,
                "Imperial", age, bodyFat, activityLevel)
        }

        AccountAppearance()
        AccountAppearanceSwitches(darkTheme.collectAsState().value, toggleTheme,
            "☀", "\uD83C\uDF18")
        AccountAppearanceSwitches(largeFontSize.collectAsState().value, toggleFontSize,
            "\uD83E\uDD0F", "\uD83D\uDD90")
        AccountAppearanceSwitches(metricSystem.collectAsState().value, toggleMeasurementSystem,
            "\uD83E\uDD85", "\uD83E\uDED6")

        HelpAndSupport()
    }
}

@Composable
fun Title(){
    Text(
        text = "Settings",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(16.dp))
}

@Composable
fun AccountInformation(
    userId: StateFlow<Int>,
    userName: StateFlow<String>,
    userEmail: StateFlow<String>,
){
    Text(
        text = "Account Information",
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(8.dp))

    AccountInfoRow(label = "Account ID:", value = userId.collectAsState().value.toString())
    AccountInfoRow(label = "Account Name:", value = userName.collectAsState().value)
    AccountInfoRow(label = "Account Email:", value = userEmail.collectAsState().value)

    Spacer(modifier = Modifier.height(16.dp))
    DrawLine()
}

@Composable
fun PersonalInformation(
    height: StateFlow<Double>,
    weight: StateFlow<Double>,
    metricSystem: Boolean,
    metricSystemText: String,
    age: Int,
    bodyFat: Double,
    activityLevel: Int,
){
    var weightMeasurement = ""
    var heightMeasurement = ""

    Text(
        text = "Personal Information",
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(8.dp))

    if (metricSystem){
        weightMeasurement = "kgs"
        heightMeasurement = "cms"
    } else {
        weightMeasurement = "Lbs"
        heightMeasurement = "ft"
    }

    AccountInfoRow("Weight", value = "${weight.collectAsState().value} $weightMeasurement")
    AccountInfoRow("Height", value = "${height.collectAsState().value} $heightMeasurement")
    AccountInfoRow("Age", value = age.toString())
    AccountInfoRow("Body Fat", value = "$bodyFat %")
    AccountInfoRow("Activity Level", value = activityLevel.toString())
    AccountInfoRow("Measurement System", value = metricSystemText)


    Spacer(modifier = Modifier.height(16.dp))
    DrawLine()

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun AccountAppearance(){
    Text(
        text = "Account Appearance",
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HelpAndSupport(){
    DrawLine()

    Text(
        text = "Help & Support",
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(8.dp))

    AccountInfoRow("Help Desk", value = "123-456-7890")
    AccountInfoRow("LinkedIn Support", value = "LinkedIn.com/FutureFitSupport")
    AccountInfoRow("Website Chatline", value = "FutureFit.com/SupportChat")
}

@Composable
fun AccountAppearanceSwitches(
    isSwitchChecked: Boolean, toggle: () -> Unit,
    firstText: String, secondText: String){
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(firstText)
        Switch(checked = isSwitchChecked, onCheckedChange = { toggle()})
        Text(secondText)
    }

    Spacer(modifier = Modifier.height(8.dp))
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