package com.example.myapplication.data.ViewModels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.DataStores.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    //private val repository: Repository
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    //#region Fields
    private val _userId: MutableStateFlow<Int> = MutableStateFlow(0)
    private var _userName: MutableStateFlow<String> = MutableStateFlow("")
    private val _userEmail: MutableStateFlow<String> = MutableStateFlow("")
    //#endregion

    var userId: StateFlow<Int> = _userId.asStateFlow()
    var userName: StateFlow<String> = _userName.asStateFlow()
    var userEmail: StateFlow<String> = _userEmail.asStateFlow()

    var bodyFat: Double = 0.0
    var activityLevel: Int = 0
    var age: Int = 0

    init{
        // CALL QUERY TO FILL ABOVE FIELDS

        viewModelScope.launch {
            _userId.value = dataStoreManager.userIdFlow.first()
            _userName.value = dataStoreManager.userNameFlow.first()
            _userEmail.value = dataStoreManager.userEmailFlow.first()
        }
    }

    //#region Methods
    fun adjustHeight(height: Double){
        // CALL REPO TO ADJUST HEIGHT
    }

    fun adjustWeight(height: Double){
        // CALL REPO TO ADJUST WEIGHT
    }

    fun adjustBodyFat(height: Double){
        // CALL REPO TO ADJUST BODY FAT
    }

    fun adjustActivityLevel(height: Double){
        // CALL REPO TO ADJUST ACTIVITY LEVEL
    }

    fun adjustAge(height: Double){
        // CALL REPO TO ADJUST AGE
    }

    fun adjustGender(height: Double){
        // CALL REPO TO ADJUST GENDER
    }


    fun changeUserEmail(userEmail: String){
        // Changes in datastore
        viewModelScope.launch {
            dataStoreManager.saveUserEmail(userEmail)
            _userEmail.value = userEmail
        }

        // Changes in database
    }

    //#endregion
}