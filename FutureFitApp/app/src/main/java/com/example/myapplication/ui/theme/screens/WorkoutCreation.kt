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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.myapplication.data.Database.DatabaseViewModel
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Exercise
import com.example.myapplication.data.Entities.Workout
import com.example.myapplication.data.ViewModels.GPTViewModel
import com.example.myapplication.ui.theme.navigation.WorkoutHistory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun WorkoutSelectionPage(
    navController: NavController,
    user: Account,
    dbViewModel: DatabaseViewModel,
    gptViewModel: GPTViewModel,
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
                if (weight.isNotEmpty() && height.isNotEmpty() && age.isNotEmpty()) {
                    try {
                        val weightInt = weight.toInt()
                        val heightInt = height.toInt()
                        val ageInt = age.toInt()

                        val workoutQuery = """
                    Give me a workout plan using these information: $ageInt, ${user.bodyFat}, $weightInt, $heightInt, $selectedMuscleGroup. 
                    The output needs to be <TitleOfMuscleWorkout>, <duration in minutes>, <Intensity either Low, Medium, High>, 
                    <Exercises 3-5 only their names>. The output is a string comma separated with no spaces after the commas.
                """.trimIndent()

                        gptViewModel.viewModelScope.launch {
                            val response = gptViewModel.fetchGPTResponse(workoutQuery)

                            val responseData: List<String> = response.split(", ")
                            if (responseData.size >= 3) {
                                dbViewModel.upsertWorkoutFromUI(
                                    Workout(
                                        name = responseData[0],
                                        date = System.currentTimeMillis(),
                                        duration = responseData[1].toInt(),
                                        intensity = responseData[2],
                                        accountId = user.id
                                    )
                                ){
                                        newWorkout ->
                                    val newResponse: List<String> = responseData.slice(3..(responseData.count() - 1))
                                    newResponse.forEach { exercise ->
                                        dbViewModel.upsertExercise(Exercise(name = exercise, workoutId = newWorkout.workoutId))
                                    }
                                }

                                navController.navigate(WorkoutHistory.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
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