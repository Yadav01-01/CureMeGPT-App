package com.bussiness.curemegptapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.GradientButton
import com.bussiness.curemegptapp.ui.component.GradientHeader
import com.bussiness.curemegptapp.ui.component.GradientIconInputField

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Gradient Header
        GradientHeader(heading = "Welcome Back!", description = "Sign in to continue your dental health journey.")

        Spacer(modifier = Modifier.height(32.dp))

        // FORM
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Email Field
        GradientIconInputField(icon = R.drawable.mail_ic,placeholder = "Email / Phone Number", value = email, onValueChange = { email = it })

        Spacer(Modifier.height(18.dp))

        GradientIconInputField(icon = R.drawable.pass_ic,placeholder = "Password", value = password, onValueChange = { password = it }, isPassword = true)

        Spacer(Modifier.height(15.dp))

        // Forgot Password
        Text(
            text = "Forgot Password?",
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .align(Alignment.End)
                .clickable{ },
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(28.dp))

        // Gradient Login Button
        GradientButton(text = "Login", onClick = { /*TODO*/ })

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Signup
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 42.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New here?" ,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Create an account",
                color = Color(0xFF4338CA),
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.clickable { /*onSignup()*/ }
            )
        }
    }
}