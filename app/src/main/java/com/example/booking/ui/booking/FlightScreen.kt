package com.example.booking.ui.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booking.R
import com.example.booking.ui.components.TitleWithBackButton


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FlightScreen(
    onFilterClicked: () -> Unit = {},
    onBack: () -> Unit = {},
    onTicketClicked: () -> Unit = {},
    viewModel: TransportBookingViewModel = viewModel()
) {
    Surface(
        color = colorResource(id = R.color.bg)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val departureDate by viewModel.departureDate.collectAsState()
            val arrivalDate by viewModel.arrivalDate.collectAsState()
            val departureCity by viewModel.departureCity.collectAsState()
            val arrivalCity by viewModel.arrivalCity.collectAsState()
            val departureHour by viewModel.departureHour.collectAsState()

            val listDate = viewModel.getFormattedDates()

            TitleWithBackButton(
                title = "Flights",
                onBack = onBack
            )

            DateSelectionRow(listDate = listDate) {}

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
//                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${stringResource(R.string.flights_available)}\nfrom $departureCity to $arrivalCity",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    ),
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                IconButton(
                    onClick = onFilterClicked,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(
                            colorResource(id = R.color.peach),
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    Icon(Icons.Default.Tune, contentDescription = "Search")
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) {
                    FlightCard(
                        departureCity = departureCity,
                        arrivalCity = arrivalCity,
                        departureDate = viewModel.formatDate(departureDate),
                        departureHour = departureHour,
                        onTicketClicked = onTicketClicked
                    )
                }
            }
        }
    }
}

@Composable
fun FlightCard(
    departureCity: String = "New York",
    arrivalCity: String = "London",

    departureDate: String = "1 Jun",
    departureHour: String = "10:00",
    onTicketClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .wrapContentSize()
            .clickable(onClick = onTicketClicked)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.flightselection),
            contentDescription = "Flight Image"
        )

        Text(
            text = departureCity,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            modifier = Modifier.offset(x = 18.dp, y = 23.dp)
        )

        Text(
            text = arrivalCity,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            modifier = Modifier.offset(x = 250.dp, y = 23.dp)
        )

        Text(
            text = departureDate,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            ),
            modifier = Modifier.offset(x = 20.dp, y = 132.dp)
        )

        Text(
            text = departureHour,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            ),
            modifier = Modifier.offset(x = 97.dp, y = 132.dp)
        )
    }
}

@Composable
fun DateSelectionRow(
    listDate: List<Pair<String, String>>,
    onDateSelected: (String) -> Unit = {}
) {
    var selectedDate: String? by remember { mutableStateOf(null) }

    LazyRow(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(listDate) { datePair ->
            val backgroundColor =
                if (datePair.first == selectedDate) colorResource(id = R.color.beige)
                else Color.Transparent

            Box(
                Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .clickable {
                        onDateSelected(datePair.first)
                        selectedDate = datePair.first
                    },
                Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = datePair.second,
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp)
                    )

                    Text(
                        text = datePair.first,
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp,
                            fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}