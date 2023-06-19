package com.test_application.hollyscompose.ui.compose

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HollysHomeTopAppBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "")
        },
        backgroundColor = Color.White,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    )
}

@Composable
fun HollysDefaultTopAppBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "")
        },
        backgroundColor = Color.White,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    )
}