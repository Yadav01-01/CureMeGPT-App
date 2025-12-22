package com.bussiness.curemegptapp.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.GradientButton
import com.bussiness.curemegptapp.ui.component.GradientHeader
import com.bussiness.curemegptapp.ui.component.GradientIconInputField
import com.bussiness.curemegptapp.ui.dialog.SuccessfulDialog
import com.bussiness.curemegptapp.util.ValidationUtils

@Composable
fun NewPasswordScreen(navController: NavHostController, from: String) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        SuccessfulDialog(
            title = stringResource(R.string.password_updated_title),/*"Password updated \nSuccessfully!",*/
            description = "Your password has been updated.",
            onDismiss = {
                if (from == "auth") {
                    navController.navigate(AppDestination.Login)
                } else {
                    navController.navigateUp()
                }
                showDialog = false
            },
            onOkClick = {
                if (from == "auth") {
                    navController.navigate(AppDestination.Login)
                } else {
                    navController.navigateUp()
                }
                showDialog = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Gradient Header
        GradientHeader(
            heading = stringResource(R.string.new_password_title),
            description = stringResource(R.string.new_password_description)/*heading = "New Password", description = "Please enter your new password."*/
        )

        Spacer(modifier = Modifier.height(55.dp))

        // FORM
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        GradientIconInputField(
            icon = R.drawable.pass_ic,
            placeholder = stringResource(R.string.password_placeholder)/*"Password"*/,
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )

        Spacer(Modifier.height(20.dp))

        GradientIconInputField(
            icon = R.drawable.pass_ic,
            placeholder = stringResource(R.string.confirm_password_placeholder)/*"Confirm Password"*/,
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isPassword = true
        )

        Spacer(Modifier.height(20.dp))

        // Gradient Submit Button
        // GradientButton(text = stringResource(R.string.submit_button)/*"Submit"*/, onClick = { showDialog = true})
        // Gradient Submit Button with validation
        GradientButton(
            text = stringResource(R.string.submit_button),
            onClick = {
                // Validate new password
                val passwordValidation = ValidationUtils.validatePassword(password)
                if (!passwordValidation.isValid) {
                    Toast.makeText(context, passwordValidation.errorMessage, Toast.LENGTH_LONG)
                        .show()
                    return@GradientButton
                }

                // Validate password confirmation
                val confirmPasswordValidation =
                    ValidationUtils.validateConfirmPassword(password, confirmPassword)
                if (!confirmPasswordValidation.isValid) {
                    Toast.makeText(
                        context,
                        confirmPasswordValidation.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    return@GradientButton
                }

                // All validations passed - show success dialog
                showDialog = true
            }
        )


        Spacer(modifier = Modifier.weight(1f))

    }
}

@Preview(showBackground = true)
@Composable
fun NewPasswordScreenPreview() {
    val navController = rememberNavController()
    NewPasswordScreen(navController = navController, "")
}