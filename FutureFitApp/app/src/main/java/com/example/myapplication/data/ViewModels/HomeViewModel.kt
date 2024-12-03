package com.example.myapplication.data.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.entities.Exercise

class HomeViewModel : ViewModel() {
    // ADD PROPER DATABASE CONNECTION
    private val repository: String = ""
    private val _quote: MutableState<String> = mutableStateOf("")

    init {

    }
}