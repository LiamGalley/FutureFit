package com.example.myapplication.data.Database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Exercise
import com.example.myapplication.data.Entities.Workout
import kotlinx.coroutines.launch

class DatabaseViewModel(
    private val repository: Repository
): ViewModel() {
    fun getAccountByEmail(email: String) = repository.getAccountByEmail(email).asLiveData(viewModelScope.coroutineContext)

    fun getWorkoutById(id:Int) = repository.getWorkoutById(id).asLiveData(viewModelScope.coroutineContext)

    fun getWorkoutByAccountId(id: Int) = repository.getWorkoutByAccountById(id).asLiveData(viewModelScope.coroutineContext)

    fun getExerciseByWorkoutId(id: Int) = repository.getExercisesByWorkoutId(id).asLiveData(viewModelScope.coroutineContext)


    fun upsertAccountFromUI(account: Account, onSuccess: (Account) -> Unit) {
        viewModelScope.launch {
            val newAccount = repository.upsertAccount(account)
            onSuccess(newAccount) // Return the new account to the UI
        }
    }

    fun upsertWorkoutFromUI(workout: Workout, onSuccess: (Workout) -> Unit)
    {
        viewModelScope.launch {
            val newAccount = repository.upsertWorkout(workout)
            onSuccess(newAccount)
        }
    }

    fun deleteAccount(account: Account){
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }

    fun upsertWorkout(workout: Workout)
    {
        viewModelScope.launch {
            repository.upsertWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Workout){
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }

    fun upsertExercise(exercise: Exercise)
    {
        viewModelScope.launch {
            repository.upsertExercise(exercise)
        }
    }
}