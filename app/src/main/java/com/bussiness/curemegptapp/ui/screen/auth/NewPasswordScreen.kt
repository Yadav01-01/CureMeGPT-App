package com.bussiness.curemegptapp.ui.screen.auth

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.GradientButton
import com.bussiness.curemegptapp.ui.component.GradientHeader
import com.bussiness.curemegptapp.ui.component.GradientIconInputField
import com.bussiness.curemegptapp.ui.dialog.SuccessfulDialog

@Composable
fun NewPasswordScreen(navController: NavHostController) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        SuccessfulDialog(title = "Password updated \nSuccessfully!", description = "Your password has been updated.",
            onDismiss = { showDialog = false },
            onOkClick = { showDialog = false }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Gradient Header
        GradientHeader(heading = "New Password", description = "Please enter your new password.")

        Spacer(modifier = Modifier.height(32.dp))

        // FORM
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        GradientIconInputField(icon = R.drawable.pass_ic,placeholder = "Password", value = password, onValueChange = { password = it }, isPassword = true)

        Spacer(Modifier.height(18.dp))

        GradientIconInputField(icon = R.drawable.pass_ic,placeholder = "Confirm Password", value = confirmPassword, onValueChange = { confirmPassword = it }, isPassword = true)

        Spacer(Modifier.height(28.dp))

        // Gradient Submit Button
        GradientButton(text = "Submit", onClick = { showDialog = true})

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Preview(showBackground = true)
@Composable
fun NewPasswordScreenPreview() {
    val navController = rememberNavController()
    NewPasswordScreen(navController = navController)
}