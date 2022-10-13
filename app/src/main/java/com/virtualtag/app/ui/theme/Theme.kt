package com.virtualtag.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = WhiteBG,
    secondaryVariant = Gray700,
    background = BlackBG,
)

private val LightColorPalette = lightColors(
    primary = Blue700,
    primaryVariant = Blue500,
    secondary = BlackBG,
    secondaryVariant = WhiteBG,
    background = Gray200,
)

@Composable
fun VirtualTagTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    // Set status bar color
    val systemUIController = rememberSystemUiController()
    if (darkTheme) {
        systemUIController.setStatusBarColor(
            color = Gray700
        )
    } else {
        systemUIController.setStatusBarColor(
            color = Blue700
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
