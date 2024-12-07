// CustomTheme.kt

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.myapplication.ui.theme.theme.Typography
import com.example.myapplication.ui.theme.theme.DarkColorScheme
import com.example.myapplication.ui.theme.theme.LargeTypography
import com.example.myapplication.ui.theme.theme.LightColorScheme

@Composable
fun FutureFitTheme(
    darkTheme: Boolean = false,
    largeFont: Boolean = false,
    content: @Composable () -> Unit
) {
    val themeScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typographyScheme = if (largeFont) LargeTypography else Typography

    MaterialTheme(
        colorScheme = themeScheme,
        typography = typographyScheme,
        content = content
    )
}