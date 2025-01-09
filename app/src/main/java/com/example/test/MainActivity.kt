package com.example.test

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.data.OfflineUsersRepository
import com.example.test.data.UsersDatabase
import com.example.test.data.UsersRepository
import com.example.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = UsersDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()
        val reservationDao = database.reservationDao()
        val requestDao = database.requestDao()
        val repository = OfflineUsersRepository(userDao,reservationDao,requestDao)
        val viewModel = UserViewModel(repository)
        val viewModelReservation = ReservationViewModel(repository)
        val viewModelRequest = RequestViewModel(repository)

        enableEdgeToEdge()
        setContent {
            SmartDorm(viewModel,viewModelReservation,viewModelRequest,repository)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SmartDorm(viewModel: UserViewModel, viewModelReservation: ReservationViewModel,viewModelRequest: RequestViewModel,repository: UsersRepository){
    TestTheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "login", modifier = Modifier){
            composable(route = "login"){
                viewModel.updatePassword("")
                viewModel.updateUsername("")
                LogInScreen(onLogInClicked = {
                    navController.navigate("mainPage")}, onSignUpClicked = {
                    navController.navigate("signUp")
                },viewModel = viewModel)
            }
            composable(route="mainPage"){
                MainPageScreen(onBookBtnClick = {
                    navController.navigate("bookASpot")
                },
                    onSubmitRequestClick = {
                        navController.navigate("submitRequest")
                    },
                    viewModel = viewModel,
                    onManageBookingsBtnClick = {
                        navController.navigate("manageBookings")
                    },
                    onSeeRequestsBtnClick = {
                        navController.navigate("seeRequests")
                    })
            }
            composable(route = "bookASpot"){
                BookASpotScreen(
                    viewModel = viewModel,
                    viewModelReservation = viewModelReservation,
                    navigation ={
                        navController.navigate("mainPage")
                    } )
            }

            composable(route = "submitRequest"){
                SubmitRequestScreen(navigation = {
                    navController.navigate("mainPage")
                }, viewModelRequest = viewModelRequest,
                    viewModelUser = viewModel)
            }

            composable(route = "signUp"){
                viewModel.updatePassword("")
                viewModel.updateUsername("")
                SignUpScreen(onBtnClicked = {
                    navController.navigate("mainPage")
                }, viewModel = viewModel)
            }

            composable(route= "manageBookings") {
                ManageBookingsScreen(viewModel,viewModelReservation, repository)
            }

            composable(route = "seeRequests"){
                SeeRequestsScreen(viewModel,viewModelRequest)
            }

        }
    }
}



