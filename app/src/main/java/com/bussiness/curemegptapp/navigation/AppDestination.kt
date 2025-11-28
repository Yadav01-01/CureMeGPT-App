package com.bussiness.curemegptapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination {

    @Serializable
    data object Splash : AppDestination()

    @Serializable
    data object Onboarding : AppDestination()

    @Serializable
    data object Login : AppDestination()

//    @Serializable
//    data class Chat(
//        val chatId: String
//    ) : AppDestination()
}