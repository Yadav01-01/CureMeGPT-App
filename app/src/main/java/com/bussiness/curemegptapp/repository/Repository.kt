package com.bussiness.curemegptapp.repository

import com.bussiness.curemegptapp.apimodel.loginmodel.LoginResponse
import com.bussiness.curemegptapp.apimodel.personalmodel.PersonalModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun loginRequest(
        emailOrPhone: String,
        password: String,
        fcmToken: String
    ): Flow<Resource<LoginResponse>>

    fun registerRequest(
        name: String,
        emailOrPhone: String,
        password: String,
        deviceType: String
    ): Flow<Resource<LoginResponse>>

    fun updatePasswordRequest(emailOrPhone: String, password: String): Flow<Resource<LoginResponse>>
    fun forgotOtpRequest(emailOrPhone: String): Flow<Resource<LoginResponse>>
    fun otpVerifyRequest(emailOrPhone: String,otp: String,fcmToken: String,fromScreen: String): Flow<Resource<LoginResponse>>
    fun sendOtpRequest(emailOrPhone: String): Flow<Resource<LoginResponse>>
    fun profileRequest(): Flow<Resource<PersonalModel>>

    fun updatePersonalRequest(
        name: String,
        phone: String,
        email: String,
        dob: String,
        gender: String,
        height: String,
        heightType: String,
        weight: String,
        weightType: String
    ): Flow<Resource<PersonalModel>>


}