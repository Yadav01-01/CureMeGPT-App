package com.bussiness.curemegptapp.ui.screen.healthReports

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.CommonHeader
import com.bussiness.curemegptapp.ui.component.GradientRedButton
import com.bussiness.curemegptapp.ui.dialog.AlertCardDialog
import com.bussiness.curemegptapp.ui.screen.main.schedule.Appointment
import com.bussiness.curemegptapp.ui.screen.main.schedule.AppointmentCard
import com.bussiness.curemegptapp.ui.screen.main.schedule.Medication
import com.bussiness.curemegptapp.ui.screen.main.schedule.MedicationTime
import com.bussiness.curemegptapp.ui.screen.main.schedule.MedicationsCard
import com.bussiness.curemegptapp.ui.screen.main.schedule.TabButton
import com.bussiness.curemegptapp.ui.sheet.BottomSheetDialog
import com.bussiness.curemegptapp.ui.sheet.BottomSheetDialogProperties
import com.bussiness.curemegptapp.ui.sheet.FilterAppointmentsBottomSheet
import com.bussiness.curemegptapp.ui.sheet.FilterFamilyMembersSheet

//Health Reports


@Composable
fun HealthReportsScreen(navController: NavHostController) {
    var showSheet by remember { mutableStateOf(false) }
    var showSheet1 by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDeleteDialog1 by remember { mutableStateOf(false) }
    var members: List<String> = listOf(
        "James (Myself)",
        "Rose Logan (Spouse)",
        "Peter Logan (Son)"
    )
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

    val medication = listOf(
        Medication(
            icon = R.drawable.ic_medication_icon, // You'll need to add this icon
            title = "Albuterol Inhaler 2 puffs",
            patientName = "Peter Logan",
            medicationType = "Medication",
            frequency = "Weekly",
            days = "Monday, Tuesday",
            times = listOf(
                MedicationTime("09:00 AM", false),
                MedicationTime("09:00 PM", false),
                MedicationTime("10:00 AM", false),
                MedicationTime("04:00 PM", false)
            ),
            startDate = "08/28/2025",
            endDate = "10/28/2025",
            instructions = "For asthma symptoms",
            isVisibleItem = true
        ),
        Medication(
            icon = R.drawable.ic_medication_icon, // You'll need to add this icon
            title = "Albuterol Inhaler",
            patientName = "Peter Logan",
            medicationType = "Medication",
            frequency = "Weekly",
            days = "Monday, Tuesday",
            times = listOf(
                MedicationTime("09:00 AM", false),
                MedicationTime("09:00 PM", false),
                MedicationTime("10:00 AM", false),
                MedicationTime("04:00 PM", false)
            ),
            startDate = "08/28/2025",
            endDate = "10/28/2025",
            instructions = "For asthma symptoms",
            isVisibleItem = true
        ),
        Medication(
            icon = R.drawable.ic_medication_icon, // You'll need to add this icon
            title = "Supplements Name",
            patientName = "Peter Logan",
            medicationType = "Medication",
            frequency = "Weekly",
            days = "Monday, Tuesday",
            times = listOf(
                MedicationTime("09:00 AM", false),
                MedicationTime("09:00 PM", false),
                MedicationTime("10:00 AM", false),
                MedicationTime("04:00 PM", false),
                MedicationTime("11:00 AM", false),
                MedicationTime("12:00 PM", false),
                MedicationTime("13:00 AM", false),
                MedicationTime("14:00 PM", false)
            ),
            startDate = "08/28/2025",
            endDate = "10/28/2025",
            instructions = "For asthma symptoms",
            isVisibleItem = true
        )
    )

    val filteredList = appointments.filter { item ->
        item.title.contains(searchQuery, ignoreCase = true)
    }
    val filteredList1 = medication.filter { item ->
        item.title.contains(searchQuery, ignoreCase = true)
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)) .statusBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CommonHeader("Health Reports")



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
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable() {
                                showSheet1 = true
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
                    items(filteredList1) { medication ->
                        MedicationsCard(medication = medication, onEditClick = {}, onDeleteClick = {showDeleteDialog1 = true})
                    }
                }

            }




    }


    if (showSheet) {
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

        }
    }

    if (showSheet1) {
        BottomSheetDialog(
            onDismissRequest = {

                showSheet1 = false
            },
            properties = BottomSheetDialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                dismissWithAnimation = true,
                enableEdgeToEdge = false,
            )
        ) {

            FilterFamilyMembersSheet(
                members = members
            )

        }
    }


    if (showDeleteDialog) {
        AlertCardDialog(
            icon = R.drawable.ic_delete_icon_new,
            title = "Delete Appointment?",
            message = "Are you sure you want to delete Peter’s appointment? This action cannot be undone.",
            confirmText = "Delete",
            cancelText = "Cancel",
            onDismiss = { showDeleteDialog = false},
            onConfirm = {  showDeleteDialog = false
            }
        )

    }

    if (showDeleteDialog1) {
        AlertCardDialog(
            icon = R.drawable.ic_delete_icon_new,
            title = "Delete Medication?",
            message = "Are you sure you want to delete Rosy’s medication? This action cannot be undone.",
            confirmText = "Delete",
            cancelText = "Cancel",
            onDismiss = { showDeleteDialog1 = false},
            onConfirm = {  showDeleteDialog1 = false
            }
        )

    }
}