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

class SettingsViewModel(
    //private val repository: Repository
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _darkTheme = MutableStateFlow(false)
    private val _metricSystem = MutableStateFlow(false)
    private val _largeFontSize = MutableStateFlow(false)
    private val _height: MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val _weight: MutableStateFlow<Double> = MutableStateFlow(0.0)

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
        }
    }

    fun initializeFromDb(idUser: Account){
        _height.value = idUser.height
        _weight.value = idUser.weight
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

    fun switchMeasurements(){
        if (_metricSystem.value){
            val height = Math.round((_height.value * 30.48) * 1000.0) / 1000.0
            val weight = Math.round((_weight.value * 2.20462) * 1000.0) / 1000.0

            viewModelScope.launch {
                dataStoreManager.saveUserHeight(height)
                _height.value = height

                dataStoreManager.saveUserWeight(weight)
                _weight.value = weight
            }
        } else{
            val height = Math.round((_height.value / 30.48) * 1000.0) / 1000.0
            val weight = Math.round((_weight.value / 2.20462) * 1000.0) / 1000.0

            viewModelScope.launch {
                dataStoreManager.saveUserHeight(height)
                _height.value = height

                dataStoreManager.saveUserWeight(weight)
                _weight.value = weight
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