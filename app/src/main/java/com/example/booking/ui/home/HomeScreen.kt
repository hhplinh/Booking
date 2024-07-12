package com.example.booking.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booking.R
import com.example.booking.ui.components.AlertDialogWithText

@Composable
fun HomeScreen(
    onBookingSelected: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        color = colorResource(id = R.color.bg),
        modifier = Modifier.fillMaxSize()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .semantics { contentDescription = "Home Screen" }
        ) {
            Text(
                text = stringResource(R.string.explore_the_beautiful_world),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(10.dp)
            )
            SearchBar(
                text = searchText,
                onTextChanged = { searchText = it },
                onSearchClicked = {
                    searchText = it
                    showDialog = true
                },
                onSearchKeyboardAction = {
                    searchText = it
                    showDialog = true
                }
            )
            Text(
                text = stringResource(R.string.booking_services),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                ButtonIconWithText(
                    text = "Trips",
                    imageVector = ImageVector.vectorResource(id = R.drawable.globe),
                    onClick = { onBookingSelected("Trips") }
                )
                ButtonIconWithText(
                    text = "Hotel",
                    imageVector = ImageVector.vectorResource(id = R.drawable.hotel),
                    onClick = { onBookingSelected("Hotel") }
                )
                ButtonIconWithText(
                    text =  stringResource(R.string.b2),
                    imageVector = ImageVector.vectorResource(id = R.drawable.plane),
                    onClick = {onBookingSelected(R.string.b2.toString()) }
                )
                ButtonIconWithText(
                    text = "Events",
                    imageVector = ImageVector.vectorResource(id = R.drawable.event),
                    onClick = { onBookingSelected("Events") }
                )


            }
            Spacer(modifier = Modifier.weight(1.0f))
        }

        if (showDialog) {
            SearchDialog(
                searchText = searchText,
                onCloseDialog = { showDialog = false }
            )
        }
    }
}


@Composable
fun SearchDialog(
    searchText: String,
    onCloseDialog: () -> Unit
) {
    AlertDialogWithText(onCloseDialog = onCloseDialog, text = "Search for $searchText")
}


@Composable
fun ButtonIconWithText(
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(8.dp)
                .background(
                    colorResource(id = R.color.darkGreen),
                    shape = RoundedCornerShape(15.dp)
                ),
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
        ))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchKeyboardAction: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text("Search") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.peach),
            unfocusedBorderColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(56.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            IconButton(
                onClick = { onSearchClicked(text) },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .background(
                        colorResource(id = R.color.peach),
                        shape = RoundedCornerShape(15.dp)
                    ),
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send
        ),
        keyboardActions = KeyboardActions(
            onSend = {
                onSearchKeyboardAction(text)
            }
        ),
        textStyle = TextStyle(color = Color.Black)
    )
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}