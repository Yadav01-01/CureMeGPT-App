package com.bussiness.curemegptapp.ui.component

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.theme.AppGradientColors
import com.bussiness.curemegptapp.ui.theme.Grey

@Composable
fun GradientHeader(heading: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(AppGradientColors)
            )
            .padding(horizontal = 20.dp, vertical = 36.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = heading,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_bold)),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal,
                color = Color.White,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun GradientIconInputField(
    icon: Int,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    var passwordVisible by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // ----------- LEFT GRADIENT ICON -----------
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(AppGradientColors)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = placeholder,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        val shape = RoundedCornerShape(28.dp)

        // ----------- INPUT FIELD -----------
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = Grey,
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(55.dp)
                .clip(shape)
                .border(
                    width = 1.dp,
                    color = Color(0xFFC3C6CB),
                    shape = shape
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else keyboardType
            ),

            // 👁 Visual Transformation
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

            // 👁 Trailing Icon (ONLY for password field)
            trailingIcon = {
                if (isPassword) {

                    val interactionSource = remember { MutableInteractionSource() }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                passwordVisible = !passwordVisible
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.eyeclose
                                else R.drawable.eyeclose
                            ),
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(end = 15.dp)
                        )
                    }
                }
            }

        )
    }
}

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .height(55.dp)
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(RoundedCornerShape(45.dp))
            .background(
                brush = Brush.linearGradient(AppGradientColors)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.urbanist_semibold))
        )
    }
}

@Composable
fun GradientButton1(
    text: String,
    fontSize: TextUnit = 16.sp,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fontFamily : FontFamily = FontFamily(Font(R.font.urbanist_semibold)),
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(
                brush = Brush.linearGradient(AppGradientColors)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            fontFamily = fontFamily
        )
    }
}

@Composable
fun GradientButton2(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp,
    enabled: Boolean = true,
    paddingHorizontal : Dp = 18.dp,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .height(33.dp)
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal)
            .clip(RoundedCornerShape(45.dp))
            .background(
                brush = Brush.linearGradient(AppGradientColors)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.urbanist_medium))
        )
    }
}

@Composable
fun RoundedCustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val cornerRadius = 10.dp  // iOS style rounded square
    val darkBlue = Color(0xFF1E3A8A) // Dark blue (you can change)

    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                if (checked) darkBlue else Color.Unspecified
            )
            .border(
                width = 1.dp,
                color = if (checked) darkBlue else Color.Black,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check_white), // white tick icon
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}


@Composable
fun DisclaimerBox(
    title: String,
    description: String,
    titleColor: Color = Color.Black,
    backColor: Color = Color(0x084338CA)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backColor,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = titleColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = Color(0xFF697383),
            lineHeight = 18.sp
        )
    }

}

