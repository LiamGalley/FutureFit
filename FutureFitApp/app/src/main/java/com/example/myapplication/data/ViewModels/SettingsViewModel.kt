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
    private var _darkTheme = MutableStateFlow(false)
    private var _metricSystem = MutableStateFlow(false)
    private var _largeFontSize = MutableStateFlow(false)

    var darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()
    var metricSystem: StateFlow<Boolean> = _metricSystem.asStateFlow()
    var largeFontSize: StateFlow<Boolean> = _largeFontSize.asStateFlow()

    init {
        viewModelScope.launch {
            _darkTheme.value = dataStoreManager.darkModeFlow.first()
            _metricSystem.value = dataStoreManager.metricSystemFlow.first()
            _largeFontSize.value = dataStoreManager.fontSizeFlow.first()
        }
    }

    fun toggleTheme() {
        val theme = !_darkTheme.value

        viewModelScope.launch {
            dataStoreManager.saveTheme(theme)
            _darkTheme.value = theme
        }
    }

    fun toggleMeasurementSystem() {
        val system = !_metricSystem.value

        viewModelScope.launch {
            dataStoreManager.saveMeasurementSystem(system)
            _metricSystem.value = system
        }
    }

    fun toggleLargeFontSize(){
        val system = !_largeFontSize.value

        viewModelScope.launch {
            dataStoreManager.saveFontSize(system)
            _largeFontSize.value = system
        }
    }
}