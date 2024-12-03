package com.example.myapplication.data.DataStores

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.myapplication.utils.PreferencesKeys
import com.example.myapplication.utils.dataStore

class DataStoreManager(private val context: Context) {
    suspend fun saveTheme(darkMode: Boolean) {
        context.dataStore.edit { preferences -> preferences[PreferencesKeys.DARK_MODE] = darkMode
        }
    }

    suspend fun saveMeasurementSystem(metricSystem: Boolean) {
        context.dataStore.edit { preferences -> preferences[PreferencesKeys.METRIC_SYSTEM] = metricSystem
        }
    }

    suspend fun saveFontSize(fontSize: Int){
        context.dataStore.edit { preferences -> preferences[PreferencesKeys.FONT_SIZE] = fontSize}
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.DARK_MODE] ?: false
        }

    val metricSystemFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.METRIC_SYSTEM] ?: false
        }

    val fontSizeFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.FONT_SIZE] ?: 12}
}