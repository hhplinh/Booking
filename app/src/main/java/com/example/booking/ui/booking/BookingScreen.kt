package com.example.booking.ui.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booking.R
import com.example.booking.data.BookingOption
import com.example.booking.data.Datasource

@Composable
fun BookingScreen(
    onBookingSelected: (String) -> Unit
) {
    Surface(color = colorResource(id = R.color.bg))
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Booking",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(16.dp)
            )

            BookingOptionList(
                bookingList = Datasource().loadBookingOptions(),
                onBookingSelected = onBookingSelected
            )
        }
    }
}

@Composable
fun BookingOptionList(
    bookingList: List<BookingOption>,
    modifier: Modifier = Modifier,
    onBookingSelected: (String) -> Unit
) {
    Surface(color = colorResource(id = R.color.darkGreen)){
    LazyColumn(modifier = modifier) {
        items(bookingList) { booking ->
            BookingOptionCard(
                booking = booking,
                modifier = Modifier.padding(8.dp),
                onClick = { onBookingSelected(booking.stringResourceId.toString()) }
            )
        }
        }
    }
}

@Composable
fun BookingOptionCard(
    booking: BookingOption,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(color = colorResource(id = R.color.bg)){
        Card(modifier = modifier) {
            Column {
                Surface (color = colorResource(id = R.color.bg)) {
                    Image(
                        painter = painterResource(booking.imageResourceId),
                        contentDescription = stringResource(booking.stringResourceId),
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(onClick = onClick),
                        contentScale = ContentScale.FillWidth
                    )
                }
//            Text(
//                text = LocalContext.current.getString(booking.stringResourceId),
//                modifier = Modifier.padding(16.dp),
//                style = MaterialTheme.typography.headlineSmall
//            )
            }
        }
    }
}

@Preview
@Composable
private fun BookingOptionCardPreview() {
    BookingOptionCard(BookingOption(R.string.b1,R.drawable.b1), onClick = {})
}