package com.example.myapplication.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    // General Preferences
    val LOGGED_IN = booleanPreferencesKey("logged_in")
    val USER_ID = intPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")

    // Home Preferences


    // Profile & Training Preferences
    val HEIGHT = floatPreferencesKey("height")
    val WEIGHT = doublePreferencesKey("weight")
    val BODY_FAT_PERCENTAGE = floatPreferencesKey("body_fat_percentage")
    val ACTIVITY_LEVEL = intPreferencesKey("activity_level")
    val AGE = intPreferencesKey("age")
    val GENDER = stringPreferencesKey("gender")
    val PICTURE = stringPreferencesKey("picture")

    // Settings Preferences
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val METRIC_SYSTEM = booleanPreferencesKey("metric_system")
    val FONT_SIZE = intPreferencesKey("font_size")
}