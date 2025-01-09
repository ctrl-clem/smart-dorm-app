package com.example.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
fun SignUpForm(
    modifier: Modifier = Modifier,
    onBtnClicked: () -> Unit,
    viewModel: UserViewModel

    ){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Welcome to SmartDorm!")
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val viewState by viewModel.userState.collectAsState()
            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                label = { Text(text = "First name") },
                value = viewModel.firstname,
                isError = viewState.firstnameError.isNotEmpty(),
                onValueChange = { viewModel.updateFirstname(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (viewState.firstnameError.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewState.firstnameError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                label = { Text(text = "Surname") },
                value = viewState.surname,
                onValueChange = { viewModel.updateSurname(it) },
                isError = viewState.surnameError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (viewState.surnameError.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewState.surnameError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )

            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                label = { Text(text = "Email") },
                value = viewState.email,
                isError = viewState.emailError.isNotEmpty(),
                onValueChange = { viewModel.updateEmail(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (viewState.emailError.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewState.emailError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )


            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                label = { Text(text = "Username") },
                value = viewState.username,
                isError = viewState.usernameError.isNotEmpty() ,
                onValueChange = { viewModel.updateUsername(it) },
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

                },
            )


            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Password") },
                value = viewState.password,
                onValueChange = { viewModel.updatePassword(it) },
                isError = viewState.passwordError.isNotEmpty(),
                supportingText = {
                    if (viewState.passwordError.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewState.passwordError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                },

                )


            OutlinedTextField(
                modifier = Modifier.padding(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Confirm password") },
                value = viewState.confirmPassword,
                isError = viewState.confirmPasswordError.isNotEmpty(),
                onValueChange = { viewModel.updateConfirmPassword(it) },
                supportingText = {
                    if (viewState.confirmPasswordError.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = viewState.confirmPasswordError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
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
                    if (viewModel.isSingUpFormValid()) {
                        viewModel.checkUsernameExists { usernameExists ->
                            if (!usernameExists) {
                                viewModel.addUser()
                                viewModel.login()
                                onBtnClicked()
                            }
                        }
                    }
                }

            ) {
                Text("Sign up")
            }

        }
    }

}


@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun SignUpFormPreview(){
    TestTheme {
        //SignUpForm (onBtnClicked = {}, viewModel = UserViewModel())
    }
}

@Composable
fun SignUpScreen(onBtnClicked: () -> Unit, viewModel: UserViewModel){
    SignUpForm (onBtnClicked = onBtnClicked, viewModel = viewModel)
}