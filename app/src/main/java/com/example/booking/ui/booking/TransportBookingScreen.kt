package com.example.booking.ui.booking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.DirectionsBoat
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.Train
import androidx.compose.material.icons.outlined.Tram
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booking.R
import com.example.booking.ui.components.ButtonWithTextAndIcon
import com.example.booking.ui.components.CustomIconButton
import com.example.booking.ui.components.DatePickerDialogDemo
import com.example.booking.ui.components.LabeledDropdownMenu
import com.example.booking.ui.components.SelectableRowText
import com.example.booking.ui.components.TextFieldWithLeadingIcon
import com.example.booking.ui.components.TitleWithBackButton
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransportBookingScreen(
    onBack: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    viewModel: TransportBookingViewModel = viewModel()
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    val departureCity by viewModel.departureCity.collectAsState()
    val arrivalCity by viewModel.arrivalCity.collectAsState()
    val selectedOption by viewModel.selectedClass.collectAsState()

    val departureCityTextFieldValue by viewModel.departureCityTextFieldValue.collectAsState()
    val arrivalCityTextFieldValue by viewModel.arrivalCityTextFieldValue.collectAsState()

    val departureDate by viewModel.departureDate.collectAsState()
    val arrivalDate by viewModel.arrivalDate.collectAsState()

    val context = LocalContext.current

    Surface(
        color = colorResource(id = R.color.bg),
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .weight(weight = 1f, fill = false)
            ) {
                TitleWithBackButton(
                    title = "Transport Booking",
                    onBack = onBack
                )

                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
                {
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                LabeledDropdownMenu(
                                    label = "From",
                                    options = CityOptions.departureOptions,
                                    selectedOption = departureCity,

                                    textFieldValue = departureCityTextFieldValue,

                                    onOptionSelected = { viewModel.updateDepartureCity(it) }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                LabeledDropdownMenu(
                                    label = "To",
                                    options = CityOptions.arrivalOptions,
                                    selectedOption = arrivalCity,

                                    textFieldValue = arrivalCityTextFieldValue,

                                    onOptionSelected = { viewModel.updateArrivalCity(it) }
                                )

                            }

                            IconButton(
                                onClick = { viewModel.swapCities() },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 16.dp)
                                    .background(
                                        colorResource(id = R.color.peach),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                            ) {
                                Icon(
                                    Icons.Default.SwapVert,
                                    contentDescription = "Swap Cities",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .safeDrawingPadding()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    var departureDateText =
                        remember { mutableStateOf(departureDate.format(dateFormatter)) }
                    var arrivalDateText =
                        remember { mutableStateOf(arrivalDate.format(dateFormatter)) }
                    var showDepartureDatePicker by remember { mutableStateOf(false) }
                    var showArrivalDatePicker by remember { mutableStateOf(false) }


                    OutlinedTextField(
                        value = departureDateText.value,
                        enabled = false,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        label = { Text(text = "Departure Date", color = Color.Gray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Black,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            disabledBorderColor = Color.Transparent,
                        ),
                        modifier = Modifier
//                        .wrapContentSize()
                            .weight(1f)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                showDepartureDatePicker = true
                                //log in logcat
                                Log.d("date picker 1", "Text field 1 for date picker")
                            }
                    )

                    if (showDepartureDatePicker) {
                        DatePickerDialogDemo { timestamp ->
                            val selectedDate = Instant.ofEpochMilli(timestamp)
                                .atZone(ZoneId.systemDefault()).toLocalDate()
                            viewModel.updateDepartureDate(selectedDate)
                            departureDateText.value = selectedDate.format(dateFormatter)
                            showDepartureDatePicker = false
                        }
                    }

                    OutlinedTextField(
                        value = arrivalDateText.value,
                        enabled = false,
                        onValueChange = {},
                        readOnly = true,

                        singleLine = true,
                        label = { Text(text = "Arrival Date", color = Color.Gray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Black,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            disabledBorderColor = Color.Transparent,
                        ),
                        modifier = Modifier
//                        .wrapContentSize()
                            .weight(1f)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                showArrivalDatePicker = true
                                Log.d("date picker 2", "Text field 2 for date picker")
                            }
                    )

                    if (showArrivalDatePicker) {
                        DatePickerDialogDemo { timestamp ->
                            val selectedDate = Instant.ofEpochMilli(timestamp)
                                .atZone(ZoneId.systemDefault()).toLocalDate()
                            viewModel.updateArrivalDate(selectedDate)
                            arrivalDateText.value = selectedDate.format(dateFormatter)
                            showArrivalDatePicker = false
                        }
                    }
                }

                Text(
                    text = "Passenger & Luggage",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextFieldWithLeadingIcon(
                        value = viewModel.passengers.toString(),
                        onValueChange = { newValue ->
                            if (newValue.isNotEmpty() && newValue.last().isDigit()) {
                                val newIntValue = newValue.last().toString().toInt()
                                viewModel.passengers = newIntValue
                            }
                        },
                        leadingIcon = R.drawable.person,
                    )

                    TextFieldWithLeadingIcon(
                        value = viewModel.babyLuggage.toString(),
                        onValueChange = { newValue ->
                            if (newValue.isNotEmpty() && newValue.last().isDigit()) {
                                val newIntValue = newValue.last().toString().toInt()
                                viewModel.passengers = newIntValue
                            }
                        },
                        leadingIcon = R.drawable.baby
                    )

                    TextFieldWithLeadingIcon(
                        value = viewModel.animalLuggage.toString(),
                        onValueChange = { newValue ->
                            if (newValue.isNotEmpty() && newValue.last().isDigit()) {
                                val newIntValue = newValue.last().toString().toInt()
                                viewModel.passengers = newIntValue
                            }
                        },
                        leadingIcon = R.drawable.dog
                    )

                    TextFieldWithLeadingIcon(
                        value = viewModel.valiLuggage.toString(),
                        onValueChange = { newValue ->
                            if (newValue.isNotEmpty() && newValue.last().isDigit()) {
                                val newIntValue = newValue.last().toString().toInt()
                                viewModel.passengers = newIntValue
                            }
                        },
                        leadingIcon = R.drawable.vali
                    )

                }

                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(text = "Class:", color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    SelectableRowText(
                        options = listOf("Economy", "Business"),
                        selectedOption = selectedOption,
                        onOptionSelected = { viewModel.updateSelectedClass(it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Transport:", color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.wrapContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CustomIconButton(
                            icon = Icons.Outlined.Flight,
                            onClick = { viewModel.setTransportToFlight() }
                        )
                        CustomIconButton(
                            icon = Icons.Outlined.DirectionsBoat,
                            onClick = { viewModel.setTransportToFlight() }
                        )
                        CustomIconButton(
                            icon = Icons.Outlined.Tram,
                            onClick = { viewModel.setTransportToFlight() }
                        )
                        CustomIconButton(
                            icon = Icons.Outlined.Train,
                            onClick = { viewModel.setTransportToFlight() }
                        )
                    }

                }
            }

            ButtonWithTextAndIcon(
                text = "Search",
                buttonColor = colorResource(id = R.color.peach),
                textColor = Color.White,
                onClick = onSearchClick
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TransportBookingScreenPreview() {
    TransportBookingScreen()
}