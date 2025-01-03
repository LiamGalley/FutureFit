package com.example.myapplication.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    // General Preferences
    val INITIALIZED = booleanPreferencesKey("initialized")
    val USER_ID = intPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")

    // Settings Preferences
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val METRIC_SYSTEM = booleanPreferencesKey("metric_system")
    val FONT_SIZE = booleanPreferencesKey("font_size")

    // Profile Preferences
    val HEIGHT = doublePreferencesKey("height")
    val WEIGHT = doublePreferencesKey("weight")
    val AGE = intPreferencesKey("age")
    val BODY_FAT = intPreferencesKey("body_fat")
    val ACTIVITY_LEVEL = intPreferencesKey("activity_level")
}