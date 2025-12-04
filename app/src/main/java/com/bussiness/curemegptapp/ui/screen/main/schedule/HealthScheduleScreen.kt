package com.bussiness.curemegptapp.ui.screen.main.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.curemegptapp.R
//import com.bussiness.curemegptapp.ui.component.AppointmentMenuPopup
import com.bussiness.curemegptapp.ui.component.CommonHeader
import com.bussiness.curemegptapp.ui.component.GradientRedButton
import com.bussiness.curemegptapp.ui.sheet.BottomSheetDialog
import com.bussiness.curemegptapp.ui.sheet.BottomSheetDialogProperties
import com.bussiness.curemegptapp.ui.sheet.FilterAppointmentsBottomSheet

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bussiness.curemegptapp.ui.component.RoundedCustomCheckbox

data class Appointment(
    val title: String,
    val doctor: String,
    val patientName: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val icon: Int,
    val isVisibleItem : Boolean = true
)

@Composable
fun HealthScheduleScreen(navController: NavHostController) {
    var showSheet by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val appointments = listOf(
        Appointment(
            title = "Normal Check-up",
            doctor = "Dr. Emily Rodriguez",
            patientName = "Peter Logan",
            date = "09/01/2025",
            time = "10:30 AM",
            location = "Health Care Hub, 20 Cooper Square, New York, NY 10003, USA",
            description = "Regular 6-month check-up with cleaning",
            icon = R.drawable.ic_medical_bag_icon
        ),
        Appointment(
            title = "Dental Check-up",
            doctor = "Dr. Sarah Johnson",
            patientName = "James Logan",
            date = "08/26/2025",
            time = "10:30 AM",
            location = "20 Cooper Square, New York, NY 10003, USA",
            description = "Regular 6-month check-up with cleaning",
            icon = R.drawable.ic_dental_icon
        ),
        Appointment(
            title = "Root Canal",
            doctor = "Dr. Sarah Johnson",
            patientName = "James Logan",
            date = "08/26/2025",
            time = "10:30 AM",
            location = "20 Cooper Square, New York, NY 10003, USA",
            description = "Regular 6-month check-up with cleaning",
            icon = R.drawable.ic_tooth_light,
            isVisibleItem = false
        ),
        Appointment(
            title = "Root Canal",
            doctor = "Dr. Sarah Johnson",
            patientName = "James Logan",
            date = "08/26/2025",
            time = "10:30 AM",
            location = "20 Cooper Square, New York, NY 10003, USA",
            description = "Regular 6-month check-up with cleaning",
            icon = R.drawable.ic_tooth_light,
            isVisibleItem = false
        ),
        Appointment(
            title = "Root Canal",
            doctor = "Dr. Sarah Johnson",
            patientName = "James Logan",
            date = "08/26/2025",
            time = "10:30 AM",
            location = "20 Cooper Square, New York, NY 10003, USA",
            description = "Regular 6-month check-up with cleaning",
            icon = R.drawable.ic_tooth_light,
            isVisibleItem = false
        )
    )

    val filteredList = appointments.filter { item ->
        item.title.contains(searchQuery, ignoreCase = true)
    }



    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFFF))) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CommonHeader("Health Schedule")

            // ---------- TABS ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TabButton(
                    text = "Appointments",
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.weight(1f)
                )
                TabButton(
                    text = "Medications",
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.weight(1f)
                )
            }

            if (selectedTab == 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(40.dp),
                        color = Color(0xFFF4F4F4)
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search",
                                    color = Color(0xFFBCBCBC),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.urbanist_regular))
                                )
                            },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_search_icon),
                                    contentDescription = "Search",
                                    modifier = Modifier.size(18.dp)
                                )
                            },
//                        colors = TextFieldDefaults.textFieldColors(
//                            containerColor = Color(0xFFF4F4F4),
//                            unfocusedIndicatorColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent
//                        )
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF4F4F4),
                                unfocusedContainerColor = Color(0xFFF4F4F4),
                                disabledContainerColor = Color(0xFFF4F4F4),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    Image(
                        painter = painterResource(id = R.drawable.ic_filter_icon),
                        contentDescription = "Filter",
                        modifier = Modifier.wrapContentSize().clickable() {
                            showSheet = true
                        }
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                // ---------- LIST ----------
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredList) { appointment ->
                        AppointmentCard(appointment = appointment)
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(40.dp),
                        color = Color(0xFFF4F4F4)
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search",
                                    color = Color(0xFFBCBCBC),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.urbanist_regular))
                                )
                            },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_search_icon),
                                    contentDescription = "Search",
                                    modifier = Modifier.size(18.dp)
                                )
                            },
//                        colors = TextFieldDefaults.textFieldColors(
//                            containerColor = Color(0xFFF4F4F4),
//                            unfocusedIndicatorColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent
//                        )
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF4F4F4),
                                unfocusedContainerColor = Color(0xFFF4F4F4),
                                disabledContainerColor = Color(0xFFF4F4F4),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    Image(
                        painter = painterResource(id = R.drawable.ic_filter_icon),
                        contentDescription = "Filter",
                        modifier = Modifier.wrapContentSize().clickable() {
                            showSheet = true
                        }
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                // ---------- LIST ----------
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredList) { appointment ->
                        MedicationsCard(appointment = appointment)
                    }
                }

            }

        }


            GradientRedButton(
                text = "Schedule",
                icon = R.drawable.ic_plus_normal_icon,
                width = 150.dp,
                height = 55.dp,
                fontSize = 16.sp,
                imageSize = 24.dp,
                modifier = Modifier.align(Alignment.BottomEnd).padding(horizontal = 15.dp, vertical = 30.dp),
                gradientColors = listOf(
                    Color(0xFF4338CA),
                    Color(0xFF211C64)
                ),
                onClick = { /* Your action */ }
            )

    }


    if (showSheet){
        BottomSheetDialog(
            onDismissRequest = {

                showSheet = false
            },
            properties = BottomSheetDialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                dismissWithAnimation = true,
                enableEdgeToEdge = false,
            )
        ) {
            FilterAppointmentsBottomSheet(
                onDismiss = { showSheet = false },
                onApply = { filter, member ->
                    // Apply filter logic here
                    println("Applied filter: $filter, member: $member")
                    showSheet = false
                }
            )

        }}
}


@Composable
fun TabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF2C2C2C) else Color.White,
            contentColor = if (selected) Color.White else Color(0xFF697383)
        ),
        shape = RoundedCornerShape(50.dp),
        border = if (!selected) BorderStroke(1.dp, Color(0xFF697383)) else null
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal
        )
    }
}









