package com.bussiness.curemegptapp.util

import android.util.Patterns

object ValidationUtils {

    fun validateName(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult(false, "Name is required")
            name.length < 2 -> ValidationResult(false, "Name must be at least 2 characters")
            name.length > 50 -> ValidationResult(false, "Name is too long")
            !name.matches(Regex("^[a-zA-Z\\s]+\$")) -> ValidationResult(false, "Name can only contain letters and spaces")
            else -> ValidationResult(true, "")
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "Email is required")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(false, "Enter a valid email address")
            else -> ValidationResult(true, "")
        }
    }

    fun validatePhone(phone: String): ValidationResult {
        return when {
            phone.isBlank() -> ValidationResult(false, "Phone number is required")
            !phone.matches(Regex("^[0-9]+\$")) -> ValidationResult(false, "Phone number must contain only digits")
            phone.length < 10 -> ValidationResult(false, "Phone number must be at least 10 digits")
            phone.length > 15 -> ValidationResult(false, "Phone number is too long")
            else -> ValidationResult(true, "")
        }
    }

    fun validateEmailOrPhone(input: String): ValidationResult {
        return when {
            input.isBlank() -> ValidationResult(false, "Email or phone is required")
            Patterns.EMAIL_ADDRESS.matcher(input).matches() -> ValidationResult(true, "")
            input.matches(Regex("^[0-9]+\$")) && input.length in 10..15 -> ValidationResult(true, "")
            else -> ValidationResult(false, "Enter a valid email or phone number")
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "Password is required")
            password.length < 8 -> ValidationResult(false, "Password must be at least 8 characters")
            password.length > 50 -> ValidationResult(false, "Password is too long")
            !password.matches(Regex(".*[A-Z].*")) -> ValidationResult(false, "Password must contain at least one uppercase letter")
            !password.matches(Regex(".*[a-z].*")) -> ValidationResult(false, "Password must contain at least one lowercase letter")
            !password.matches(Regex(".*[0-9].*")) -> ValidationResult(false, "Password must contain at least one digit")
            !password.matches(Regex(".*[@#\$%^&+=!].*")) -> ValidationResult(false, "Password must contain at least one special character (@#\$%^&+=!)")
            else -> ValidationResult(true, "")
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isBlank() -> ValidationResult(false, "Please confirm your password")
            password != confirmPassword -> ValidationResult(false, "Passwords do not match")
            else -> ValidationResult(true, "")
        }
    }

    fun validateLoginCredentials(emailOrPhone: String, password: String): ValidationResult {
        val emailOrPhoneValidation = validateEmailOrPhone(emailOrPhone)
        if (!emailOrPhoneValidation.isValid) {
            return emailOrPhoneValidation
        }

        val passwordValidation = validatePassword(password)
        if (!passwordValidation.isValid) {
            return passwordValidation
        }

        return ValidationResult(true, "")
    }

    fun validateSignUp(name: String, emailOrPhone: String, password: String, confirmPassword: String): ValidationResult {
        val nameValidation = validateName(name)
        if (!nameValidation.isValid) {
            return nameValidation
        }

        val emailOrPhoneValidation = validateEmailOrPhone(emailOrPhone)
        if (!emailOrPhoneValidation.isValid) {
            return emailOrPhoneValidation
        }

        val passwordValidation = validatePassword(password)
        if (!passwordValidation.isValid) {
            return passwordValidation
        }

        val confirmPasswordValidation = validateConfirmPassword(password, confirmPassword)
        if (!confirmPasswordValidation.isValid) {
            return confirmPasswordValidation
        }

        return ValidationResult(true, "")
    }

    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String
    )
}