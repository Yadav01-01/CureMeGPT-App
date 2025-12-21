package com.bussiness.curemegptapp.ui.screen.auth


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.GradientButton
import com.bussiness.curemegptapp.ui.component.GradientHeader
import com.bussiness.curemegptapp.ui.component.GradientIconInputField
import com.bussiness.curemegptapp.util.ValidationUtils

@Composable
fun CreateAccountScreen(navController: NavHostController) {
    val context = LocalContext.current
    // FORM
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var accountCreatedSuccessfully = stringResource(R.string.account_created_success)
    Column(
        modifier = Modifier
            .fillMaxSize().imePadding()
            .background(Color.White).verticalScroll(rememberScrollState())
    ) {

        GradientHeader( heading = stringResource(R.string.create_account_title),
            description = stringResource(R.string.create_account_description))

        Spacer(modifier = Modifier.height(55.dp))

        GradientIconInputField(icon = R.drawable.profile_ic, placeholder = stringResource(R.string.full_name_placeholder), value = name, onValueChange = { name = it })

        Spacer(Modifier.height(20.dp))
        // Email Field
        GradientIconInputField(icon = R.drawable.mail_ic,placeholder = stringResource(R.string.email_phone_placeholder), value = email, onValueChange = { email = it },keyboardType = KeyboardType.Email)

        Spacer(Modifier.height(20.dp))

        GradientIconInputField(icon = R.drawable.pass_ic,placeholder = stringResource(R.string.password_placeholder),/*"Password",*/ value = password, onValueChange = { password = it }, isPassword = true)

        Spacer(Modifier.height(20.dp))

        GradientIconInputField(icon = R.drawable.pass_ic,placeholder = stringResource(R.string.confirm_password_placeholder), /*"Confirm Password",*/ value = confirmPassword, onValueChange = { confirmPassword = it }, isPassword = true)

        Spacer(Modifier.height(20.dp))

        // Gradient Login Button
     //   GradientButton(text = "Sign Up", onClick = { navController.navigate("verifyOtp?from=create&email=$email") })

// Gradient Sign Up Button
        GradientButton( text = stringResource(R.string.sign_up_button),
           // text = "Sign Up",
            onClick = {
                // Validate all inputs in sequence
                val nameValidation = ValidationUtils.validateName(name)
                if (!nameValidation.isValid) {
                    Toast.makeText(context, nameValidation.errorMessage, Toast.LENGTH_LONG).show()
                    return@GradientButton
                }

                val emailOrPhoneValidation = ValidationUtils.validateEmailOrPhone(email)
                if (!emailOrPhoneValidation.isValid) {
                    Toast.makeText(context, emailOrPhoneValidation.errorMessage, Toast.LENGTH_LONG).show()
                    return@GradientButton
                }

                val passwordValidation = ValidationUtils.validatePassword(password)
                if (!passwordValidation.isValid) {
                    Toast.makeText(context, passwordValidation.errorMessage, Toast.LENGTH_LONG).show()
                    return@GradientButton
                }

                val confirmPasswordValidation = ValidationUtils.validateConfirmPassword(password, confirmPassword)
                if (!confirmPasswordValidation.isValid) {
                    Toast.makeText(context, confirmPasswordValidation.errorMessage, Toast.LENGTH_LONG).show()
                    return@GradientButton
                }

                // All validations passed
                // TODO: Call sign up API here

                Toast.makeText(context,accountCreatedSuccessfully, /*"Account created successfully!",*/ Toast.LENGTH_SHORT).show()
                navController.navigate("verifyOtp?from=create&email=$email")
            }
        )


        Spacer(modifier = Modifier.weight(1f))

        // Bottom Signup
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.already_have_account),//"Already have an account?" ,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.login_link), //" Login",
                color = Color(0xFF4338CA),
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp,
                modifier = Modifier.clickable( interactionSource = remember { MutableInteractionSource() },
                    indication = null) { navController.navigate(AppDestination.Login) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    val navController = rememberNavController()
    CreateAccountScreen(navController = navController)
}