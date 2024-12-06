package com.example.myapplication.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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

@Composable
fun WorkoutSelectionPage() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var selectedMuscleGroup by remember { mutableStateOf("Whole Body") }

    val muscleGroups = listOf("Chest", "Back", "Legs", "Arms", "Shoulders", "Abs", "Whole Body")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Workout Program Generator", style = MaterialTheme.typography.headlineSmall)

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

        // Muscle group selection
        Text(text = "Select Muscle Group", style = MaterialTheme.typography.bodyLarge)
        muscleGroups.forEach { muscleGroup ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedMuscleGroup == muscleGroup,
                    onClick = { selectedMuscleGroup = muscleGroup }
                )
                Text(text = muscleGroup)
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