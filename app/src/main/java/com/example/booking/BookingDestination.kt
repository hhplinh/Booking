package com.example.booking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface BookingDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Booking app navigation destinations
 */
data object Home : BookingDestination {
    override val icon = Icons.Outlined.Home
    override val route = "Home"
}

data object Booking : BookingDestination {
    override val icon = Icons.Outlined.ConfirmationNumber
    override val route = "Booking"
}

data object Notification : BookingDestination {
    override val route = "Notification"
    override val icon = Icons.Outlined.Notifications
}

data object Account : BookingDestination {
    override val icon = Icons.Outlined.Person
    override val route = "Account"
}

data object ComingSoon : BookingDestination {
    override val icon = Icons.Outlined.ConfirmationNumber
    override val route = "ComingSoon"
}

data class BookingOptionDestination(val bookingId: String) : BookingDestination {
    override val icon: ImageVector = Icons.Outlined.Hotel
    override val route: String = "booking_option/{bookingId}"
}

data object FlightBooking : BookingDestination {
    override val icon: ImageVector = Icons.Outlined.Hotel
    override val route: String = "flight_booking"
}

data object SeatBooking : BookingDestination {
    override val icon: ImageVector = Icons.Outlined.Hotel
    override val route: String = "seat_booking"
}

data object PassTicket : BookingDestination {
    override val icon: ImageVector = Icons.Outlined.Hotel
    override val route: String = "pass_ticket"
}

data object Filter : BookingDestination {
    override val icon: ImageVector = Icons.Outlined.Hotel
    override val route: String = "filter"
}

data object PersonalInfo : BookingDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the BookingTabRow selection
    override val icon = Icons.Filled.Money
    val routeParent = Account.route
    val accountTypeArg = R.string.personal_information.toString()
    override val route = "$routeParent/{$accountTypeArg}"
    val arguments = listOf(
        navArgument(accountTypeArg) { type = NavType.StringType }
    )
}

data object TransportBooking : BookingDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the BookingTabRow selection
    override val icon = Icons.Filled.Money
    val routeParent = Booking.route
    val accountTypeArg = R.string.b2.toString()
    override val route = "$routeParent/{$accountTypeArg}"
    val arguments = listOf(
        navArgument(accountTypeArg) { type = NavType.StringType }
    )
}

//data object SingleAccount : BookingDestination {
//    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
//    // part of the BookingTabRow selection
//    override val icon = Icons.Filled.Money
//    override val route = "single_account"
//    const val accountTypeArg = "account_type"
//    val routeWithArgs = "$route/{$accountTypeArg}"
//    val arguments = listOf(
//        navArgument(accountTypeArg) { type = NavType.StringType }
//    )
//    val deepLinks = listOf(
//        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
//    )
//}

// Screens to be displayed in the top BookingTabRow
val bookingTabRowScreens = listOf(Home, Booking, Notification, Account)