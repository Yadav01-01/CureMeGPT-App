package com.bussiness.curemegptapp.ui.screen.main


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.data.model.BottomItem
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.navigation.MainNavGraph
import com.bussiness.curemegptapp.ui.component.input.CustomBottomBar
import com.bussiness.curemegptapp.util.AppConstant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(authNavController: NavHostController) {

    val navController = rememberNavController()
    val currentRoute = getCurrentRoute(navController)

    val bottomBarItems = listOf(
        BottomItem(AppConstant.BOTTOM_NAV_HOME, R.drawable.ic_home_icon, AppDestination.Home::class.qualifiedName!!),
        BottomItem(AppConstant.BOTTOM_NAV_SCHEDULE, R.drawable.ic_schedule_icon, AppDestination.Schedule::class.qualifiedName!!),
        BottomItem(AppConstant.BOTTOM_NAV_FAMILY, R.drawable.ic_family_icon, AppDestination.Family::class.qualifiedName!!),
        BottomItem(AppConstant.BOTTOM_NAV_REPORTS, R.drawable.ic_report_icon, AppDestination.Reports::class.qualifiedName!!)
    )

    val selectedIndex = bottomBarItems.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        /** NAVIGATION SCREEN  */
        MainNavGraph(
            authNavController = authNavController,
            navController = navController,
            modifier = Modifier.fillMaxSize().padding(bottom = 80.dp )
        )

        /** CUSTOM BOTTOM BAR — Always on top of screen content */
        AnimatedVisibility(
            visible = currentRoute != AppDestination.Reset::class.qualifiedName!!,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            CustomBottomBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    val item = bottomBarItems[index]
                    navController.navigate(item.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}



//import android.annotation.SuppressLint
//import android.app.Activity
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.core.view.WindowInsetsControllerCompat
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.bussiness.curemegptapp.R
//import com.bussiness.curemegptapp.data.model.BottomItem
//import com.bussiness.curemegptapp.navigation.AppDestination
//import com.bussiness.curemegptapp.navigation.MainNavGraph
//import com.bussiness.curemegptapp.ui.component.input.CustomBottomBar
//import com.bussiness.curemegptapp.util.AppConstant
//
//@SuppressLint("ContextCastToActivity")
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MainScreen(authNavController: NavHostController) {
//
//    val navController = rememberNavController()
//    val currentRoute = getCurrentRoute(navController)
//
//    val bottomBarItems = listOf(
//        BottomItem(AppConstant.BOTTOM_NAV_HOME, R.drawable.ic_home_icon, AppDestination.Home::class.qualifiedName!!),
//        BottomItem(AppConstant.BOTTOM_NAV_SCHEDULE, R.drawable.ic_schedule_icon, AppDestination.Schedule::class.qualifiedName!!),
//        BottomItem(AppConstant.BOTTOM_NAV_FAMILY, R.drawable.ic_family_icon, AppDestination.Family::class.qualifiedName!!),
//        BottomItem(AppConstant.BOTTOM_NAV_REPORTS, R.drawable.ic_report_icon, AppDestination.Reports::class.qualifiedName!!)
//    )
//
//    val selectedIndex = bottomBarItems.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)
//
//    val activity = LocalContext.current as? Activity
//
//    LaunchedEffect(Unit) {
//        activity?.window?.let { window ->
//            window.statusBarColor = android.graphics.Color.WHITE
//            window.navigationBarColor = android.graphics.Color.WHITE
//            val controller = WindowInsetsControllerCompat(window, window.decorView)
//            controller.isAppearanceLightStatusBars = true
//            controller.isAppearanceLightNavigationBars = true
//        }
//    }
//
//    Scaffold(
//        bottomBar = {
//            val hideBottomBarRoutes = listOf(
//                AppDestination.Reset::class.qualifiedName!!
//            )
//
//            AnimatedVisibility(
//                visible = currentRoute !in hideBottomBarRoutes,
//                enter = fadeIn(),
//                exit = fadeOut()
//            ) {
//                CustomBottomBar(
//                    selectedIndex = selectedIndex,
//                    onItemSelected = { index ->
//                        val item = bottomBarItems[index]
//                        navController.navigate(item.route) {
//                            popUpTo(0) { inclusive = true }
//                            launchSingleTop = true
//                        }
//                    }
//                )
//            }
//        }
//    ) { innerPadding ->
//        MainNavGraph(
//            authNavController = authNavController,
//            navController = navController,
//            modifier = Modifier
//                .background(Color.Transparent)
//                .padding(innerPadding)
//        )
//    }
//}
//
@Composable
fun getCurrentRoute(navController: NavController): String {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route ?: ""
}


