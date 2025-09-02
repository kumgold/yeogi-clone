package com.example.yeogi

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.yeogi.main.MainScreen
import com.example.yeogi.ui.theme.Background
import com.example.yeogi.ui.theme.YeogiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YeogiTheme {
                SystemBarColor(color = Background)
                MainScreen()
            }
        }
    }
}

@Composable
fun SystemBarColor(color: androidx.compose.ui.graphics.Color) {
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()

    SideEffect {
        val window = (view.context as Activity).window

        window.statusBarColor = color.toArgb()
        window.navigationBarColor = color.toArgb()

        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
    }
}