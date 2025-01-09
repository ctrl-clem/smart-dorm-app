package com.example.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    onBookBtnClick: () -> Unit = {},
    onSubmitRequestClick: () -> Unit = {},
    onManageBookingsBtnClick:() -> Unit = {},
    onSeeRequestsBtnClick:() -> Unit = {},
    viewModel: UserViewModel
) {
    val viewState by viewModel.userState.collectAsState()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Hello " + viewState.firstname + "!");
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 12.dp,
                    end = 24.dp,
                    bottom = 12.dp
                ),
                onClick = onBookBtnClick

            ) {
                Text("Book a spot")
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 12.dp,
                    end = 24.dp,
                    bottom = 12.dp
                ),
                onClick = onSubmitRequestClick

            ) {
                Text("Submit request")
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 12.dp,
                    end = 24.dp,
                    bottom = 12.dp
                ),
                onClick = onManageBookingsBtnClick

            ) {
                Text("Manage my bookings")
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 12.dp,
                    end = 24.dp,
                    bottom = 12.dp
                ),
                onClick = onSeeRequestsBtnClick

            ) {
                Text("See my requests")
            }


        }

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun MainPagePreview(onBookBtnClick: () -> Unit = {} ){
    TestTheme {
        //MainPage(onBookBtnClick = onBookBtnClick,)
    }
}

@Composable
fun MainPageScreen(onBookBtnClick: () -> Unit = {},onSubmitRequestClick: () -> Unit, viewModel: UserViewModel, onManageBookingsBtnClick: () -> Unit, onSeeRequestsBtnClick: () -> Unit){
    TestTheme {
        MainPage(onSeeRequestsBtnClick = onSeeRequestsBtnClick,onBookBtnClick = onBookBtnClick, onSubmitRequestClick = onSubmitRequestClick, viewModel = viewModel, onManageBookingsBtnClick = onManageBookingsBtnClick)
    }
}