package com.bussiness.curemegptapp.ui.screen.auth

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.GradientButton
import com.bussiness.curemegptapp.ui.component.GradientHeader
import com.bussiness.curemegptapp.ui.dialog.AccountCreatedDialog
import kotlinx.coroutines.delay

@Composable
fun VerifyOtpScreen(  navController: NavHostController,
                      fromScreen: String ?= "",
                      email: String?= "") {
    val context = LocalContext.current
    var otp by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(30) }
    var errorMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AccountCreatedDialog(title = "Account Created\nSuccessfully!", description = "Your account is ready. Start exploring now!",
            onDismiss = { showDialog = false },
            onSetupProfile = { showDialog = false
                navController.navigate(AppDestination.PrivacyConsent) },
                    onGoToAskAI = { showDialog = false }
        )
    }

    // Timer Logic
    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        GradientHeader(
            heading = "Verify Your Account",
            description = "We’ve sent a 5-digit code to your $email."
        )

        Spacer(Modifier.height(32.dp))

        // OTP INPUT
        OtpInputField(
            otp = otp,
            onOtpChange = { entered ->
                if (entered.length <= 5 && entered.all { it.isDigit() }) {
                    otp = entered
                }
                if (otp.length == 5) {
                    errorMessage = ""
                }
            }
        )



        Spacer(Modifier.height(24.dp))

        GradientButton(
            text = "Verify & Continue",
            onClick = {
                when {
                    otp.isEmpty() -> {
                        Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show()
                    }

                    otp.length < 5 -> {
                        Toast.makeText(context, "Enter complete 5-digit OTP", Toast.LENGTH_SHORT).show()
                    }

                    otp != "12345" -> {
                        Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        if (fromScreen== "create"){
                            showDialog = true
                        }else{
                            navController.navigate(AppDestination.NewPassword)
                        }

                    }
                }
            }
        )


        Spacer(Modifier.height(16.dp))

        // RESEND TEXT
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Didn’t receive code? ",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.urbanist_medium))
            )

            if (timeLeft > 0) {
                Text(
                    text = "Resend OTP in ",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.urbanist_medium))
                )

                Text(
                    text = "${timeLeft}s",
                    color = Color(0xFF1E3A8A),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.urbanist_medium))
                )
            } else {
                Text(
                    text = "Resend Now",
                    color = Color(0xFF4338CA),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.urbanist_bold)),
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        timeLeft = 30
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // BOTTOM: BACK TO LOGIN
        if (fromScreen == "create") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 42.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Back",
                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                    fontSize = 18.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(AppDestination.CreateAccount)
                    }
                )

            }
        }else{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 42.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Back to",
                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Login",
                    color = Color(0xFF4338CA),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                    modifier = Modifier.clickable {
                        navController.navigate(AppDestination.Login)
                    }
                )
            }
        }

    }
}

@Composable
fun OtpInputField(
    otp: String,
    onOtpChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Hidden input field
        BasicTextField(
            value = otp,
            onValueChange = { newValue ->
                if (newValue.length <= 5 && newValue.all { it.isDigit() }) {
                    onOtpChange(newValue)
                    if (newValue.length == 5) {
                        focusManager.clearFocus()
                    }
                }
            },
            modifier = Modifier
                .size(1.dp)
                .alpha(0f)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        // Visible OTP boxes
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(5) { index ->
                Box(
                    modifier = Modifier.width(60.dp).height(55.dp)
                       // .size(48.dp)
                        .border(
                            1.dp,
                            if (index < otp.length) Color(0xFF4338CA) else Color(0xFFE5E7EB),
                            RoundedCornerShape(46.dp)
                        )
                        .clickable {
                            focusRequester.requestFocus()
                            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                                    as InputMethodManager
                            val view = (context as? Activity)?.currentFocus ?: return@clickable
                            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when {
                            index < otp.length -> otp[index].toString()     // typed number
                            else -> "0"                                     // hint
                        },
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                        textAlign = TextAlign.Center,
                        color = if (index < otp.length) Color.Black else Color(0xFF3741514D)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}



@Preview(showBackground = true)
@Composable
fun VerifyOtpScreenPreview() {
    val navController = rememberNavController()
    VerifyOtpScreen(navController = navController,)
}