package com.test_application.hollyscompose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.test_application.hollyscompose.ui.compose.HollysTopAppBar

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = { HollysTopAppBar() },
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "따뜻한 커피 어때요",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            ConstraintLayout {
                val (delivery, order) = createRefs()

                HomeDropDownButton(
                    modifier = Modifier.constrainAs(delivery) {
                        top.linkTo(parent.top)
                    }.zIndex(1.0f),
                    mainColor = Color.White,
                    secondColor = Color.Black,
                    topMargin = 30.dp
                )
                HomeDropDownButton(
                    modifier = Modifier.constrainAs(order) {
                        top.linkTo(parent.top, margin = 90.dp)
                    },
                    mainColor = Color.Red,
                    secondColor = Color.White,
                    topMargin = 80.dp
                )
            }
        }
    }
}

@Composable
fun HomeDropDownButton(
    modifier: Modifier,
    mainColor: Color,
    secondColor: Color,
    topMargin: Dp = 0.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(mainColor, shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 50.dp))
            .padding(40.dp, topMargin, 20.dp, 40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = "딜리버리",
                color = secondColor,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "이제는 집에서 즐기는 할리스",
                color = secondColor
            )
        }
    }
}

@Composable
@Preview
private fun HomeDropDownButtonPreView() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout {
            val (delivery, order) = createRefs()

            HomeDropDownButton(
                modifier = Modifier.constrainAs(delivery) {
                    top.linkTo(parent.top)
                }.zIndex(1.0f),
                mainColor = Color.Black,
                secondColor = Color.White
            )
            HomeDropDownButton(
                modifier = Modifier.constrainAs(order) {
                    top.linkTo(parent.top, margin = 140.dp)
                }.zIndex(0.0f),
                mainColor = Color.Red,
                secondColor = Color.White
            )
        }
    }
}