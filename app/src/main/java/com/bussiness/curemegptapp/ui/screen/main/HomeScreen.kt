package com.bussiness.curemegptapp.ui.screen.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.AppointmentBox
import com.bussiness.curemegptapp.ui.component.AttentionItem
import com.bussiness.curemegptapp.ui.component.GradientButton2
import com.bussiness.curemegptapp.ui.component.GradientRedButton
import com.bussiness.curemegptapp.ui.component.MoodOptionSelectable
import com.bussiness.curemegptapp.ui.viewModel.main.AttentionItem
import com.bussiness.curemegptapp.ui.viewModel.main.HomeViewModel
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(navController: NavHostController,
               viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedMood by viewModel.selectedMood

    // Initialize or refresh data when screen appears
    LaunchedEffect(Unit) {
        // Future mein: viewModel.refreshData()
        // For now, just use initial data
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader(
            logoRes = R.drawable.ic_logo,               // Aapke project ke drawable
            notificationRes = R.drawable.ic_notification_home_icon,
            profileRes = R.drawable.ic_profile_image,
            onClick = {

            },
            onClickNotification = {
                navController.navigate(AppDestination.AlertScreen)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,

            ) {
            Spacer(modifier = Modifier.height(23.dp))
            WelcomeSection(userGreating = uiState.userGreating, userName = uiState.userName)

            Spacer(modifier = Modifier.height(20.dp))

            DailyMoodCheckCard(     selectedMood = selectedMood,
                onMoodSelected = { mood -> viewModel.updateMood(mood) }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ProfileCompletionBar(progress = uiState.profileCompletion)
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9FD)),
                shape = RoundedCornerShape(30.dp)
            ) {
                MedicationsAndAllergies(    medications = uiState.medications,
                    allergies = uiState.allergies)

                Spacer(modifier = Modifier.height(20.dp))
                RecommendedSteps(steps = uiState.recommendedSteps)
            }

            Spacer(modifier = Modifier.height(20.dp))
            ThingsNeedingAttention(
                attentionItems = uiState.attentionItems,
                onScheduleClick = { itemId ->
                    viewModel.scheduleAttentionItem(itemId)
                },
                onViewAllClick = {
                    navController.navigate(AppDestination.ThingNeedingAttention)
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            HealthOverviewSection(alerts = uiState.alerts)
        }
    }
}


@Composable
fun WelcomeSection(userGreating: String,userName: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = userGreating,
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = userName,
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
fun DailyMoodCheckCard(   selectedMood: String,
                          onMoodSelected: (String) -> Unit
) {



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
                            onMoodSelected(item.second)
                            Log.d("MOOD_SELECTED", "Selected Mood: $selectedMood")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ---------- SKIP BUTTON ----------
            TextButton(
                onClick = { },
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
fun ProfileCompletionBar(progress: Float) {
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
            text = "${(progress * 100).toInt()}%",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(5.dp))

    GradientProgressBar(
        progress = progress,
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
fun MedicationsAndAllergies( medications: List<String>,
                             allergies: List<String>) {

//    val medications = listOf(
//        "Lisinopril 10mg",
//        "Vitamin D"
//    )
//
//    val allergies = listOf(
//        "Penicillin",
//        "Shellfish"
//    )


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
                modifier = Modifier.size(45.dp)
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun RecommendedSteps(steps: List<String>) {
//    val steps = listOf(
//        "Set a reminder for your blood pressure medication",
//        "Schedule your annual checkup",
//        "Complete emergency contact information"
//    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 10.dp)
    ) {
        Text(
            text = "Recommended Next Steps",
            fontSize = 14.sp,
            color = Color(0xFF697383),
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(10.dp))

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
fun ThingsNeedingAttention(   attentionItems: List<AttentionItem>,
                              onScheduleClick: (Int) -> Unit,
                              onViewAllClick: () -> Unit) {

    val attention = listOf(
        Triple("Tooth Pain Symptoms Detected", "For: James Logan",true),
        Triple("Overdue Dental Cleaning", "For: Rosy Logan",true),


    )


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Thing Needing Attention",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        GradientButton2(
            text = "View All",
            fontSize = 12.sp,
            paddingHorizontal = 2.dp,
            onClick = onViewAllClick,
            modifier = Modifier
                .width(88.dp)
                .height(42.dp)
        )
    }
    Spacer(modifier = Modifier.height(20.dp))

    if (!attention.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            attention.forEach { attention ->

                AttentionItem(
                    title = attention.first,
                    subtitle = attention.second,
                    isUrgent = attention.third
                )
            }
        }
    }else{

    }

}



@Composable
fun HealthOverviewSection(alerts: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Health Overview",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        GradientRedButton(
            text = "Add",
            icon = R.drawable.ic_plus_normal_icon,
            width = 88.dp,
            height = 42.dp,
            fontSize = 14.sp,
            imageSize = 16.dp,
            gradientColors = listOf(
                Color(0xFF4338CA),
                Color(0xFF211C64)
            ),
            onClick = { /* Your action */ }
        )
    }
    Spacer(modifier = Modifier.height(12.dp))

    val profiles = listOf(
        "James (Myself)",
        "Rose Logan",
        "Peter Logan"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        profiles.forEach { name ->
            ProfileTab(
                name = name,
                isSelected = name == "James (Myself)",
                modifier = Modifier.weight(1f)
            )
        }
    }



    Spacer(modifier = Modifier.height(42.dp))
    UserHealthCard(alerts = alerts)

    Spacer(modifier = Modifier.height(71.dp))
}

@Composable
fun ProfileTab(
    name: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50.dp),
        color = if (isSelected) Color.Black else Color.White,
        border = if (!isSelected) BorderStroke(1.dp, Color(0xFF697383)) else null,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color(0xFF697383)
            )
        }
    }

}


