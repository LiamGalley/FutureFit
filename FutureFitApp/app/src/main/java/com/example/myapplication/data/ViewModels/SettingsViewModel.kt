package com.example.myapplication.data.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DataStores.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    //private val repository: Repository
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    //#region Fields
    // ADD DATABASE CONNECTION
    private val _userId: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    private val _userEmail: MutableStateFlow<String> = MutableStateFlow("")
    private var _darkTheme = MutableStateFlow(false)
    private var _metricSystem = MutableStateFlow(false)
    private var _fontSize = MutableStateFlow(12)
    //#endregion

    //#region StateFlows
    private var darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()
    private var metricSystem: StateFlow<Boolean> = _metricSystem.asStateFlow()
    private var fontSize: StateFlow<Int> = _fontSize.asStateFlow()
    //#endregion

    //#region Constructor
    init {
        viewModelScope.launch {
            _userId.value = dataStoreManager.userIdFlow.first()
            _userName.value = dataStoreManager.userNameFlow.first()
            _userEmail.value = dataStoreManager.userEmailFlow.first()
            _darkTheme.value = dataStoreManager.darkModeFlow.first()
            _metricSystem.value = dataStoreManager.metricSystemFlow.first()
            _fontSize.value = dataStoreManager.fontSizeFlow.first()
        }
    }
    //#endregion

    //#region Methods
    fun toggleTheme() {
        val theme = !darkTheme.value

        viewModelScope.launch {
            dataStoreManager.saveTheme(theme)
            _darkTheme.value = theme
        }
    }

    fun toggleMeasurementSystem() {
        val system = !metricSystem.value

        viewModelScope.launch {
            dataStoreManager.saveMeasurementSystem(system)
            _metricSystem.value = system
        }
    }

    fun changeFontSize(size: Int){
        viewModelScope.launch {
            dataStoreManager.saveFontSize(size)
            _fontSize.value = size
        }
    }
    //#endregion
}