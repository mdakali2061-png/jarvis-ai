package com.jarvis.ai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val JarvisColors = darkColorScheme(
    primary = Violet,
    onPrimary = TextPrimary,
    secondary = Blue,
    tertiary = Pink,
    background = Bg,
    onBackground = TextPrimary,
    surface = BgSoft,
    onSurface = TextPrimary,
    surfaceVariant = BgSoft,
    onSurfaceVariant = TextMuted,
    outline = VioletLight
)

private val JarvisTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        letterSpacing = 1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 13.sp,
        lineHeight = 19.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 0.6.sp
    )
)

@Composable
fun JarvisTheme(content: @Composable () -> Unit) {
    // JARVIS is always dark/futuristic, regardless of the system theme.
    MaterialTheme(
        colorScheme = JarvisColors,
        typography = JarvisTypography,
        content = content
    )
}
