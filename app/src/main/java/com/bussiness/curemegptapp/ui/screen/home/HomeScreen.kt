package com.bussiness.curemegptapp.ui.screen.home


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.HomeHeader
import androidx.compose.foundation.lazy.items
import com.bussiness.curemegptapp.navigation.AppDestination


@Composable
fun HomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader(
            logoRes = R.drawable.ic_logo,
            notificationRes = R.drawable.ic_notification_home_icon,
            profileRes = R.drawable.ic_profile_image,
            onClick = {

            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,

            ) {
            Spacer(modifier = Modifier.height(23.dp))
            WelcomeSection()
            Spacer(modifier = Modifier.height(20.dp))
            DailyMoodCheckCard()
            Spacer(modifier = Modifier.height(20.dp))
            ProfileCompletionBar()
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9FD)),
                shape = RoundedCornerShape(30.dp)
            ) {
                MedicationsAndAllergies()

                Spacer(modifier = Modifier.height(20.dp))
                RecommendedSteps()
            }

            Spacer(modifier = Modifier.height(20.dp))
            ThingsNeedingAttention()
            Spacer(modifier = Modifier.height(20.dp))
            HealthOverviewSection()
        }
    }
}

@Composable
fun WelcomeSection() {
    Column(horizontalAlignment = Alignment.Start) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Hello ",
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "James",
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                color = Color(0xFF352CA0)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Here's your health overview for today",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}


@Composable
fun DailyMoodCheckCard() {

    var selectedMood by remember { mutableStateOf("") }

    val moodList = listOf(
        Pair(R.drawable.mood1, "Low"),
        Pair(R.drawable.mood2, "Down"),
        Pair(R.drawable.mood3, "Neutral"),
        Pair(R.drawable.mood4, "Good"),
        Pair(R.drawable.mood5, "Great")
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4338CA))
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 14.dp)
        ) {

            // ---------- TOP ROW ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.daily_mood_chekcer_main_icon),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Daily Mood Check",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_close_icon_mood),
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- EMOJI OPTIONS ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                moodList.forEach { item ->
                    MoodOptionSelectable(
                        icon = item.first,
                        label = item.second,
                        isSelected = selectedMood == item.second,
                        onClick = {
                            selectedMood = item.second
                            Log.d("MOOD_SELECTED", "Selected Mood: $selectedMood")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ---------- SKIP BUTTON ----------
            TextButton(
                onClick = {  },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Skip for Now",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun MoodOptionSelectable(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (isSelected) Color(0xFF4338CA) else Color.White,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(if (isSelected) R.font.urbanist_semibold else R.font.urbanist_medium)),
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
        )
    }
}


@Composable
fun ProfileCompletionBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Profile Completion",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal,

            color = Color(0xFF697383)
        )
        Text(
            text = "50%",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(5.dp))

    GradientProgressBar(
        progress = 0.5f,
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun GradientProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        Color(0xFF5B4FE9),
        Color(0xFF4338CA)
    )

    Box(
        modifier = modifier
            .height(10.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFD9D9D9))   // Track color
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .clip(RoundedCornerShape(50))
                .background(brush = Brush.horizontalGradient(gradientColors))
        )
    }
}


@Composable
fun MedicationsAndAllergies() {

    val medications = listOf(
        "Lisinopril 10mg",
        "Vitamin D"
    )

    val allergies = listOf(
        "Penicillin",
        "Shellfish"
    )


    Column(
        modifier = Modifier.padding(20.dp)
    ) {

        // ------- Header -------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Current Medications",
                fontSize = 14.sp,
                color = Color(0xFF697383),
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium
            )
            Image(
                painter = painterResource(id = R.drawable.ic_edit_icon_cirlcular),
                contentDescription = "Edit",
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ------- Medications LazyRow -------
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(medications) { item ->
                MedicationChip(item, Color(0xFFD5D2F3))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Allergies",
            fontSize = 14.sp,
            color = Color(0xFF697383),
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ------- Allergies LazyRow -------
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(allergies) { item ->
                MedicationChip(item, Color(0xFFF8E3E7))
            }
        }


    }

}


@Composable
fun MedicationChip(text: String, backgroundColor: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            color = if (backgroundColor == Color(0xFFD5D2F3)) Color(0xFF211C64) else Color(
                0xFFF31D1D
            )
        )
    }
}

@Composable
fun RecommendedSteps() {
    val steps = listOf(
        "Set a reminder for your blood pressure medication",
        "Schedule your annual checkup",
        "Complete emergency contact information"
    )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Recommended Next Steps",
                fontSize = 14.sp,
                color = Color(0xFF697383),
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Replace LazyColumn with regular Column since we have only 3 items
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                steps.forEach { step ->
                    RecommendedStepItem(step)
                }
            }
        }

}


@Composable
fun RecommendedStepItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_recommended_icon),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium,
            color = Color(0xFF181818)
        )
    }
}

@Composable
fun ThingsNeedingAttention() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Thing Needing Attention",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B4FE9)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("View All", fontSize = 12.sp)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    AttentionItem(
        title = "Tooth Pain Symptoms Detected",
        subtitle = "For: James Logan",
        isUrgent = true
    )
    Spacer(modifier = Modifier.height(8.dp))
    AttentionItem(
        title = "Overdue Dental Cleaning",
        subtitle = "For: Rosy Logan",
        isUrgent = true
    )
}

@Composable
fun AttentionItem(title: String, subtitle: String, isUrgent: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFD32F2F)
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_my_calendar),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Schedule", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun HealthOverviewSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Health Overview",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B4FE9)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_add),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add", fontSize = 12.sp)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileTab("James (Myself)", true)
        ProfileTab("Rose Logan", false)
        ProfileTab("Peter Logan", false)
    }
    Spacer(modifier = Modifier.height(12.dp))
    UserHealthCard()
}

@Composable
fun ProfileTab(name: String, isSelected: Boolean) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color.Black else Color.White,
        border = if (!isSelected) BorderStroke(1.dp, Color.LightGray) else null
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun UserHealthCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8E4FF))
                    ) {
                        Image(
                            painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "James Logan",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFE8E4FF)
                            ) {
                                Text(
                                    text = "40 yrs",
                                    fontSize = 10.sp,
                                    color = Color(0xFF5B4FE9),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Self",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Last checkup: 45 days ago",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Active Alerts",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            AlertItem("Blood pressure medication reminder")
            Spacer(modifier = Modifier.height(8.dp))
            AlertItem("Annual checkup due")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_my_calendar),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Schedule", fontSize = 12.sp)
                }
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5B4FE9)),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0xFF5B4FE9))
                ) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_info_details),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ask AI", fontSize = 12.sp)
                }
                OutlinedButton(
                    onClick = { },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_my_calendar),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Appointment in 7 Days",
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun AlertItem(alertText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF3E8FF), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_alert_notification),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = alertText,
            fontSize = 13.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}