package com.bussiness.curemegptapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.curemegptapp.ui.screen.LoginScreen
import com.bussiness.curemegptapp.ui.screen.OnboardingScreen
import com.bussiness.curemegptapp.ui.screen.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = AppDestination.Splash
    ) {

        composable<AppDestination.Splash> { SplashScreen(navController) }
        composable<AppDestination.Onboarding> { OnboardingScreen(navController) }
        composable<AppDestination.Login> { LoginScreen(navController) }

    }
}