package com.bussiness.curemegptapp.ui.uistate

data class PersonalUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val dob: String = "",
    val gender: String = "Male",
    val height: String = "",
    val heightType: String = "Cm",
    val weight: String = "",
    val weightType: String = "Kg",
    val imageProfile: String = "selected_file",
    val imageProfilePath: String = "",
    val errorMessage: String? = null,
    val loginSuccess: Boolean = false
)