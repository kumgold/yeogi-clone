package com.test_application.hollyscompose.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.ui.compose.HollysTopAppBar
import com.test_application.hollyscompose.ui.theme.HollysTypography
import com.test_application.hollyscompose.ui.theme.MainBottomStartRoundShape
import com.test_application.hollyscompose.ui.theme.Red

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
                    modifier = Modifier
                        .constrainAs(delivery) {
                            top.linkTo(parent.top)
                        }
                        .zIndex(1.0f),
                    mainColor = Color.White,
                    subColor = Color.Black,
                    topMargin = 30.dp,
                    title = R.string.delivery_button_title,
                    subtitle = R.string.delivery_button_subtitle
                )
                HomeDropDownButton(
                    modifier = Modifier.constrainAs(order) {
                        top.linkTo(parent.top)
                    },
                    mainColor = Red,
                    subColor = Color.White,
                    topMargin = 150.dp,
                    title = R.string.smart_order_button_title,
                    subtitle = R.string.smart_order_button_subtitle
                )
            }
        }
    }
}

@Composable
fun HomeDropDownButton(
    modifier: Modifier,
    mainColor: Color,
    subColor: Color,
    topMargin: Dp = 0.dp,
    @StringRes title: Int,
    @StringRes subtitle: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(mainColor, shape = MainBottomStartRoundShape)
            .padding(35.dp, topMargin, 20.dp, 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(id = title),
                color = subColor,
                fontWeight = HollysTypography.h1.fontWeight,
                fontSize = HollysTypography.h1.fontSize
            )
            Text(
                text = stringResource(id = subtitle),
                color = subColor,
                fontWeight = HollysTypography.body1.fontWeight,
                fontSize = HollysTypography.body1.fontSize
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
           modifier = Modifier
               .size(30.dp)
               .clip(CircleShape)
               .background(subColor)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = mainColor
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
                modifier = Modifier
                    .constrainAs(delivery) {
                        top.linkTo(parent.top)
                    }
                    .zIndex(1.0f),
                mainColor = Color.Black,
                subColor = Color.White,
                topMargin = 40.dp,
                title = R.string.delivery_button_title,
                subtitle = R.string.delivery_button_subtitle
            )
            HomeDropDownButton(
                modifier = Modifier.constrainAs(order) {
                    top.linkTo(parent.top)
                },
                mainColor = Color.Red,
                subColor = Color.White,
                topMargin = 160.dp,
                title = R.string.smart_order_button_title,
                subtitle = R.string.smart_order_button_subtitle
            )
        }
    }
}