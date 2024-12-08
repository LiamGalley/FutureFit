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
    private val _initialized: MutableStateFlow<Boolean> = MutableStateFlow(false)

    var darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()
    var metricSystem: StateFlow<Boolean> = _metricSystem.asStateFlow()
    var largeFontSize: StateFlow<Boolean> = _largeFontSize.asStateFlow()
    var height: StateFlow<Double> = _height.asStateFlow()
    var weight: StateFlow<Double> = _weight.asStateFlow()
    var initialized: StateFlow<Boolean> = _initialized.asStateFlow()


    var metricHeight = _height.value
    var metricWeight = _weight.value
    var imperialHeight = Math.round((_height.value / 30.48) * 1000.0) / 1000.0
    var imperialWeight = Math.round((_weight.value * 2.20462) * 1000.0) / 1000.0

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

    fun switchMeasurements(){
        if (_metricSystem.value){
            viewModelScope.launch {
                dataStoreManager.saveUserHeight(metricHeight)
                _height.value = metricHeight

                dataStoreManager.saveUserWeight(metricWeight)
                _weight.value = metricWeight
            }
        } else{
            viewModelScope.launch {
                dataStoreManager.saveUserHeight(imperialHeight)
                _height.value = imperialHeight

                dataStoreManager.saveUserWeight(imperialWeight)
                _weight.value = imperialWeight
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