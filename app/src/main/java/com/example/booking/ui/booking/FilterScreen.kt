package com.example.booking.ui.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booking.R
import com.example.booking.ui.components.ButtonWithTextAndIcon
import com.example.booking.ui.components.CustomIconButton
import com.example.booking.ui.components.CustomTextFieldWithLabel
import com.example.booking.ui.components.SelectableRowText
import com.example.booking.ui.components.TitleWithBackButton

const val defaultDepartureTime = "12AM - 06AM"
const val defaultArrivalTime = "12AM - 06AM"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilterScreen(
    onBack: () -> Unit = {},
    viewModel: TransportBookingViewModel = viewModel()
) {
    var selectedDepartureTime: String by remember { mutableStateOf(defaultDepartureTime) }
    var selectedArrivalTime: String by remember { mutableStateOf(defaultArrivalTime) }

    var priceFrom by remember { mutableStateOf("") }
    var priceTo by remember { mutableStateOf("") }
    var priceRange by remember { mutableStateOf(0f..250f) }

    var isReset by remember { mutableStateOf(false) }


    fun resetToDefault() {
        isReset = true
        selectedDepartureTime = defaultDepartureTime
        selectedArrivalTime = defaultArrivalTime
        priceFrom = ""
        priceTo = ""
        priceRange = 0f..250f
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.bg)
    )
    {
        Column {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)
            )
            {
                TitleWithBackButton(
                    title = "Filter",
                    onBack = onBack
                )
                Text(
                    text = "Departure",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))


                SelectableRowText(
                    options = departureTime,
                    selectedOption = selectedDepartureTime,
                    onOptionSelected = { selectedOption ->
                        val index = departureTime.indexOf(selectedOption)
                        viewModel.updateDepartureHour(index)
                        selectedDepartureTime = selectedOption

                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Arrival",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                SelectableRowText(
                    options = arrivalTime,
                    selectedOption = selectedArrivalTime,
                    onOptionSelected = { selectedOption ->
                        val index = arrivalTime.indexOf(selectedOption)
                        viewModel.updateArrivalHour(index)
                        selectedDepartureTime = selectedOption
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Price",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                RangeSlider(
                    value = priceRange,
                    steps = 24,
                    valueRange = 0f..250f,
                    onValueChange = {
                        priceRange = it
                    },
                    onValueChangeFinished = {
                        priceFrom = priceRange.start.toInt().toString()
                        priceTo = priceRange.endInclusive.toInt().toString()
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = colorResource(id = R.color.darkGreen),
                        activeTrackColor = colorResource(id = R.color.darkGreen),
                        inactiveTrackColor = colorResource(id = R.color.teal_700)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                )
                {

                    CustomTextFieldWithLabel(
                        label = "From",
                        value = priceFrom,
                        onValueChange = { priceFrom = it },
                        modifier = Modifier
                            .weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    CustomTextFieldWithLabel(
                        label = "To",
                        value = priceTo,
                        onValueChange = { priceTo = it },
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Facilities",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomIconButton(
                        icon = Icons.Outlined.LocalCafe,
                        onClick = { viewModel.setTransportToFlight()},
                        isReset = isReset
                    )
                    CustomIconButton(
                        icon = Icons.Outlined.Restaurant,
                        onClick = { viewModel.setTransportToFlight() },
                        isReset = isReset
                    )
                    CustomIconButton(
                        icon = Icons.Outlined.Wifi,
                        onClick = { viewModel.setTransportToFlight() },
                        isReset = isReset
                    )
                    CustomIconButton(
                        icon = Icons.Outlined.AcUnit,
                        onClick = { viewModel.setTransportToFlight() },
                        isReset = isReset
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Sort by",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                SortByRadioButtons(isReset = isReset)

                if (isReset)
                {
                    isReset = false
                }
            }

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    ButtonWithTextAndIcon(
                        text = "Reset", buttonColor = colorResource(id = R.color.peach),
                        textColor = Color.White,
                        modifier = Modifier.weight(1f),
                        onClick = { resetToDefault() }
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    ButtonWithTextAndIcon(
                        text = "Done",
                        buttonColor = Color.White,
                        textColor = colorResource(id = R.color.peach),
                        modifier = Modifier.weight(1f),
                        onClick = onBack
                    )

                }


        }
    }
}
@Composable
fun SortByRadioButtons(
    isReset : Boolean
) {
    var selectedOption by remember { mutableStateOf(sortByOptions[0]) }

    if (isReset) {
        selectedOption = sortByOptions[0]
    }

    Column(
        Modifier
            .selectableGroup()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        sortByOptions.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = {},
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = { selectedOption = option},
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.darkGreen),
                        unselectedColor = colorResource(id = R.color.darkGreen)
                    )
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal)
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewFilterScreen() {
    FilterScreen()
}