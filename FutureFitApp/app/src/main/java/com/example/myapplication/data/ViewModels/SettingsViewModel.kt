package com.example.myapplication.data.ViewModels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DataStores.DataStoreManager
import com.example.myapplication.data.Entities.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    //private val repository: Repository
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _darkTheme = MutableStateFlow(false)
    private val _metricSystem = MutableStateFlow(false)
    private val _largeFontSize = MutableStateFlow(false)
    private val _height: MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val _weight: MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val _initialized: MutableStateFlow<Boolean> = MutableStateFlow(false)

    var darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()
    var metricSystem: StateFlow<Boolean> = _metricSystem.asStateFlow()
    var largeFontSize: StateFlow<Boolean> = _largeFontSize.asStateFlow()
    var height: StateFlow<Double> = _height.asStateFlow()
    var weight: StateFlow<Double> = _weight.asStateFlow()

    init {
        viewModelScope.launch {
            _darkTheme.value = dataStoreManager.darkModeFlow.first()
            _metricSystem.value = dataStoreManager.metricSystemFlow.first()
            _largeFontSize.value = dataStoreManager.fontSizeFlow.first()
            _height.value = dataStoreManager.userHeightFlow.first()
            _weight.value = dataStoreManager.userWeightFlow.first()
            _initialized.value = dataStoreManager.initialized.first()
        }
    }

    fun initializeFromDb(idUser: Account){
        if (!_initialized.value){
            _weight.value = idUser.weight
            _height.value = idUser.height
        }
    }

    fun toggleTheme() {
        val theme = !darkTheme.value

        viewModelScope.launch {
            dataStoreManager.saveTheme(theme)
            _darkTheme.value = theme
        }
    }

    fun toggleMeasurementSystem() {
        val system = !_metricSystem.value
        switchMeasurements()

        viewModelScope.launch {
            dataStoreManager.saveMeasurementSystem(system)
            _metricSystem.value = system
        }
    }

    fun switchMeasurements() {
        val isMetric = _metricSystem.value

        viewModelScope.launch {
            if (isMetric) {
                val imperialHeight = (_height.value * 0.0328084)
                val imperialWeight = (_weight.value * 2.20462)

                dataStoreManager.saveUserHeight(imperialHeight)
                dataStoreManager.saveUserWeight(imperialWeight)

                _height.value = Math.round(imperialHeight * 100.0) / 100.0
                _weight.value = Math.round(imperialWeight * 100.0) / 100.0
            } else {
                val metricHeight = (_height.value / 0.0328084)
                val metricWeight = (_weight.value / 2.20462)

                // Save the converted values
                dataStoreManager.saveUserHeight(metricHeight)
                dataStoreManager.saveUserWeight(metricWeight)

                _height.value = Math.round(metricHeight * 100.0) / 100.0
                _weight.value = Math.round(metricWeight * 100.0) / 100.0
            }
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