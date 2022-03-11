package com.example.todo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryColor,
    primaryVariant = purple700,
    secondary = secondaryColor,
    onSecondary = Color.Black,
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = purple700,
    secondary = secondaryColor,
    onSecondary = Color.White,
    onBackground = Color.Black
)

@Composable
fun TasksTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}