package com.ajmir.ui.commons.resources

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Colors {
    val background = Color.White
    val backgroundSecondary = Color(red = 0xF5, green = 0xF5, blue = 0xF5, alpha = 0xFF)

    val primary = Color(red = 0x12, green = 0x42, blue = 0xBD, alpha = 0xFF)
    val secondary = Color(red = 0x67, green = 0x3A, blue = 0xB7, alpha = 0xFF)

    val title = Color(red = 0x3E, green = 0x4B, blue = 0x6F, alpha = 0xFF)
    val text = Color.Gray
    val section = Color(red = 0x98, green = 0xA9, blue = 0xDA, alpha = 0xFF)
    val textDark = Color.DarkGray

    val loading = primary
    val error = Color(red = 0xBB, green = 0x5B, blue = 0x5B, alpha = 0xFF)
    val errorBackground = Color(red = 0xFA, green = 0xA7, blue = 0xA7, alpha = 0xFF)
    val warning = Color(red = 0xAF, green = 0x83, blue = 0x0, alpha = 0xFF)
    val warningBackground = Color(red = 0xF8, green = 0xE2, blue = 0xA1, alpha = 0xFF)

    val accountBrushBackground = Brush.verticalGradient(listOf(primary, secondary))
}
