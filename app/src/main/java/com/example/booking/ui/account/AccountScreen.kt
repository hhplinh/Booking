package com.example.booking.ui.account


import ProfileViewModel
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.booking.R
import com.example.booking.ui.components.ButtonWithTextAndIcon

@Composable
fun AccountScreen(
    onAccountInfoSelected: (String) -> Unit = {},
    profileViewModel: ProfileViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)


    val initialFirstName by profileViewModel.firstName.collectAsState()
    val initialLastName by profileViewModel.lastName.collectAsState()


    val profilePictureUri by profileViewModel.profilePictureUri.collectAsState()

    var firstName by remember { mutableStateOf(initialFirstName) }
    var lastName by remember { mutableStateOf(initialLastName) }

    val savedProfilePictureUri = sharedPreferences.getString("profilePictureUri", null)

    val profilePicturePainter = if (savedProfilePictureUri != null) {
        rememberAsyncImagePainter(savedProfilePictureUri)
    } else {
        painterResource(id = R.drawable.ava)
    }

    LaunchedEffect(Unit) {
        firstName = sharedPreferences.getString("firstName", initialFirstName) ?: initialFirstName
        lastName = sharedPreferences.getString("lastName", initialLastName) ?: initialLastName
    }

    Surface(color = colorResource(id = R.color.bg)) {
        Column (modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Account",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(26.dp)
                )
//                Image(
//                    painter = painterResource(id = R.drawable.ava),
//                    contentDescription = "Profile Picture",
//                    modifier = Modifier
//                        .size(120.dp)
//                        .clip(shape = RoundedCornerShape(26.dp)),
//                    contentScale = ContentScale.Crop
//                )
//                profilePictureUri?.let { uri ->
//                    Image(
//                        painter = rememberAsyncImagePainter(uri),
//                        contentDescription = "Profile Picture",
//                        modifier = Modifier
//                            .size(120.dp)
//                            .clip(shape = RoundedCornerShape(26.dp)),
//                        contentScale = ContentScale.Crop
//                    )
//                } ?: Image(
//                    painter = painterResource(id = R.drawable.ava),
//                    contentDescription = "Profile Picture",
//                    modifier = Modifier
//                        .size(120.dp)
//                        .clip(shape = RoundedCornerShape(26.dp)),
//                    contentScale = ContentScale.Crop
//                )
                Image(
                    painter = profilePicturePainter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(shape = RoundedCornerShape(60.dp)), // Adjust the shape as needed
                    contentScale = ContentScale.Crop
                )

                Text(
//                    text = "Victoria Yoker",
                    text = "$firstName $lastName",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 30.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                MenuRowWithIcon(
                    icon = Icons.Outlined.Person,
                    text = stringResource(R.string.personal_information),
                    onClick = { onAccountInfoSelected(R.string.personal_information.toString()) }
                )
                MenuRowWithIcon(
                    icon = Icons.Outlined.CreditCard,
                    text = stringResource(R.string.payment_and_cards),
                    onClick = { onAccountInfoSelected(R.string.payment_and_cards.toString()) }
                )

                MenuRowWithIcon(icon = Icons.Outlined.FavoriteBorder, text = stringResource(R.string.saved),
                    onClick = { onAccountInfoSelected(R.string.saved.toString()) })

                MenuRowWithIcon(icon = Icons.Outlined.History, text = stringResource(R.string.booking_history),
                    onClick = { onAccountInfoSelected(R.string.booking_history.toString()) })

                MenuRowWithIcon(icon = Icons.Outlined.Settings, text = stringResource(R.string.settings),
                    onClick = { onAccountInfoSelected(R.string.settings.toString()) })

            }

            Spacer(modifier = Modifier.weight(1f))

            ButtonWithTextAndIcon(
                text = "End session",
                icon = Icons.AutoMirrored.Outlined.Logout,
                textColor = colorResource(id = R.color.alert),
                buttonColor = Color.White,
                textSize = 20.sp,
                iconSize = 28,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
private fun MenuRowWithIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    )
    {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = colorResource(id = R.color.peach)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(480)
            )
        )
    }
}

@Preview
@Composable
fun PreviewAccountScreen() {
    AccountScreen()
}