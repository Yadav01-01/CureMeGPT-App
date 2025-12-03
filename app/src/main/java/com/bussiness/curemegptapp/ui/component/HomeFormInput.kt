package com.bussiness.curemegptapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import com.bussiness.curemegptapp.R

@Composable
fun HomeHeader(
    logoRes: Int,          // Logo image resource
    notificationRes: Int,  // Notification icon image resource
    profileRes: Int,       // Profile image resource
    modifier: Modifier = Modifier,
    paddingHorizontal: Dp = 16.dp,
    paddingVertical: Dp = 10.dp,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.wrapContentWidth().height( 30.dp) // Adjust size according to your logo
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Notification Icon
            Image(
                painter = painterResource(id = notificationRes),
                contentDescription = "Notification",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(45.dp)
                    .padding(end = 8.dp)
            )

            // Profile Image



            Row(
                modifier = modifier.width(74.dp).height(52.dp)
                    .background(
                        color = Color(0xFFEBE1FF),
                        shape = RoundedCornerShape(50) // Capsule shape
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onClick() }
                    .padding(horizontal = 5.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ---- Profile Circular Image ----
                Image(
                    painter = painterResource(id = profileRes),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(6.dp))

                // ---- Arrow Icon ----
                Image(
                    painter = painterResource(id = R.drawable.ic_next_arrow_calender),
                    contentDescription = "Arrow",
                    modifier = Modifier.size(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

        }
    }
}