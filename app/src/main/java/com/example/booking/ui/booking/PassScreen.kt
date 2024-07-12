package com.example.booking.ui.booking


import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booking.R
import com.example.booking.ui.components.ButtonWithTextAndIcon
import com.example.booking.ui.components.TitleWithBackButton

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PassScreen(
    onBack: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    viewModel: TransportBookingViewModel = viewModel()
) {
    var context = LocalContext.current

    val departureCity by viewModel.departureCity.collectAsState()
    val arrivalCity by viewModel.arrivalCity.collectAsState()

    val departureDateState = viewModel.departureDate.collectAsState()
    val departureDate = viewModel.formatDate(departureDateState.value)

    val departureHour by viewModel.departureHour.collectAsState()
//    val seatNumber by viewModel.seatNumber.collectAsState()

    val seatNumbers by viewModel.seatNumbers.collectAsState()


    fun shareTicketInfo(ticketInfo: TicketInfo, shouldShare: Boolean, context: Context) {
        if (shouldShare) {
            val shareContent =
                "Ticket Info: Departure - ${ticketInfo.departureCity}, Arrival - ${ticketInfo.arrivalCity}, Date - ${ticketInfo.departureDate}, Time - ${ticketInfo.departureHour}, Seat - ${ticketInfo.seatNumber}"
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            ContextCompat.startActivity(context, shareIntent, null)
        }
    }
    Surface(color = colorResource(id = R.color.bg))
    {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column()
            {
                TitleWithBackButton(
                    title = "Boarding Pass",
                    onBack = onBack
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(weight = 1f, fill = false)
                ) {
//                    item {
//                        PassTicket(
//                            departureCity = departureCity,
//                            arrivalCity = arrivalCity,
//                            departureDate = departureDate,
//                            departureHour = departureHour,
//                            seatNumber = seatNumber
//                        )
//                    }
                    if (seatNumbers.isEmpty()) {
                        viewModel.addSeatNumber("1A")
                    }
                    items(seatNumbers.size) { index -> // Iterate over seat numbers
                        PassTicket(
                            departureCity = departureCity,
                            arrivalCity = arrivalCity,
                            departureDate = departureDate,
                            departureHour = departureHour,
                            seatNumber = seatNumbers[index] // Pass each seat number
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                ButtonWithTextAndIcon(
                    text = "Return to Home",
                    onClick = onHomeClicked,
                    modifier = Modifier.weight(1f)
                )

                ButtonWithTextAndIcon(
                    text = "Share ticket",
                    onClick = {
                        shareTicketInfo(
                            TicketInfo(
                                departureCity = departureCity,
                                arrivalCity = arrivalCity,
                                departureDate = departureDate,
                                departureHour = departureHour,
                                seatNumber = seatNumbers[0]
                            ),
                            shouldShare = true,
                            context = context
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

            }
        }
    }


}

//@Composable
//fun ShareTicketIntent(shareContent: String) {
//    val context = LocalContext.current
//    val intent = Intent().apply {
//        action = Intent.ACTION_SEND
//        putExtra(Intent.EXTRA_TEXT, shareContent)
//        type = "text/plain"
//    }
//    val shareIntent = Intent.createChooser(intent, null)
//    startActivity(context, shareIntent, null)
//}

@Composable
fun PassTicket(
    departureCity: String,
    arrivalCity: String,
    departureDate: String,
    departureHour: String,
    seatNumber: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.passticket),
            contentDescription = "Plane"
        )

        Text(
            text = departureCity,
            modifier = Modifier
                .offset(x = 24.dp, y = 280.dp),
            color = colorResource(id = R.color.black)
        )

        Text(
            text = arrivalCity,
            modifier = Modifier
                .offset(x = 270.dp, y = 280.dp),
            color = colorResource(id = R.color.black)
        )

        Text(
            text = departureDate,
            modifier = Modifier.offset(x = 20.dp, y = 340.dp),
            color = colorResource(id = R.color.black)
        )

        Text(
            text = departureHour,
            modifier = Modifier.offset(x = 80.dp, y = 340.dp),
            color = colorResource(id = R.color.black)
        )

        Text(
            text = seatNumber,
            modifier = Modifier.offset(x = 300.dp, y = 415.dp),
            color = colorResource(id = R.color.black)
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewPassScreen() {
    PassScreen()
}