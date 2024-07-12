package com.example.booking.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booking.R

@Composable
fun TitleWithBackButton(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(64.dp)
            )
        }

        Text(
            text = title,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(24.dp)
        )
    }
}


@Composable
fun ButtonWithTextAndIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    buttonColor: Color = colorResource(id = R.color.peach),
    textColor: Color = Color.White,
    textSize: TextUnit = 18.sp,
    iconSize: Int = 24,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)

    ) {
        Surface(
            color = buttonColor,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null, // Content description can be null for decorative icons
                        modifier = Modifier
                            .size(iconSize.dp)
                            .padding(end = 8.dp),
                        tint = colorResource(id = R.color.alert)
                    )
                }
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = textSize,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                )
            }
        }
    }
}


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    onValueChange: (String) -> Unit,
    inputTextStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    )
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier
            .border(
                BorderStroke(width = 2.dp, color = Color.White),
                shape = RoundedCornerShape(25)
            )
            .fillMaxWidth()
            .height(48.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = inputTextStyle,
        shape = RoundedCornerShape(25),
        label = {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
        },
        placeholder = {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )
        }
    )
}

@Composable
fun ComingSoonScreen() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialogWithText(
            text = "Coming soon!",
            onCloseDialog = { showDialog = false }
        )
    }
}

@Composable
fun AlertDialogWithText(
    onCloseDialog: () -> Unit = {},
    text: String
) {
    AlertDialog(
        onDismissRequest = onCloseDialog,
        title = {
            Text(text = "Alert Dialog")
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            Button(
                onClick = onCloseDialog
            ) {
                Text(text = "OK")
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun TextFieldWithLeadingIcon(
    value: String,
    @DrawableRes leadingIcon: Int,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val textColor = if (isFocused) Color(0xFF00897B) else Color.Gray
    val iconTint = if (isFocused) Color(0xFF00897B) else Color.Gray
    val containerColor = colorResource(id = R.color.bg)
//            containerColor = Color.White,
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .wrapContentHeight()
            .padding(end = 8.dp)
            .width(80.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = textColor
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null,
                tint = iconTint
//                modifier = Modifier.size(48.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
    )
}

@Composable
fun CustomTextFieldWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            disabledBorderColor = Color.Transparent,
        ),
        label = { Text(text = label) },
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(
                RoundedCornerShape(16.dp)
            )
    )
}

@Composable
fun LabeledDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String,
    textFieldValue: TextFieldValue,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                expanded = true
            },
            label = { androidx.compose.material.Text(label, color = Color.Gray) },
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                disabledBorderColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(
                    RoundedCornerShape(8.dp)
                )

                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { androidx.compose.material.Text(option, color = Color.Black) })
            }
        }
    }
}

@Composable
fun SelectableRowText(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(options) { option ->
            Box(
                modifier = Modifier
                    .clickable { onOptionSelected(option) }
                    .wrapContentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedOption == option) colorResource(id = R.color.teal_700) else Color.White)
                    .padding(8.dp)
            ) {
                Text(
                    text = option,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedOption == option) Color.White else colorResource(id = R.color.teal_700)
                    )
                )
            }
        }
    }
}

//@Composable
//fun SelectableRowText(
//    options: List<String>,
//    selectedOption: String,
//    onOptionSelected: (String) -> Unit
//) {
//    Row(
//        modifier = Modifier.wrapContentSize(),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//
//    ) {
//        options.forEach { option ->
//            Box(
//                modifier = Modifier
//                    .clickable { onOptionSelected(option) }
////                    .weight(1f)
//                    .wrapContentSize()
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(if (selectedOption == option) colorResource(id = R.color.teal_700) else Color.White)
////                    .padding(vertical = 8.dp, horizontal = 16.dp)
//                    .padding(8.dp)
//            ) {
//                Text(
//                    text = option,
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = if (selectedOption == option) Color.White else colorResource(id = R.color.teal_700)
//                    )
//                )
//
//            }
//        }
//    }
//}

@Composable
fun CustomIconButton(
    icon: ImageVector,
    isReset: Boolean = false,
    onClick: () -> Unit = {}
) {
    var isClicked by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            isClicked = !isClicked
            if (isReset) {
                isClicked = false
            }
            onClick()

        },
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isClicked) colorResource(id = R.color.teal_700) else Color.White)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isClicked) Color.White else colorResource(id = R.color.teal_700),
            modifier = Modifier.size(24.dp)
        )
    }
}