package com.bussiness.curemegptapp.ui.screen.auth

//CreateAccountScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun CreateAccountScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Gradient Header
        GradientHeader(heading = "Create Your Account", description = "Join and let AI guide your dental & family health.")

        Spacer(modifier = Modifier.height(32.dp))

        // FORM
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        GradientIconInputField(icon = R.drawable.profile_ic,placeholder = "Full Name", value = name, onValueChange = { name = it })

        Spacer(Modifier.height(18.dp))
        // Email Field
        GradientIconInputField(icon = R.drawable.mail_ic,placeholder = "Email / Phone Number", value = email, onValueChange = { email = it },keyboardType = KeyboardType.Email)

        Spacer(Modifier.height(18.dp))

        GradientIconInputField(icon = R.drawable.pass_ic,placeholder = "Password", value = password, onValueChange = { password = it }, isPassword = true)

        Spacer(Modifier.height(28.dp))

        // Gradient Login Button
        GradientButton(text = "Sign Up", onClick = { navController.navigate("verifyOtp?from=create&email=$email") })

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Signup
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 42.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account?" ,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = " Login",
                color = Color(0xFF4338CA),
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
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