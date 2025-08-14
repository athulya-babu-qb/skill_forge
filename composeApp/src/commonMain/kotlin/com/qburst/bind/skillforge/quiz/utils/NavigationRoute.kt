package com.qburst.bind.skillforge.quiz.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.LandingScreen
import com.qburst.bind.skillforge.quiz.presentation.ui.login.LoginScreen
import com.qburst.bind.skillforge.quiz.presentation.ui.splash.SplashScreen

sealed class NavigationRoute(val name: String) {
    object Splash : NavigationRoute(name = "splash")
    object Login : NavigationRoute(name = "login")
    object Landing : NavigationRoute(name = "landing")
}

@Composable
fun Router() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationRoute.Landing.name) {
        composable(NavigationRoute.Splash.name) {
            SplashScreen(navController)
        }
        composable(NavigationRoute.Login.name) {
            LoginScreen(
                onLoginSuccess = {
                    navController.toHome()
                }
            )
        }
        composable(NavigationRoute.Landing.name) {
            LandingScreen()
        }
    }
}

fun NavController.toHome(
    popUpToRoute: NavigationRoute? = NavigationRoute.Landing,
    inclusiveRoute: Boolean = true
) {
    this.navigate(NavigationRoute.Landing.name) {
        popUpToRoute?.let {
            popUpTo(popUpToRoute.name) {
                inclusive = inclusiveRoute
            }
        }
        launchSingleTop = true
    }
}
