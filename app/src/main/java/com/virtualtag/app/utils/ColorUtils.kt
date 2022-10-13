package com.virtualtag.app.utils

import androidx.compose.ui.graphics.Color

fun colorToString(color: Color): String {
    return String.format(
        "#%02x%02x%02x%02x", (color.alpha * 255).toInt(),
        (color.red * 255).toInt(), (color.green * 255).toInt(), (color.blue * 255).toInt()
    )
}

fun stringToColor(color: String): Color {
    return Color(android.graphics.Color.parseColor(color))
}
