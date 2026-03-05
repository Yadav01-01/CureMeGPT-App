package com.bussiness.curemegptapp.viewmodel.personalviewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.curemegptapp.apimodel.personalmodel.User
import com.bussiness.curemegptapp.repository.Repository
import com.bussiness.curemegptapp.repository.Resource
import com.bussiness.curemegptapp.ui.uistate.PersonalUiState
import com.bussiness.curemegptapp.util.LoaderManager
import com.bussiness.curemegptapp.util.SessionManager
import com.bussiness.curemegptapp.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileCompeteViewModel @Inject constructor(private val repository: Repository, private val sessionManager: SessionManager) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonalUiState())
    val uiState = _uiState.asStateFlow()

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun onPhoneChange(value: String) {
        _uiState.value = _uiState.value.copy(phone = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value)
    }

    fun onDobChange(value: String) {
        _uiState.value = _uiState.value.copy(dob = value)
    }

    fun onGenderChange(value: String) {
        _uiState.value = _uiState.value.copy(gender = value)
    }

    fun onHeightChange(value: String) {
        _uiState.value = _uiState.value.copy(height = value)
    }

    fun onHeightTypeChange(value: String) {
        _uiState.value = _uiState.value.copy(heightType = value)
    }
    fun onWeightChange(value: String) {
        _uiState.value = _uiState.value.copy(weight = value)
    }

    fun onWeightTypeChange(value: String) {
        _uiState.value = _uiState.value.copy(weightType = value)
    }

    fun onImageChange(value: String) {
        _uiState.value = _uiState.value.copy(imageProfile = value)
    }

    fun onImagePathChange(value: String) {
        _uiState.value = _uiState.value.copy(imageProfilePath = value)
    }

    fun getPersonalRequest(onSuccess: (User) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.profileRequest()
                .collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            LoaderManager.show()
                        }
                        is Resource.Success -> {
                            LoaderManager.hide()
                            val data = result.data.data?.user
                            data?.let { userData->
                                _uiState.value = _uiState.value.copy(
                                    name = userData.name ?: "",
                                    phone = userData.phone ?: "",
                                    email = userData.email ?: "",
                                    dob = userData.dob ?: "",
                                    gender = userData.gender ?: "Male",
                                    height = userData.height ?: "",
                                    heightType = "Cm",
                                    weight = userData.weight ?: "",
                                    weightType = "Kg",
                                )
                                onSuccess(userData)
                            }
                        }
                        is Resource.Error -> {
                            LoaderManager.hide()
                            onError(result.message)
                        }
                        Resource.Idle -> Unit
                    }
                }
        }
    }


    fun updatePersonalRequest(onSuccess: (User) -> Unit, onError: (String) -> Unit){
        val state = _uiState.value
        val nameValidation = ValidationUtils.validateName(state.name)
        val phoneValidation = ValidationUtils.validatePhone(state.phone)
        val emailValidation = ValidationUtils.validateEmail(state.email)
        val dobValidation = ValidationUtils.validateDateOfBirth(state.dob)
        val heightValidation = ValidationUtils.validateHeight(state.height)
        val weightValidation = ValidationUtils.validateWeight(state.weight)
        if (!nameValidation.isValid) {
            onError(nameValidation.errorMessage)
            return
        }
        if (!phoneValidation.isValid) {
            onError(phoneValidation.errorMessage)
            return
        }
        if (!emailValidation.isValid) {
            onError(emailValidation.errorMessage)
            return
        }
        if (!dobValidation.isValid) {
            onError(dobValidation.errorMessage)
            return
        }
        if (!heightValidation.isValid) {
            onError(heightValidation.errorMessage)
            return
        }
        if (!weightValidation.isValid) {
            onError(weightValidation.errorMessage)
            return
        }
        viewModelScope.launch {
            repository.updatePersonalRequest(state.name,state.phone,state.email,state.dob,state.gender,state.height,state.heightType,state.weight,state.weightType)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            LoaderManager.show()
                        }
                        is Resource.Success -> {
                            LoaderManager.hide()
                            val data = result.data.data?.user
                            data?.let { userData->
                                _uiState.value = _uiState.value.copy(
                                    name = userData.name ?: "",
                                    phone = userData.phone ?: "",
                                    email = userData.email ?: "",
                                    dob = userData.dob ?: "",
                                    gender = userData.gender ?: "Male",
                                    height = userData.height ?: "",
                                    heightType = "Cm",
                                    weight = userData.weight ?: "",
                                    weightType = "Kg",
                                )
                                onSuccess(userData)
                            }
                        }
                        is Resource.Error -> {
                            LoaderManager.hide()
                            onError(result.message)
                        }
                        Resource.Idle -> Unit
                    }
                }
        }
    }


}



