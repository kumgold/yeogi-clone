package com.example.yeogi

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.yeogi.main.MainScreen
import com.example.yeogi.ui.theme.YeogiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YeogiTheme {
                /**
                 * 개발 기기가 태블릿이라서 사이즈를 줄이는 코드를 추가하였습니다.
                 */
//                val configuration = LocalConfiguration.current
//                val screenWidth = configuration.screenWidthDp.dp
//
//                val desiredPhoneWidth = screenWidth / 3
//                val aspectRatio = 9f / 16f
//
//                val desiredPhoneHeight = (desiredPhoneWidth / aspectRatio)
//
//                Box(
//                    modifier = Modifier
//                        .background(MaterialTheme.colorScheme.background)
//                        .fillMaxSize()
//                        .wrapContentSize()
//                        .width(desiredPhoneWidth)
//                        .height(desiredPhoneHeight),
//                    contentAlignment = Alignment.Center
//                ) {
//                    MainScreen()
//                }
                MainScreen()
            }
        }
    }
}

@Composable
fun SystemBarColor(color: Color) {
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