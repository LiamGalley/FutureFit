package com.example.myapplication.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object PreferencesKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val METRIC_SYSTEM = booleanPreferencesKey("metric_system")
}