@Composable
fun UserHealthCard(alerts: List<String>) {
    val alerts = listOf(
        "Blood pressure medication reminder",
        "Annual checkup due"
    )


    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EFFB)),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(65.dp)
                            .height(70.dp)
                            .clip(RoundedCornerShape(20.dp))

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile_image),
                            contentDescription = null,
                            modifier = Modifier
                                .matchParentSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(7.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "James Logan",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                modifier = Modifier.width(110.dp)
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFFE8E4FF)
                            ) {
                                Text(
                                    text = "40 yrs",
                                    fontSize = 10.sp,
                                    color = Color(0xFF211C64),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Text(
                            text = "Self",
                            fontSize = 14.sp,
                            color = Color(0xFF374151),
                            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Last checkup: 45 days ago",
                            fontSize = 14.sp,
                            color = Color(0xFF374151),
                            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                    Image(
                        painter = painterResource(id = R.drawable.ic_edit_icon_cirlcular),
                        contentDescription = "Edit",
                        modifier = Modifier.size(45.dp).clickable(){

                        }
                    )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Active Alerts",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium,
                color = Color(0xFF697383)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                alerts.forEach { alert ->
                    AlertItem(alert)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        maxItemsInEachRow = 3   // Optional: control max items in a row
                    ) {

                        GradientRedButton(
                            text = "Schedule",
                            icon = R.drawable.ic_schedule_attention_icon,
                            width = 101.dp,
                            height = 42.dp,
                            imageSize = 16.dp,
                            fontSize = 12.sp,
                            horizontalPadding = 12.dp,
                            modifier = Modifier.alignByBaseline(),
                            onClick = { }
                        )

                        GradientRedButton(
                            text = "Ask Ai",
                            icon = R.drawable.ic_ask_ai_icon,
                            width = 80.dp,
                            height = 42.dp,
                            imageSize = 18.dp,
                            fontSize = 12.sp,
                            horizontalPadding = 10.dp,
                            modifier = Modifier.alignByBaseline(),
                            gradientColors = listOf(
                                Color(0xFF4338CA),
                                Color(0xFF211C64)
                            ),
                            onClick = { }
                        )

                        AppointmentBox(
                            text = "Appointment in 7 Days",
                            modifier = Modifier
                                .width(180.dp)
                                .height(42.dp).alignByBaseline(),
                            iconRes = R.drawable.ic_appointed_icon
                        )
                    }

        }
    }
}

@Composable
fun AlertItem(alertText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDFD5FC), RoundedCornerShape(50.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_alert_notification),
            contentDescription = null,
            modifier = Modifier.size(29.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = alertText,
            fontSize = 13.sp,
            color = Color(0xFF181818),
            modifier = Modifier.weight(1f),
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium

        )
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}





