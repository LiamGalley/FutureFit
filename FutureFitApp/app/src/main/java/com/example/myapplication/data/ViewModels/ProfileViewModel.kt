package com.example.myapplication.data.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DataStores.DataStoreManager
import com.example.myapplication.data.Entities.Account
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
    private val _height: MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val _weight: MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val _initialized: MutableStateFlow<Boolean> = MutableStateFlow(false)
    //#endregion

    var userId: StateFlow<Int> = _userId.asStateFlow()
    var userName: StateFlow<String> = _userName.asStateFlow()
    var userEmail: StateFlow<String> = _userEmail.asStateFlow()
    var height: StateFlow<Double> = _height.asStateFlow()
    var weight: StateFlow<Double> = _weight.asStateFlow()
    var initialized: StateFlow<Boolean> = _initialized.asStateFlow()

    var bodyFat: Double = 0.0
    var activityLevel: Int = 0
    var age: Int = 0

    init{
        // CALL QUERY TO FILL ABOVE FIELDS

        viewModelScope.launch {
            _userId.value = dataStoreManager.userIdFlow.first()
            _userName.value = dataStoreManager.userNameFlow.first()
            _userEmail.value = dataStoreManager.userEmailFlow.first()
            _height.value = dataStoreManager.userHeightFlow.first()
            _weight.value = dataStoreManager.userWeightFlow.first()
            _initialized.value = dataStoreManager.initialized.first()
        }
    }

    fun initializeFromDb(idUser: Account){
        if (!_initialized.value){
            _weight.value = idUser.weight
            _height.value = idUser.height
            bodyFat = idUser.bodyFat.toDouble()
            activityLevel = idUser.activityLevel
            age = idUser.age
            _userName.value = "${idUser.firstName} ${idUser.lastName}"
            _userEmail.value = idUser.emailAddress
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