package com.example.test

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.test.data.Reservation
import com.example.test.data.User
import com.example.test.data.UserWithReservations
import com.example.test.data.UsersRepository
import com.example.test.ui.theme.TestTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ManageBookings(
    viewModel: UserViewModel,
    viewModelReservation: ReservationViewModel,
    repository: UsersRepository
) {
    val successfulDialog  = remember { mutableStateOf(false) }
    val viewUserState by viewModel.userState.collectAsState()
    val id = viewUserState.userId
    val viewModelBookings = ManageBookingsViewModel(repository, id)
    val bookings by viewModelBookings.bookings.collectAsState(
        UserWithReservations(
            user = User(
                -1,
                "",
                "",
                "",
                "",
                ""
            ), reservations = emptyList()
        )
    )


    if (!bookings?.reservations.isNullOrEmpty()) {
        Column() {
            TopBar("Your bookings")

            LazyColumn {

                bookings?.let {
                    items(it.reservations) { reservation ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Date: ${reservation.day}",
                                )
                                Text(
                                    text = "Time: ${reservation.time}",
                                )
                            }

                            Spacer(modifier = Modifier.padding(horizontal = 40.dp))
                            if((reservation.day.isAfter(LocalDate.now())) ||
                                (reservation.day.isEqual(LocalDate.now()) && reservation.time.isAfter(LocalTime.now())))
                                Button(
                                    onClick = {
                                        viewModelBookings.deleteReservation(reservation)
                                        successfulDialog.value=true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
                                    ) {
                                    Text("Cancel booking")
                                }
                        }
                    }
                }
            }
        }
    }

    else {
        Text(
            text = "No bookings found.",
            color = MaterialTheme.colorScheme.error
        )

    }
    if(successfulDialog.value)
        AlertDialog(
            onDismissRequest = { successfulDialog.value = false },
            title = { Text("Successful canceling!") },
            containerColor = Color.White,
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color.Magenta),
                    onClick = {
                        successfulDialog.value = false
                        }
                ) {
                    Text("Dismiss")
                }
            }
        )
}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ManageBookingsScreen(viewModel: UserViewModel, viewModelReservation: ReservationViewModel,repository: UsersRepository){
    TestTheme {
        ManageBookings(viewModel,viewModelReservation,repository)
    }
}