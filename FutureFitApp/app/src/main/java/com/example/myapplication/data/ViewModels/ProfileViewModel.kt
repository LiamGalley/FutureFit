package com.example.myapplication.data.ViewModels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.DataStores.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    //#region Fields
    //ADD THE DATABASE CONNECTION
    private val _repository: String = ""
    private val _userId: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    private val _userEmail: MutableStateFlow<String> = MutableStateFlow("")

    private val _height: Double = 0.00
    private val _weight: Double = 56.7
    private val _bodyFat: Float = 0.0F
    private val _activityLevel: Int = 0
    private val _age: Int = 0
    private val _gender: String = ""
    //#endregion
}