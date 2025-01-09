package com.example.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme

@Composable
fun SubmitRequestForm(
    modifier: Modifier = Modifier,
    navigation: () -> Unit,
    viewModelRequest: RequestViewModel,
    viewModelUser: UserViewModel
) {
    var request by remember { mutableStateOf("") }
    val emptyInputDialog  = remember { mutableStateOf(false) }
    val viewUserState by viewModelUser.userState.collectAsState()
    val successfulDialog = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Hello user!");
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                maxLines = 40,
                label = { Text(text = "Write your request (e.g. door lock damaged in room 100)") },
                value = request,
                onValueChange = { newText ->
                    request = newText
                }
            )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 12.dp,
                    end = 24.dp,
                    bottom = 12.dp
                ),
                onClick = {
                    if (request.isEmpty()) {
                        emptyInputDialog.value = true
                    }
                    else{
                        viewModelRequest.insertRequest(viewUserState.userId,request)
                        successfulDialog.value = true
                    }
                }

            ) {
                Text("Submit")
            }
            if(emptyInputDialog.value){
                AlertDialog(
                    onDismissRequest = { emptyInputDialog.value = false },
                    title = { Text("Empty input!") },
                    text = {Text("Type a short description of your request!")},
                    containerColor = Color.White,
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Magenta),
                            onClick = {
                                emptyInputDialog.value = false
                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }
            if(successfulDialog.value)
                AlertDialog(
                    onDismissRequest = { successfulDialog.value = false },
                    title = { Text("Successful submission!") },
                    containerColor = Color.White,
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Magenta),
                            onClick = {
                                successfulDialog.value = false
                                navigation()

                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
        }
    }
}


//@Preview(showBackground = true, widthDp = 320, heightDp = 500)
//@Composable
//fun SubmitRequestFormPreview(){
//    TestTheme {
//        SubmitRequestForm( onSubmitBtnClick = {})
//    }
//}

@Composable
fun SubmitRequestScreen(viewModelUser: UserViewModel, viewModelRequest:RequestViewModel, navigation: () -> Unit = {}){
    TestTheme  {
        SubmitRequestForm(viewModelUser = viewModelUser,viewModelRequest = viewModelRequest, navigation = navigation)
    }
}