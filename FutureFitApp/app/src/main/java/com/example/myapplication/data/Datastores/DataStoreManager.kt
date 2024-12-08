package com.example.myapplication.data.DataStores

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.myapplication.data.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.myapplication.utils.PreferencesKeys

class DataStoreManager(private val context: Context) {
    suspend fun saveTheme(darkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = darkMode }
    }

    suspend fun saveMeasurementSystem(metricSystem: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.METRIC_SYSTEM] = metricSystem }
    }

    suspend fun saveFontSize(fontSize: Boolean){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_SIZE] = fontSize}
    }

    suspend fun saveUserName(userName: String){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = userName }
    }

    suspend fun saveUserEmail(userEmail: String){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_EMAIL] = userEmail }
    }

    suspend fun saveUserWeight(userHeight: Double){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.WEIGHT] = userHeight }
    }

    suspend fun saveUserHeight(userHeight: Double){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.HEIGHT] = userHeight }
    }

    suspend fun saveInitialization(initialized: Boolean){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.INITIALIZED] = initialized }
    }
    val userIdFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_ID] ?: 0}

    val userEmailFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_EMAIL] ?: ""}

    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_NAME] ?: ""}

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.DARK_MODE] ?: false }

    val metricSystemFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.METRIC_SYSTEM] ?: false }

    val fontSizeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.FONT_SIZE] ?: false }

    val userHeightFlow: Flow<Double> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.HEIGHT] ?: 0.0}

    val userWeightFlow: Flow<Double> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.WEIGHT] ?: 0.0}

    val initialized: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.INITIALIZED] ?: false}
}