package com.example.myapplication.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    // General Preferences
    val LOGGED_IN = booleanPreferencesKey("logged_in")
    val USER_ID = intPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")

    // Settings Preferences
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val METRIC_SYSTEM = booleanPreferencesKey("metric_system")
    val FONT_SIZE = booleanPreferencesKey("font_size")
}