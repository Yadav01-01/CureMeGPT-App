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
import com.bussiness.curemegptapp.ui.screen.ChatAI.AIChatScreen
import com.bussiness.curemegptapp.ui.screen.home.HomeScreen
import com.bussiness.curemegptapp.ui.screen.auth.ProfileCompletionScreen
import com.bussiness.curemegptapp.ui.screen.ChatAI.ChatScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    authNavController: NavController,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = AppDestination.Home,
        modifier = modifier.background(Color.Transparent)
    ) {

        composable<AppDestination.Home> { HomeScreen(navController) }
        composable<AppDestination.Schedule> { ProfileCompletionScreen(navController) }
        composable<AppDestination.Family> { ProfileCompletionScreen(navController) }
        composable<AppDestination.Reports> { ProfileCompletionScreen(navController) }
        composable<AppDestination.AIChatScreen> { AIChatScreen(navController) }
        composable<AppDestination.ChatScreen> { ChatScreen(navController) }

    }
}
