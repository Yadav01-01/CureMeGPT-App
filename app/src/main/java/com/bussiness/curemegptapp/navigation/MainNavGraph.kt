package com.bussiness.curemegptapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.curemegptapp.ui.screen.auth.ResetScreen
import com.bussiness.curemegptapp.ui.screen.intro.SplashScreen
import com.bussiness.curemegptapp.ui.screen.main.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.curemegptapp.ui.screen.auth.ProfileCompletionScreen
import com.bussiness.curemegptapp.ui.screen.auth.ResetScreen
import com.bussiness.curemegptapp.ui.screen.main.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    authNavController: NavController,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = AppDestination.Home::class.qualifiedName!!,
        modifier = modifier.background(Color.Transparent)
    ) {

        composable(AppDestination.Home::class.qualifiedName!!) {
            HomeScreen(navController)
        }

        composable(AppDestination.Schedule::class.qualifiedName!!) {
            ProfileCompletionScreen(navController)
        }
        composable(AppDestination.Family::class.qualifiedName!!) {
            ProfileCompletionScreen(navController)
        }
        composable(AppDestination.Reports::class.qualifiedName!!) {
            ProfileCompletionScreen(navController)
        }

    }
}
