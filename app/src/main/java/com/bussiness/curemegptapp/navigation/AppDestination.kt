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

    @Serializable
    data object Reset : AppDestination()

    @Serializable
    data object VerifyOtp : AppDestination()

    @Serializable
    data object NewPassword : AppDestination()

    @Serializable
    data object CreateAccount : AppDestination()

    @Serializable
    data object PrivacyConsent : AppDestination()

    @Serializable
    data object ProfileCompletion : AppDestination()

    @Serializable
    data object Home : AppDestination()

    @Serializable
    data object Schedule : AppDestination()

    @Serializable
    data object Family : AppDestination()

    @Serializable
    data object Reports : AppDestination()

    @Serializable
    data object MainScreen : AppDestination()



}