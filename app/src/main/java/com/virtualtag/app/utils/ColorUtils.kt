package com.virtualtag.app.utils

import androidx.compose.ui.graphics.Color

// Returns a HEX string from a Color instance
fun colorToString(color: Color): String {
    return String.format(
        "#%02x%02x%02x%02x", (color.alpha * 255).toInt(),
        (color.red * 255).toInt(), (color.green * 255).toInt(), (color.blue * 255).toInt()
    )
}

// Returns a Color instance from a HEX string
fun stringToColor(color: String): Color {
    return Color(android.graphics.Color.parseColor(color))
}
