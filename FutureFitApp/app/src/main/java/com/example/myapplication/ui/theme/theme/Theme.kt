package com.example.myapplication.ui.theme.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

// Define the light color scheme
val LightColorScheme = lightColorScheme(
    primary = LightPrimaryText,
    onPrimary = LightBorder,
    primaryContainer = LightBackground,
    secondary = LightSecondaryText,
    onSecondary = LightBackground,
    background = LightForeground,
    onBackground = LightPrimaryText,
    surface = LightBackground,
    onSurface = LightSecondaryText,
)

// Define the dark color scheme
val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryText,
    onPrimary = DarkBorder,
    primaryContainer = DarkBackground,
    secondary = DarkSecondaryText,
    onSecondary = DarkBackground,
    background = DarkForeGround,
    onBackground = DarkBorder,
    surface = DarkBackground,
    onSurface = DarkBorder,
)