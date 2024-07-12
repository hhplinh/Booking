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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booking.R
import com.example.booking.ui.components.ButtonWithTextAndIcon
import com.example.booking.ui.components.TitleWithBackButton

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeatScreen(
    viewModel: TransportBookingViewModel = viewModel(),
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    var seatMatrix by remember { mutableStateOf(Array(6) { Array(2) { 0 } }) }
    var seatMatrix2 by remember { mutableStateOf(Array(6) { Array(2) { 0 } }) }

    fun toggleSeat(rowIndex: Int, columnIndex: Int) {
        if (seatMatrix[rowIndex][columnIndex] == 0) { // If seat is available
            val updatedMatrix = seatMatrix.copyOf()
            updatedMatrix[rowIndex] = updatedMatrix[rowIndex].copyOf()
            updatedMatrix[rowIndex][columnIndex] = 1 // Mark as selected
            seatMatrix = updatedMatrix
        } else if (seatMatrix[rowIndex][columnIndex] == 1) { // If seat is selected
            val updatedMatrix = seatMatrix.copyOf()
            updatedMatrix[rowIndex] = updatedMatrix[rowIndex].copyOf()
            updatedMatrix[rowIndex][columnIndex] = 2 // Mark as unavailable
            seatMatrix = updatedMatrix
        }
    }

    fun getSelectedSeatNumbers(seatMatrix: Array<Array<Int>>): String {
        val selectedSeats = mutableListOf<String>()
        seatMatrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, state ->
                if (state == 1) {
                    val seatName = "${rowIndex + 1}${('A' + columnIndex)}"
                    selectedSeats.add(seatName)
                }
            }
        }
        return selectedSeats.joinToString()
    }

    Surface(color = colorResource(id = R.color.bg))
    {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TitleWithBackButton(
                title = "Select Seats",
                onBack = onBack
            )
            Text(
                text = "Traveller", color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = colorResource(id = R.color.beige)
                    )
            )
            {
                Text(text = "1", color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp))

            }


            Image(
                painter = painterResource(id = R.drawable.seatstatus),
                contentDescription = "Seat map",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
            {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "A",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.width(38.dp))

                Text(
                    text = "B",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.width(120.dp))

                Text(
                    text = "C",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = "D",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))



            }

            Row(modifier = Modifier.weight(1f, fill = false))
            {
                SeatSelectionMatrix(
                    seatMatrix = seatMatrix,
                    onSeatClick = ::toggleSeat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Column(modifier = Modifier.wrapContentWidth())
                {
                    Spacer(modifier = Modifier.height(32.dp))
                    for (i in 1..6)
                    {
                        Text(
                            text = i.toString(),
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                    }

                }


                SeatSelectionMatrix(
                    seatMatrix = seatMatrix2,
                    onSeatClick = { _, _ -> },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Surface(color = Color.White, modifier = Modifier.weight(1f))
            {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        CustomFrontText(
                            text = "Your seats",
                            textColor = colorResource(id = R.color.darkGreen),
                            fontWeight = FontWeight.Medium
                        )

                        val selectedSeatNumbers = getSelectedSeatNumbers(seatMatrix)
                        //save to view model
                        viewModel.updateSeatNumber(selectedSeatNumbers, 0)

                        CustomFrontText(
                            text = selectedSeatNumbers,
                            textColor = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        CustomFrontText(
                            text = "Total price",
                            textColor = colorResource(id = R.color.darkGreen),
                            fontWeight = FontWeight.Medium
                        )

                        CustomFrontText(
                            text = "$50.00", textColor = Color.Black, fontWeight = FontWeight.Bold
                        )
                    }
                    ButtonWithTextAndIcon(text = "Continue",
                        onClick = onContinue
                    )
                }
            }
        }
    }
}


@Composable
fun CustomFrontText(
    text: String,
    textColor: Color,
    fontWeight: FontWeight
) {
    Text(
        text = text,
        style = androidx.compose.ui.text.TextStyle(
            color = textColor,
            fontSize = 16.sp,
            fontWeight = fontWeight
        )
    )
}

