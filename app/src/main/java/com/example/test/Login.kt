package com.example.test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme

@Composable
fun LogInForm(
    modifier: Modifier = Modifier,
    onLogInClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = {},
    viewModel: UserViewModel

    ){
    val viewState by viewModel.userState.collectAsState()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar("Welcome to SmartDorm!")
        Column (
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                label = { Text(text = "Username") },
                value = viewState.username,
                isError = viewState.usernameError.isNotEmpty() || !viewState.isUsernameCorrect,
                onValueChange = {viewModel.updateUsername(it)},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (viewState.usernameError.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewState.usernameError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if(!viewState.isUsernameCorrect){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Incorrect Username",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )


            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Password") },
                value = viewState.password,
                onValueChange = {viewModel.updatePassword(it)},
                isError = viewState.passwordError.isNotEmpty() || !viewState.isPasswordCorrect,
                supportingText = {
                    if (viewState.passwordError.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewState.passwordError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if(!viewState.isPasswordCorrect){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Incorrect Password",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },

                )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.padding(start = 24.dp, top = 12.dp, end = 24.dp, bottom = 12.dp),
                onClick = {
                    if(viewModel.isLoginFormValid()){
                        viewModel.validateLoginInput { isSuccess ->
                            if (isSuccess) {
                                viewModel.login()
                                onLogInClicked()
                            }
                        }

                    }

                }

            ) {
                Text("Log in")
            }


            Row {
                Text("Don't have an account? ")
                Text(text = "Sign up!", modifier = Modifier.clickable {
                    onSignUpClicked()
                }, color = Color.Magenta)

            }
        }

    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun LogInFormPreview(){
    TestTheme {
        //LogInForm(onLogInClicked = {}, viewModel = UserViewModel())
    }
}

@Composable
fun LogInScreen(onLogInClicked: () -> Unit= {}, onSignUpClicked: () -> Unit, viewModel: UserViewModel){
    LogInForm(onLogInClicked = onLogInClicked, onSignUpClicked = onSignUpClicked, viewModel = viewModel)
}