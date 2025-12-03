package com.bussiness.curemegptapp.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.DisclaimerBox
import com.bussiness.curemegptapp.ui.component.GradientButton
import com.bussiness.curemegptapp.ui.component.GradientHeader
import com.bussiness.curemegptapp.ui.component.RoundedCustomCheckbox

@Composable
fun PrivacyConsentScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Gradient Header
        GradientHeader(
            heading = "Privacy & Consent",
            description = "Please review and agree to continue"
        )

        Spacer(modifier = Modifier.height(26.dp))

        // Important Disclaimers Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // Important Disclaimers Header with Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_warning), // You'll need a warning icon
                        contentDescription = "Warning",
                        modifier = Modifier.size(42.dp)
                    )

                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Important Disclaimers",
                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            // Medical Disclaimer Box
            DisclaimerBox(
                title = "Medical Disclaimer",
                description = "This app provides AI-powered health insights for informational purposes only. It is not a substitute for professional medical advice, diagnosis, or treatment.",
                titleColor = Color(0xFF4338CA),
                backColor = Color(0x084338CA)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Data Privacy Box
            DisclaimerBox(
                title = "Data Privacy",
                description = "Your health data is encrypted and stored securely. We comply with HIPAA and other privacy regulations.",
                titleColor = Color(0xFFDC2626),
                backColor = Color(0x08F31D1D)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Consent Checkboxes
        var checkbox1 by remember { mutableStateOf(false) }
        var checkbox2 by remember { mutableStateOf(false) }
        var checkbox3 by remember { mutableStateOf(false) }
        var checkbox4 by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth().padding(16.dp)
                .border(width = 1.dp, color = Color(0xFF697383),
                    shape = RoundedCornerShape(30.dp))

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                ConsentCheckbox(
                    checked = checkbox1,
                    onCheckedChange = { checkbox1 = it },
                    text = buildAnnotatedString {
                        append("I Have Read And Agree To The ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF4338CA),
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("Privacy Policy")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ConsentCheckbox(
                    checked = checkbox2,
                    onCheckedChange = { checkbox2 = it },
                    text = buildAnnotatedString {
                        append("I Agree To The ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF4338CA),
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("Terms Of Service")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ConsentCheckbox(
                    checked = checkbox3,
                    onCheckedChange = { checkbox3 = it },
                    text = buildAnnotatedString {
                        append("I Understand This App Does Not Replace Professional Medical Advice")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ConsentCheckbox(
                    checked = checkbox4,
                    onCheckedChange = { checkbox4 = it },
                    text = buildAnnotatedString {
                        append("I Consent to the processing of my health data for AI analysis")
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // I Agree - Continue Button
        GradientButton(
            text = "I Agree - Continue",
            onClick = { navController.navigate(AppDestination.ProfileCompletion) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exit Instead
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp,bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_lock_encripted_icon), // You'll need a warning icon
                contentDescription = "Encripted",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Encrypted",
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFFCED4DA)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}



@Composable
fun ConsentCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: androidx.compose.ui.text.AnnotatedString
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.Top
    ) {
 

        RoundedCustomCheckbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) }
        )


        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color(0xFF697383),
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PrivacyConsentScreenPreview() {
    val navController = rememberNavController()
    PrivacyConsentScreen(navController = navController)
}