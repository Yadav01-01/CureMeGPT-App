package com.bussiness.curemegptapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bussiness.curemegptapp.ui.screen.auth.CreateAccountScreen
import com.bussiness.curemegptapp.ui.screen.auth.LoginScreen
import com.bussiness.curemegptapp.ui.screen.auth.NewPasswordScreen
import com.bussiness.curemegptapp.ui.screen.auth.PrivacyConsentScreen
import com.bussiness.curemegptapp.ui.screen.auth.ProfileCompletionScreen
import com.bussiness.curemegptapp.ui.screen.auth.ResetScreen
import com.bussiness.curemegptapp.ui.screen.auth.VerifyOtpScreen
import com.bussiness.curemegptapp.ui.screen.intro.OnboardingScreen
import com.bussiness.curemegptapp.ui.screen.intro.SplashScreen
import com.bussiness.curemegptapp.ui.screen.main.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController,modifier : Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White).navigationBarsPadding()
    ) {
        NavHost(
            navController = navController,
            startDestination = AppDestination.Splash
        ) {

            composable<AppDestination.Splash> { SplashScreen(navController) }
            composable<AppDestination.Onboarding> { OnboardingScreen(navController) }
            composable<AppDestination.Login> { LoginScreen(navController) }
            composable<AppDestination.Reset> { ResetScreen(navController) }
            // composable<AppDestination.VerifyOtp> { VerifyOtpScreen(navController) }
            composable(
                route = "verifyOtp?from={from}&email={email}",
                arguments = listOf(
                    navArgument("from") { defaultValue = "" },
                    navArgument("email") { defaultValue = "" }
                )
            ) { backStackEntry ->
                val from = backStackEntry.arguments?.getString("from") ?: ""
                val email = backStackEntry.arguments?.getString("email") ?: ""
                VerifyOtpScreen(navController, from, email)
            }
            composable<AppDestination.NewPassword> { NewPasswordScreen(navController) }
            composable<AppDestination.CreateAccount> { CreateAccountScreen(navController) }
            composable<AppDestination.PrivacyConsent> { PrivacyConsentScreen(navController) }
            composable<AppDestination.ProfileCompletion> { ProfileCompletionScreen(navController) }
            composable<AppDestination.MainScreen> { MainScreen(navController) }
        }
    }
}