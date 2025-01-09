package com.example.test

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun BookASpot(
    viewModel: UserViewModel,
    viewModelReservation: ReservationViewModel,
    navigation: () -> Unit
) {
    //alert dialog for empty slot
    val alertDialog = remember { mutableStateOf(false) }

    //alert dialog for successful booking
    val successfulDialog  = remember { mutableStateOf(false) }

    //user info
    val viewUserState by viewModel.userState.collectAsState()

    // Observe the available slots
    val availableSlots by viewModelReservation.availableSlots.collectAsState(initial = emptyList()) // Provide an initial empty list

    // State for selected reservation and selected day
    var selectedSlot by remember { mutableStateOf<Pair<LocalDate, LocalTime>?>(null) }

    // Group the available slots by date
    val groupedSlots = availableSlots.groupBy { it.first } // Group by LocalDate

    // Get the list of days for the segmented buttons
    val days = groupedSlots.keys.toList()

    // Default selected day is today
    var selectedDay by remember { mutableStateOf(LocalDate.now()) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
   ) {
    Column(verticalArrangement = Arrangement.Top,
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier
    ) {
        TopBar("Book a spot")

        SingleChoiceSegmentedButtonRow {
            days.forEach { day ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = day.dayOfYear, count = days.size),
                    onClick = {
                        selectedDay = day
                        selectedSlot = null // Reset selected slot
                    },
                    modifier = Modifier.weight(1f),
                    selected = day == selectedDay,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = Color.Magenta,
                        inactiveContainerColor = Color.White)

                    ) {
                    Text(text = day.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedDay?.let { day ->
            val slotsForDay = groupedSlots[day] ?: emptyList()

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(slotsForDay) { slot ->
                    val (date, time) = slot

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedSlot == slot,
                            onClick = { selectedSlot = slot }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = time.format(DateTimeFormatter.ofPattern("hh:mm a")),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        } ?: run {
            Text("Please select a day")
        }
    }
        BookButton(
            onClick = {
                if (selectedSlot != null) {
                    selectedSlot?.let { slot ->
                        val (date, time) = slot
                        viewModelReservation.insertReservation(idUser = viewUserState.userId, date=date,time=time)
                        successfulDialog.value = true
                    }
                } else {
                    alertDialog.value = true

                }
            },
        )
        if(alertDialog.value)
            AlertDialog(
                onDismissRequest = { alertDialog.value = false },
                title = { Text("No Time Selected") },
                text = { Text("Please select a time slot before booking.") },
                containerColor = Color.White,
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Magenta),
                        onClick = { alertDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            )

        if(successfulDialog.value)
            AlertDialog(
                onDismissRequest = { successfulDialog.value = false },
                title = { Text("Successful booking!") },
                text = { Text("You can view the booking in My Bookings section!") },
                containerColor = Color.White,
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Magenta),
                        onClick = {
                            successfulDialog.value = false
                            navigation()}
                    ) {
                        Text("Dismiss")
                    }
                }
            )

    }
}



@Composable
fun BookASpotScreen(
    viewModel: UserViewModel,
    viewModelReservation: ReservationViewModel,
    navigation: () -> Unit
){
    TestTheme  {
        BookASpot(viewModel=viewModel,viewModelReservation=viewModelReservation, navigation = navigation)
    }
}

