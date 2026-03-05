package com.bussiness.curemegptapp.context

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import kotlin.String

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()

        // 🔴 FIRST initialize Firebase
        FirebaseApp.initializeApp(this)

        // ✅ THEN use Firebase services
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM", "Token: $token")
                }
            }

    }

}