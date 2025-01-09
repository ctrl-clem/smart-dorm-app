package com.example.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test.data.UserWithRequests
import com.example.test.ui.theme.TestTheme

@Composable
fun RequestsDisplay(
    viewModelUser: UserViewModel = viewModel(),
    viewModelRequest: RequestViewModel = viewModel(factory = RequestViewModel.Factory)
) {
    val viewUserState by viewModelUser.userState.collectAsState()

    // Collect requests for the given user ID
    //val requests by viewModelRequest.getRequestsByUserId(viewUserState.userId).collectAsState(initial = emptyList())

    // UI to display the list of requests

    val requests by viewModelRequest.getRequestsByUserId(viewUserState.userId).collectAsState(
        UserWithRequests()
    )


    Spacer(modifier = Modifier.padding(bottom = 100.dp))
    if (requests.reservations.isEmpty()) {
        Text(
            text = "No requests found.",
            color = MaterialTheme.colorScheme.error
        )
    } else {
        Column() {
            TopBar("Your requests")

            LazyColumn {


                items(requests.reservations) { request ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = "Request: ${request.message}",

                                )

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SeeRequestsScreen(viewModelUser: UserViewModel, viewModelRequest: RequestViewModel){
    TestTheme {
        RequestsDisplay(viewModelUser,viewModelRequest)
    }
}
