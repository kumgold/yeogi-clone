package com.test_application.hollyscompose.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.ui.compose.HollysTopAppBar
import com.test_application.hollyscompose.ui.theme.MainBottomStartRoundShape
import com.test_application.hollyscompose.ui.theme.Red

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = { HollysTopAppBar() },
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.user_intro_comment),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center
                )

                HomeTopLayoutBarAndRoundButton()
            }


            Column {
                HomeIconButtonLayout()
                HomeIconButtonLayout()
            }


            Box(
                modifier = Modifier
                    .weight(1f, false)
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = Color.Blue)
            )
        }
    }
}

@Composable
fun HomeTopLayoutBarAndRoundButton() {
    ConstraintLayout {
        val (delivery, order) = createRefs()

        HomeDropDownButton(
            modifier = Modifier
                .constrainAs(delivery) {
                    top.linkTo(parent.top)
                }
                .zIndex(1.0f)
                .fillMaxWidth()
                .clip(MainBottomStartRoundShape)
                .background(Color.White)
                .clickable { }
                .padding(35.dp, 40.dp, 20.dp, 40.dp),
            title = R.string.delivery_button_title,
            subtitle = R.string.delivery_button_subtitle
        )
        HomeDropDownButton(
            modifier = Modifier
                .constrainAs(order) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .clip(MainBottomStartRoundShape)
                .background(Red)
                .clickable { }
                .padding(35.dp, 150.dp, 20.dp, 40.dp),
            mainColor = Red,
            subColor = Color.White,
            thirdColor = Color.White,
            title = R.string.smart_order_button_title,
            subtitle = R.string.smart_order_button_subtitle,
            dropdownIcon = Icons.Filled.KeyboardArrowRight
        )
    }
}

@Composable
fun HomeDropDownButton(
    modifier: Modifier,
    mainColor: Color = MaterialTheme.colors.background,
    subColor: Color = MaterialTheme.colors.surface,
    thirdColor: Color = Red,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    dropdownIcon: ImageVector = Icons.Filled.KeyboardArrowDown
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(id = title),
                color = subColor,
                style = MaterialTheme.typography.h1
            )
            Text(
                text = stringResource(id = subtitle),
                color = subColor,
                style = MaterialTheme.typography.body1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
           modifier = Modifier
               .size(30.dp)
               .clip(CircleShape)
               .background(thirdColor)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = dropdownIcon,
                contentDescription = null,
                tint = mainColor
            )
        }
    }
}

@Composable
fun HomeIconButtonLayout() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        HomeIconButton()
        HomeIconButton()
        HomeIconButton()
    }
}

@Composable
fun HomeIconButton() {
    Column(
        modifier = Modifier.clickable {  },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(30.dp, 10.dp)
                .size(50.dp),
            imageVector = Icons.Filled.Face,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
        )
        Text(
            text = "쿠폰",
            color = MaterialTheme.colors.surface,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
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
                title = R.string.delivery_button_title,
                subtitle = R.string.delivery_button_subtitle
            )
            HomeDropDownButton(
                modifier = Modifier.constrainAs(order) {
                    top.linkTo(parent.top)
                },
                mainColor = Color.Red,
                subColor = Color.White,
                title = R.string.smart_order_button_title,
                subtitle = R.string.smart_order_button_subtitle
            )
        }
    }
}