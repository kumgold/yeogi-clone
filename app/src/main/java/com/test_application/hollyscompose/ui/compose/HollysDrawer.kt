package com.test_application.hollyscompose.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HollysDrawer(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background),
    ) {

    }
}

@Preview
@Composable
private fun HollysDrawerPreview() {
    HollysDrawer(modifier = Modifier.fillMaxSize())
}