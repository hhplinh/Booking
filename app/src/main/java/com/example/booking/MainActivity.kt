package com.example.booking

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import com.example.booking.ui.components.BookingTabRow
import com.example.booking.ui.theme.BookingTheme


class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        Coil.setImageLoader {
            ImageLoader.Builder(this)
                .build()
        }

        setContent {
            BookingApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookingApp() {
    BookingTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        var previousScreen by remember { mutableStateOf<BookingDestination>(Home) }

        LaunchedEffect(currentDestination?.route) {
            val newScreen = bookingTabRowScreens.find { it.route == currentDestination?.route }
            newScreen?.let {
                previousScreen = it
            }
        }

        val currentScreen = bookingTabRowScreens.find { it.route == currentDestination?.route } ?: previousScreen
        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar(currentDestination?.route.toString()))
                {
                BookingTabRow(
                    allScreens = bookingTabRowScreens,
                    onTabSelected = { newScreen -> navController.navigateSingleTopTo(newScreen.route) },
                    currentScreen = currentScreen
                )
            }
            }
        ) {
            innerPadding ->
            BookingNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewBookingApp() {
    BookingApp()
}