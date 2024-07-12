package com.example.booking.ui.account

import ProfileViewModel
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.booking.R
import com.example.booking.ui.components.ButtonWithTextAndIcon
import com.example.booking.ui.components.CustomTextField
import com.example.booking.ui.components.TitleWithBackButton


@Composable
fun PersonalInfoScreen(
    navigateBack: () -> Unit = {},
    profileViewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)

    val initialFirstName by profileViewModel.firstName.collectAsState()
    val initialLastName by profileViewModel.lastName.collectAsState()


    // Mutable state for holding user information
    var firstName by remember { mutableStateOf(initialFirstName) }
    var lastName by remember { mutableStateOf(initialLastName) }
    var phoneNumber by remember { mutableStateOf("+380 12 345") }
    var email by remember { mutableStateOf("victoria.yoker@gmail.com") }

//    var profilePictureUri by remember { mutableStateOf<Uri?>(null) }

    val profilePictureUri by profileViewModel.getUri().collectAsState()


    // Load saved data from SharedPreferences
    LaunchedEffect(Unit) {
        firstName = sharedPreferences.getString("firstName", initialFirstName) ?: initialFirstName
        lastName = sharedPreferences.getString("lastName", initialLastName) ?: initialLastName
        phoneNumber = sharedPreferences.getString("phoneNumber", phoneNumber) ?: phoneNumber
        email = sharedPreferences.getString("email", email) ?: email
    }

    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val uriString = it.toString()
            profileViewModel.setUri(it.toString())
            sharedPreferences.edit().putString("profilePictureUri", uriString).apply()
        }
    }

    //UI
    Surface(color = colorResource(id = R.color.bg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleWithBackButton(
                    title = "Personal Information",
                    onBack = navigateBack
                )

                // Profile Picture section
                Box(
                    modifier = Modifier.wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ava),
//
//                        contentDescription = "Profile Picture",
//                        modifier = Modifier
//                            .size(120.dp)
//                            .clip(shape = RoundedCornerShape(10.dp)),
//                        contentScale = ContentScale.Crop
//                    )

                    Image(
                        painter = profilePictureUri?.let
                        { rememberAsyncImagePainter(it) } ?: painterResource(id = R.drawable.ava),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(shape = RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )



                    IconButton(
                        onClick = { pickMedia.launch("image/*") },
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cam),
                            contentDescription = "Camera",
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                CustomTextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        profileViewModel.updateFirstName(it)
                        sharedPreferences.edit().putString("firstName", it).apply()
                    },
                    label = "First Name"
                )

                CustomTextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        profileViewModel.updateLastName(it)
                        sharedPreferences.edit().putString("lastName", it).apply()
                    },
                    label = "Last Name"
                )

                CustomTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        sharedPreferences.edit().putString("phoneNumber", it).apply()
                    },
                    label = "Phone Number"
                )

                CustomTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        sharedPreferences.edit().putString("email", it).apply()
                    },
                    label = "Email"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ButtonWithTextAndIcon(
                text = "Save changes",
                buttonColor = colorResource(id = R.color.peach),
                textColor = Color.White,
                textSize = 20.sp,
                onClick = {
                    Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}