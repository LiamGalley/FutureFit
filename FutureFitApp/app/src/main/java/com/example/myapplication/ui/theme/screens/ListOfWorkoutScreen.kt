package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    ),
    Workout(
        name = "HIIT Burnout",
        durationMinutes = 30,
        exercises = listOf(
            "Jump Squats",    // A plyometric exercise that targets the quads and glutes while increasing heart rate
            "Burpees",        // Full-body movement combining a squat, jump, and push-up for high calorie burn
            "Mountain Climbers", // A fast-paced cardio movement that works the core and shoulders
            "Skater Jumps",   // A lateral plyometric movement targeting the legs, glutes, and improving agility
            "High Knees"      // A cardio exercise that boosts endurance while engaging the core and legs
        ),
        intensityLevel = "High"
    ),
    Workout(
        name = "Lower Body Burn",
        durationMinutes = 40,
        exercises = listOf(
            "Bulgarian Split Squats", // A unilateral leg exercise targeting the quads, hamstrings, and glutes
            "Glute Bridges",          // A glute and hamstring exercise that strengthens the posterior chain
            "Step-ups",               // A leg exercise focusing on the quads, glutes, and balance
            "Leg Extensions",         // Targets the quadriceps with a machine-based movement
            "Hamstring Curls"         // Focuses on the hamstrings, helping to balance out leg development
        ),
        intensityLevel = "Medium"
    ),
    Workout(
        name = "Total Body Conditioning",
        durationMinutes = 60,
        exercises = listOf(
            "Deadlifts",       // A full-body strength exercise focusing on the posterior chain
            "Push-ups",        // Targets the chest, shoulders, and triceps
            "Pull-ups",        // Upper body exercise that primarily targets the back and biceps
            "Kettlebell Swings", // A dynamic exercise targeting the hips, glutes, and core
            "Mountain Climbers" // A cardio exercise that engages the core, shoulders, and legs
        ),
        intensityLevel = "High"
    ),
    Workout(
        name = "Active Recovery",
        durationMinutes = 30,
        exercises = listOf(
            "Gentle Yoga Flow",    // A slow-paced yoga routine focusing on mobility and stretching
            "Foam Rolling",        // Myofascial release to reduce muscle tightness and improve flexibility
            "Dynamic Stretches",   // Gentle stretches to improve range of motion and flexibility
            "Deep Breathing",      // Focused breathing techniques for relaxation and stress reduction
            "Walking or Light Jog" // Light cardio to promote blood flow and recovery
        ),
        intensityLevel = "Low"
    ),
    Workout(
        name = "Sprint Intervals",
        durationMinutes = 25,
        exercises = listOf(
            "30-Second Sprints",    // Sprinting at maximum effort for 30 seconds
            "Rest (1 minute)",      // Active recovery between sprints
            "Repeat x 10"           // Perform 10 rounds of sprints and recovery
        ),
        intensityLevel = "High"
    ),
    Workout(
        name = "Mobility and Stretching",
        durationMinutes = 35,
        exercises = listOf(
            "Hip Flexor Stretch",    // Focuses on releasing tension in the hip flexors and improving hip mobility
            "Hamstring Stretch",     // Targets the hamstrings and helps improve flexibility in the back of the legs
            "Shoulder Rolls",        // A shoulder mobility exercise to improve range of motion and reduce tension
            "Cat-Cow Stretch",       // A yoga pose that improves spinal mobility and relieves lower back tension
            "Quad Stretch"           // A stretch to improve flexibility in the quadriceps and hips
        ),
        intensityLevel = "Low"
    ),
    Workout(
        name = "Endurance Run",
        durationMinutes = 60,
        exercises = listOf(
            "Warm-up (5-10 minutes)",   // Light jogging or brisk walking to prepare the body
            "Run (40-50 minutes)",      // A steady-paced run focusing on building endurance
            "Cool-down (5-10 minutes)"  // Light walking or stretching to aid recovery
        ),
        intensityLevel = "Medium"
    ),
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
    ),
    Workout(
        name = "HIIT Burnout",
        durationMinutes = 30,
        exercises = listOf(
            "Jump Squats",    // A plyometric exercise that targets the quads and glutes while increasing heart rate
            "Burpees",        // Full-body movement combining a squat, jump, and push-up for high calorie burn
            "Mountain Climbers", // A fast-paced cardio movement that works the core and shoulders
            "Skater Jumps",   // A lateral plyometric movement targeting the legs, glutes, and improving agility
            "High Knees"      // A cardio exercise that boosts endurance while engaging the core and legs
        ),
        intensityLevel = "High"
    ),
    Workout(
        name = "Lower Body Burn",
        durationMinutes = 40,
        exercises = listOf(
            "Bulgarian Split Squats", // A unilateral leg exercise targeting the quads, hamstrings, and glutes
            "Glute Bridges",          // A glute and hamstring exercise that strengthens the posterior chain
            "Step-ups",               // A leg exercise focusing on the quads, glutes, and balance
            "Leg Extensions",         // Targets the quadriceps with a machine-based movement
            "Hamstring Curls"         // Focuses on the hamstrings, helping to balance out leg development
        ),
        intensityLevel = "Medium"
    ),
    Workout(
        name = "Total Body Conditioning",
        durationMinutes = 60,
        exercises = listOf(
            "Deadlifts",       // A full-body strength exercise focusing on the posterior chain
            "Push-ups",        // Targets the chest, shoulders, and triceps
            "Pull-ups",        // Upper body exercise that primarily targets the back and biceps
            "Kettlebell Swings", // A dynamic exercise targeting the hips, glutes, and core
            "Mountain Climbers" // A cardio exercise that engages the core, shoulders, and legs
        ),
        intensityLevel = "High"
    ),
    Workout(
        name = "Active Recovery",
        durationMinutes = 30,
        exercises = listOf(
            "Gentle Yoga Flow",    // A slow-paced yoga routine focusing on mobility and stretching
            "Foam Rolling",        // Myofascial release to reduce muscle tightness and improve flexibility
            "Dynamic Stretches",   // Gentle stretches to improve range of motion and flexibility
            "Deep Breathing",      // Focused breathing techniques for relaxation and stress reduction
            "Walking or Light Jog" // Light cardio to promote blood flow and recovery
        ),
        intensityLevel = "Low"
    ),
    Workout(
        name = "Sprint Intervals",
        durationMinutes = 25,
        exercises = listOf(
            "30-Second Sprints",    // Sprinting at maximum effort for 30 seconds
            "Rest (1 minute)",      // Active recovery between sprints
            "Repeat x 10"           // Perform 10 rounds of sprints and recovery
        ),
        intensityLevel = "High"
    ),
    Workout(
        name = "Mobility and Stretching",
        durationMinutes = 35,
        exercises = listOf(
            "Hip Flexor Stretch",    // Focuses on releasing tension in the hip flexors and improving hip mobility
            "Hamstring Stretch",     // Targets the hamstrings and helps improve flexibility in the back of the legs
            "Shoulder Rolls",        // A shoulder mobility exercise to improve range of motion and reduce tension
            "Cat-Cow Stretch",       // A yoga pose that improves spinal mobility and relieves lower back tension
            "Quad Stretch"           // A stretch to improve flexibility in the quadriceps and hips
        ),
        intensityLevel = "Low"
    ),
    Workout(
        name = "Endurance Run",
        durationMinutes = 60,
        exercises = listOf(
            "Warm-up (5-10 minutes)",   // Light jogging or brisk walking to prepare the body
            "Run (40-50 minutes)",      // A steady-paced run focusing on building endurance
            "Cool-down (5-10 minutes)"  // Light walking or stretching to aid recovery
        ),
        intensityLevel = "Medium"
    )
)

@Composable
fun WorkoutDetailsScreen(workout: Workout, callback:() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            // Workout Title
            Text(
                text = workout.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Duration
            Text(
                text = "Duration: ${workout.durationMinutes} minutes",
                fontSize = 18.sp,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Intensity Level
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

            Spacer(modifier = Modifier.height(16.dp))

            // Exercises List
            Text(
                text = "Exercises:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            workout.exercises.forEach { exercise ->
                Text(
                    text = "- $exercise",
                    fontSize = 16.sp,
                    color = Color(0xFF444444)
                )
                Spacer(modifier = Modifier.height(4.dp))
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

