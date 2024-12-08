package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Workout(
    val name: String,
    val durationMinutes: Int, // Duration in minutes
    val exercises: List<String>,
    val intensityLevel: String // "Low", "Medium", "High"
)

val workouts = listOf(
    // Existing workouts...

    Workout(
        name = "Upper Body Sculpt",
        durationMinutes = 40,
        exercises = listOf(
            "Push-ups",       // Targets chest, shoulders, and triceps
            "Dumbbell Rows",  // Strengthens the upper back, biceps, and shoulders
            "Tricep Dips",    // Focuses on the triceps while engaging the shoulders and chest
            "Bicep Curls",    // Targets the biceps for increased arm strength
            "Shoulder Press"  // Strengthens the shoulders and engages the triceps
        ),
        intensityLevel = "Medium"
    ),
    Workout(
        name = "Leg Day Burn",
        durationMinutes = 50,
        exercises = listOf(
            "Squats",         // A lower-body exercise targeting the glutes, quads, and hamstrings
            "Lunges",         // A unilateral movement that works the legs, hips, and glutes
            "Leg Press",      // A machine-based exercise that targets the quads, glutes, and hamstrings
            "Romanian Deadlifts", // A hamstring-focused exercise that also works the glutes and lower back
            "Calf Raises"     // Targets the calves and helps improve ankle stability
        ),
        intensityLevel = "High"
    )
)

@Composable
fun WorkoutDetailsScreen(workout: Workout, callback:() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF7F7FFF), Color(0xFF3F3FFF))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            ){
                Text(
                    text = workout.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Duration: ${workout.durationMinutes} minutes",
                    fontSize = 18.sp,
                    color = Color(0xFF666666)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Intensity: ${workout.intensityLevel}",
                        fontSize = 18.sp,
                        color = when (workout.intensityLevel) {
                            "High" -> Color.Red
                            "Medium" -> Color.Magenta
                            "Low" -> Color.Green
                            else -> Color.Gray
                        },
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Exercises List
            Text(
                text = "Exercises:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(workout.exercises) { exercise ->
                    Text(
                        text = "- $exercise",
                        fontSize = 16.sp,
                        color = Color(0xFF444444)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // Button to Navigate Back
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { callback() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Back to Workouts")
            }
        }
    }
}

@Composable
fun ClickableContainer(workout: Workout, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp))
            .clickable {
                onClick()
                println("Container clicked!")
            }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
        ){
            Text(text="Name: ${workout.name}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text="Duration: ${workout.durationMinutes} min", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun DisplayWorkouts(callback:(Workout) -> Unit){
    LazyColumn(modifier = Modifier) {
        items(workouts) { workout ->
            ClickableContainer(workout = workout, {callback(workout)})
        }
    }
}

