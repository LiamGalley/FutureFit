package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.ViewModels.GPTViewModel
import com.example.myapplication.R
import com.example.myapplication.data.Database.AnotherViewModel
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Exercise
import com.example.myapplication.data.Entities.Workout

@Composable
fun HomeScreen(navController: NavController, dbViewModel: AnotherViewModel, user: Account, gptViewModel: GPTViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Future",
                        fontSize = 75.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Home Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )
                    Text(
                        text = "Fit",
                        fontSize = 75.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Most Recent Workout",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp)
                    .drawBehind {
                        drawLine(
                            strokeWidth = 7F,
                            color = Color.LightGray,
                            start = Offset(-650F, 0F),
                            end = Offset(650F, 0F)
                        )
                    })
                val workoutList by dbViewModel.getWorkoutByAccountId(user.id).observeAsState(emptyList())
                val workout = workoutList.lastOrNull()

                if(workout != null)
                {
                    LatestWorkout(workout, dbViewModel)
                }
                else{
                    Text(text = "No workout yet", fontSize = 24.sp)
                }

                GPTResponseScreen(gptViewModel)

            }

        }
    }
}

@Composable
fun GPTResponseScreen(viewModel: GPTViewModel) {
    val userQuery = "Give me only 1 very short gym motivational quote different everytime"

    LaunchedEffect(Unit) {
        viewModel.setQuery(userQuery)
        viewModel.fetchGPTResponse()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = viewModel.gptResponse,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold,
                //maxLines = 5,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun LatestWorkout(workout: Workout, dbViewModel: AnotherViewModel) {
    Box(
        modifier = Modifier
            .padding(4.dp)
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
                text = "Duration: ${workout.duration} minutes",
                fontSize = 18.sp,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Intensity Level
            Text(
                text = "Intensity: ${workout.intensity}",
                fontSize = 18.sp,
                color = when (workout.intensity) {
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

            val exerciseList by dbViewModel.getExerciseByWorkoutId(workout.workoutId).observeAsState(emptyList())
            exerciseList.forEach { exercise ->
                Text(
                    text = "- ${exercise.name}",
                    fontSize = 16.sp,
                    color = Color(0xFF444444)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