@Composable
fun SeatSelectionMatrix(
    modifier: Modifier = Modifier,
    seatMatrix: Array<Array<Int>>,
    onSeatClick: (Int, Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
    {
        itemsIndexed(seatMatrix) { rowIndex, row ->
            LazyRow(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
            ) {
                itemsIndexed(row) { columnIndex, state ->
                    val color = when (state) {
                        1 -> colorResource(id = R.color.peach) // Selected
                        2 -> colorResource(id = R.color.darkGreen) // Unavailable
                        else -> colorResource(id = R.color.pastelGreen) // Available
                    }
                    Box(modifier = Modifier
                        .clickable(enabled = state == 0) {
                            onSeatClick(
                                rowIndex,
                                columnIndex
                            )
                        }
                        .clip(RoundedCornerShape(4.dp))
                        .background(color)
                        .padding(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SeatScreenPreview() {
    SeatScreen()
}

//
//@Composable
//fun SeatScreen(
//    viewModel: TransportBookingViewModel = viewModel()
//) {
//    var seatMatrix by remember { mutableStateOf(Array(6) { Array(2) { false } }) }
//
//    fun toggleSeat(rowIndex: Int, columnIndex: Int) {
//        // Only allow changing the state if the seat is available (0) or selected (1)
//        if ( ! seatMatrix[rowIndex][columnIndex]) {
//            val updatedMatrix = seatMatrix.copyOf()
//            updatedMatrix[rowIndex] = updatedMatrix[rowIndex].copyOf()
//            updatedMatrix[rowIndex][columnIndex] = true // Mark as selected
//            seatMatrix = updatedMatrix
//        }
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = colorResource(id = R.color.bg)
//    )
//    {
//        Column {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
//                    .weight(weight = 1f, fill = false)
//            )
//            {
//            SeatSelectionMatrix(
//            }
//        }
//
//    }
//}
////
////@Composable
////fun SeatSelectionMatrix(seatMatrix: Array<Array<Boolean>>, onSeatClick: (Int, Int) -> Unit) {
////    LazyColumn(modifier = Modifier.padding(8.dp)) {
////        itemsIndexed(seatMatrix) { rowIndex, row ->
////            LazyRow(modifier = Modifier.padding(8.dp)) {
////                itemsIndexed(row) { columnIndex, seat ->
////                    Box(modifier = Modifier
////                        .clickable {
////                            onSeatClick(rowIndex, columnIndex)
////                            Log.d("Seat", "Clicked on seat at row $rowIndex, column $columnIndex")
////                            Log.d("ValueBoolean", "${seatMatrix[rowIndex][columnIndex]}")
////                            Log.d("sseat", "$seat")
////                        }
////                        .padding(32.dp)
////                        .clip(RoundedCornerShape(4.dp))
////                        .background(
////                            if (seat) colorResource(id = R.color.teal_700) else colorResource(
////                                id = R.color.pastelGreen
////                            )
////                        )
////                        .padding(16.dp))
//////                        contentAlignment = Alignment.Center)
////                }
////            }
////        }
////    }
////}
//
//
//@Composable
//fun SeatSelectionMatrix(seatMatrix: Array<Array<Int>>, onSeatClick: (Int, Int) -> Unit) {
//    LazyColumn(modifier = Modifier.padding(8.dp)) {
//        itemsIndexed(seatMatrix) { rowIndex, row ->
//            LazyRow(modifier = Modifier.padding(8.dp)) {
//                itemsIndexed(row) { columnIndex, state ->
//                    val color = when (state) {
//                        1 -> colorResource(id = R.color.peach) // Selected
//                        2 -> colorResource(id = R.color.darkGreen) //Unavailable
//                        else -> colorResource(id = R.color.pastelGreen) // Available
//                    }
//                    Box(modifier = Modifier
//                        .clickable(enabled = state == 0) {
//                            onSeatClick(rowIndex, columnIndex)
//                        }
//                        .padding(32.dp)
//                        .clip(RoundedCornerShape(4.dp))
//                        .background(color)
//                        .padding(16.dp))
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun SeatScreenPreview() {
//    SeatScreen()
//}