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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.Database.AnotherViewModel
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Workout
import com.example.myapplication.data.ViewModels.GPTViewModel
import com.example.myapplication.ui.theme.navigation.WorkoutHistory
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant

@Composable
fun WorkoutSelectionPage(navController: NavController, user: Account, dbViewModel: AnotherViewModel, gptViewModel: GPTViewModel) {
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

        // Created a column in the column only for muscle groups choice
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
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
        }

        var current = LocalContext.current
        // Generate Program Button
        Button(
            onClick = {
                if (weight.isNotEmpty() && height.isNotEmpty() && age.isNotEmpty()) {
                    try {
                        val weightInt = weight.toInt()
                        val heightInt = height.toInt()
                        val ageInt = age.toInt()

                        val workoutQuery = """
                    Give me a workout plan using these information: <gender>, $ageInt, <BodyFat%>, $weightInt, $heightInt, $selectedMuscleGroup. 
                    The output needs to be <TitleOfMuscleWorkout>, <duration in minutes>, <Intensity either Low, Medium, High>, 
                    <Exercises 3-5 only their names>. The output is a string comma separated with no spaces after the commas.
                """.trimIndent()

                        gptViewModel.viewModelScope.launch {
                            val response = gptViewModel.fetchGPTResponse(workoutQuery)

                            val responsedata = response.split(',')
                            if (responsedata.size >= 3) {
                                dbViewModel.upsertWorkout(
                                    Workout(
                                        name = responsedata[0],
                                        date = System.currentTimeMillis(),
                                        duration = responsedata[1].toInt(),
                                        intensity = responsedata[2],
                                        accountId = user.id
                                    )
                                )

                                navController.navigate(WorkoutHistory.route) {
                                    popUpTo("workouts") { inclusive = true }
                                }

                            } else {
                                Toast.makeText(
                                    current,
                                    "Invalid response from GPT.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            current,
                            "Please enter valid numbers for weight, height, and age.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        current,
                        "Please fill in all fields.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Text(text = "Generate Workout Program")
        }

    }
}