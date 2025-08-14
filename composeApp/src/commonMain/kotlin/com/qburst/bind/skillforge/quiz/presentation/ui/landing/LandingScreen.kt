package com.qburst.bind.skillforge.quiz.presentation.ui.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qburst.bind.skillforge.quiz.presentation.components.AppBottomBar
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.home.HomeScreen
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.myLearning.MyLearningScreen
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile.ProfileBasicDetailsScreen
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile.ProfileProfessionalDetailsPage
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile.ProfileScreen
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile.ProfileViewModel
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.search.SearchScreen
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.util.BottomNavigationScreen

@Composable
fun LandingScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteString = navBackStackEntry?.destination?.route
    val profileViewModel: ProfileViewModel = viewModel()

    val currentRoute = remember(currentRouteString) {
        when (currentRouteString) {
            BottomNavigationScreen.Home.route -> {
                BottomNavigationScreen.Home
            }

            BottomNavigationScreen.Search.route -> {
                BottomNavigationScreen.Search
            }

            BottomNavigationScreen.MyLearning.route -> {
                BottomNavigationScreen.MyLearning
            }

            BottomNavigationScreen.Profile.route,
            "profileEditBasicDetails",
            "profileEditProfessionalDetails" -> {
                BottomNavigationScreen.Profile
            }

            else -> {
                BottomNavigationScreen.Home
            }
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = currentRoute,
                onItemSelected = { screen ->
                    navController.navigate(route = screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        },
    ) {
        Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
            NavHost(
                navController = navController,
                startDestination = BottomNavigationScreen.Home.route
            ) {
                composable(BottomNavigationScreen.Home.route) { HomeScreen() }
                composable(BottomNavigationScreen.Search.route) { SearchScreen() }
                composable(BottomNavigationScreen.MyLearning.route) { MyLearningScreen() }
                composable(BottomNavigationScreen.Profile.route) { ProfileScreen(navController, profileViewModel) }
                composable("profileEditBasicDetails") { ProfileBasicDetailsScreen(navController, profileViewModel) }
                composable("profileEditProfessionalDetails") { ProfileProfessionalDetailsPage(navController, profileViewModel) }
            }
        }
    }
}