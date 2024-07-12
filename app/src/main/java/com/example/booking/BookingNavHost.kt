package com.example.booking

import NotiScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booking.ui.account.AccountScreen
import com.example.booking.ui.account.PersonalInfoScreen
import com.example.booking.ui.booking.BookingScreen
import com.example.booking.ui.booking.FilterScreen
import com.example.booking.ui.booking.FlightScreen
import com.example.booking.ui.booking.PassScreen
import com.example.booking.ui.booking.SeatScreen
import com.example.booking.ui.booking.TransportBookingScreen
import com.example.booking.ui.components.ComingSoonScreen
import com.example.booking.ui.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(
                onBookingSelected = { bookingId ->
//                    navController.navigateTo(
//                        "${Booking.route}/$bookingId"
                    if (bookingId == R.string.b2.toString()) {
                        navController.navigateTo(TransportBooking.route)
                    } else {
                        navController.navigateTo(ComingSoon.route)
                    }
                }
            )
        }

        composable(route = ComingSoon.route)
        {
            ComingSoonScreen()
        }


        composable(route = Booking.route) {
            BookingScreen(
                onBookingSelected = { bookingId ->
//                    navController.navigateTo(
//                        "${Booking.route}/$bookingId"
                    if (bookingId == R.string.b2.toString()) {
                        navController.navigateTo(TransportBooking.route)
                    } else {
                        navController.navigateTo(ComingSoon.route)
                    }
                }
            )
        }
        composable(route = Account.route) {
            AccountScreen(
                onAccountInfoSelected = { accountInfo ->
//                    navController.navigateTo(
//                        "${Account.route}/$accountInfo"
                    if (accountInfo == R.string.personal_information.toString()) {
                        navController.navigateTo(PersonalInfo.route)
                    } else {
                        navController.navigateTo(ComingSoon.route)
                    }
                }
            )
        }
        composable(route = Notification.route) {
            NotiScreen()
        }

        composable(
            route = TransportBooking.route,
            arguments = TransportBooking.arguments
        )
        {
            TransportBookingScreen(
                onBack = {
                    navController.navigateUp()
                },
                onSearchClick = { navController.navigateTo(FlightBooking.route) }
            )

        }

        composable(route = FlightBooking.route) {
            FlightScreen(
                onBack = {
                    navController.navigateUp()
                },
                onTicketClicked = {
                    navController.navigateTo(SeatBooking.route)
                },
                onFilterClicked = {
                    navController.navigateTo(Filter.route)
                }
            )
        }

        composable(route = SeatBooking.route) {
            SeatScreen(
                onBack = {
                    navController.navigateUp()
                },
                onContinue = {
                    navController.navigateTo(PassTicket.route)
                }
            )
        }

        composable(route = Filter.route) {
            FilterScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = PassTicket.route) {
            PassScreen(
                onBack = {
                    navController.navigateUp()
                },
                onHomeClicked = {
                    navController.navigateSingleTopTo(Home.route)
                }
            )
        }

        composable(
            route = PersonalInfo.route,
            arguments = PersonalInfo.arguments
        ) {
//            navBackStackEntry ->
//            val accountType =
//                navBackStackEntry.arguments?.getString(PersonalInfo.accountTypeArg)
//            SingleAccountScreen(accountType)

            PersonalInfoScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
        }
    }
}
//
fun NavHostController.navigateTo(route: String) =
    this.navigate(route) {

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }


//val shouldShowBottomBar: Boolean
//    @Composable get() = navController
//        .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes


val bottomBarTabs = BottomBarTab.entries.toTypedArray()
private val bottomBarRoutes = bottomBarTabs.map { it.route }

enum class BottomBarTab(
    val route: String
) {
    HOME(
        Home.route
    ),
    BOOKING(
        Booking.route
    ),
    NOTIFICATION(
        Notification.route
    ),
    ACCOUNT(
        Account.route
    ),
    PERSONAL_INFO(
        PersonalInfo.route
    ),
    TRANSPORT_BOOKING(
        TransportBooking.route
    )
}

fun shouldShowBottomBar(route: String) = route in bottomBarRoutes

//private fun NavHostController.navigateToBookingChoice(accountType: String) {
//    this.navigateSingleTopTo("${BookingOptionDestination.route}/$accountType")


//        composable(
//            route = "${Booking.route}/{bookingId}",
//            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
//        ) { navBackStackEntry ->
//            val bookingId = navBackStackEntry.arguments?.getString("bookingId") ?: ""
//            when (bookingId) {
//                R.string.b2.toString() -> {
//                    TransportBookingScreen(
//                        onBack = {
//                            if (Log.isLoggable("TAG", Log.DEBUG)) {
//                                Log.d("TAG", "navigateBack TransportBookingScreen message")
//                            }
//
//                            if (navController.currentBackStackEntry?.destination?.route == Booking.route) {
////                                navController.popBackStack()
//                                navController.navigateUp()
//                            }
//                            else {
//                                navController.navigateSingleTopTo(Booking.route)
//                            }
//                        }
//                    )
//                }
//                else -> {
//                    ComingSoonScreen()
//                }
//            }
//        }

//        composable(
//            route = "${Account.route}/{accountInfo}",
//            arguments = listOf(navArgument("accountInfo") { type = NavType.StringType })
//        ) { navBackStackEntry ->
//            val accountInfo = navBackStackEntry.arguments?.getString("accountInfo") ?: ""
//            when (accountInfo) {
//                R.string.personal_information.toString() -> {
//                    PersonalInfoScreen(
//                        navigateBack = {
//                            if (Log.isLoggable("TAG", Log.DEBUG)) {
//                                Log.d("TAG", "navigateBack message")
//                            }
//
//                            if (navController.currentBackStackEntry?.destination?.route == Account.route) {
////                                navController.popBackStack()
//                                navController.navigateUp()
//                            }
//                            else {
//                                navController.navigateSingleTopTo(Account.route)
//                            }
//                        }
//                    )
//                }
//                else -> {
//                    ComingSoonScreen()
//                }
//            }
//        }
