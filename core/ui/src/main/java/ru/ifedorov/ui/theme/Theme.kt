package ru.ifedorov.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CoursesColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = OnSurfaceVariantColor,
    outline = OutlineColor
)

@Composable
fun ThousandCoursesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CoursesColorScheme,
        typography = Typography,
        content = content
    )
}
