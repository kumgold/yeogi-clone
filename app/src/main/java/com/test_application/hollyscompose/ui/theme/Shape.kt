package com.test_application.hollyscompose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val MainBottomStartRoundShape = RoundedCornerShape(bottomStart = 50.dp)

val DefaultCardRoundShape = RoundedCornerShape(20.dp)