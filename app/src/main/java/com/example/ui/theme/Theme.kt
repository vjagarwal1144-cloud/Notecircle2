package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccentYellow,
    onPrimary = Color(0xFF171717),
    primaryContainer = Color(0xFF2C2818),
    onPrimaryContainer = DarkAccentYellow,
    secondary = LavenderPastel,
    onSecondary = Color(0xFF171717),
    background = DarkBackground,
    onBackground = DarkPrimaryText,
    surface = DarkSurface,
    onSurface = DarkPrimaryText,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkSecondaryText,
    outline = DarkBorder,
    error = CoralPastel,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = AmberAccent,
    onPrimary = PrimaryInk,
    primaryContainer = AmberLight,
    onPrimaryContainer = PrimaryInk,
    secondary = LavenderPastel,
    onSecondary = PrimaryInk,
    background = CreamBackground,
    onBackground = PrimaryInk,
    surface = PureWhite,
    onSurface = PrimaryInk,
    surfaceVariant = Color(0xFFF2EEE4),
    onSurfaceVariant = SecondaryInk,
    outline = BorderDark,
    error = CoralPastel,
    onError = PureWhite
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
