package com.example.myapplication.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun WorkoutSelectionPage(
    height: StateFlow<Double>,
    weight: StateFlow<Double>,
    age: Int,
    bodyFat: Double,
    activityLevel: Int
) {
    var weight by remember { mutableStateOf(weight.value.toString()) }
    var height by remember { mutableStateOf(height.value.toString()) }
    var age by remember { mutableStateOf(age.toString()) }
    var bodyFat by remember { mutableStateOf(bodyFat.toString()) }
    var activityLevel by remember { mutableStateOf(activityLevel.toString()) }
    var selectedMuscleGroup by remember { mutableStateOf("Whole Body") }

    val muscleGroups = listOf("Chest", "Back", "Legs", "Arms", "Shoulders", "Abs", "Whole Body")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Workout Program Generator",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp))

        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start))

        // Weight input
        TextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Height input
        TextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Age input
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age (years)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Body input
        TextField(
            value = bodyFat,
            onValueChange = { bodyFat = it },
            label = { Text("Body Fat (percentage)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Activity Level input
        TextField(
            value = activityLevel,
            onValueChange = { activityLevel = it },
            label = { Text("Activity Level (1-5)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Muscle Groups",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start))

        // Display the muscle group options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            muscleGroups.forEach { muscle ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = muscle == selectedMuscleGroup,
                        onClick = { selectedMuscleGroup = muscle }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = muscle,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }


        var current = LocalContext.current
        // Generate Program Button
        Button(
            onClick = {
                // Call API to generate workout program based on the user input
                if (weight.isNotEmpty() && height.isNotEmpty() && age.isNotEmpty()) {
                    try {
                        val weightInt = weight.toInt()
                        val heightInt = height.toInt()
                        val ageInt = age.toInt()

                    } catch (e: NumberFormatException) {
                        // Handle invalid input
                        Toast.makeText(
                            current,
                            "Please enter valid numbers for weight, height, and age.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else {
                    Toast.makeText(
                        current,
                        "Please fill in all fields.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Text("Generate Workout Program")
        }
    }
}