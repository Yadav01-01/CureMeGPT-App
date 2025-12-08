package com.bussiness.curemegptapp.ui.screen.main.scheduleNewAppointment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.CancelButton
import com.bussiness.curemegptapp.ui.component.ContinueButton
import com.bussiness.curemegptapp.ui.component.ProfileInputField
import com.bussiness.curemegptapp.ui.component.ProfileInputMultipleLineField
import com.bussiness.curemegptapp.ui.component.ProfileInputMultipleLineField2
import com.bussiness.curemegptapp.ui.component.TopBarHeader1
import com.bussiness.curemegptapp.ui.component.UniversalInputField
import com.bussiness.curemegptapp.ui.component.input.CustomPowerSpinner
import com.bussiness.curemegptapp.ui.dialog.CalendarDialog
import com.bussiness.curemegptapp.ui.dialog.SuccessfulDialog
import com.bussiness.curemegptapp.ui.dialog.TimePickerDialog
import com.bussiness.curemegptapp.ui.screen.auth.ProfileCompletionScreen

//ScheduleNewAppointmentScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleNewAppointmentScreen(
    navController: NavHostController
) {
    var dateOfBirth by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var preferredDoctor by remember { mutableStateOf("") }
    var preferredClinic by remember { mutableStateOf("") }
    var selectedMember by remember { mutableStateOf("Select Member") }
    var selectedAppointmentReminder by remember { mutableStateOf("Select Member") }
    val memberOptions =
        listOf("John Doe", "Jane Smith", "Alice Johnson", "Bob Williams") // Added example options
      val selectedAppointmentReminderOptions =
        listOf("10 Min Before", "30 Min Before", "2 Hrs. Before", "24 Hrs. Before") // Added example options
    var selectedAppointment by remember { mutableStateOf("Select Appointment Type") }
    var description by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var showDialog1 by remember { mutableStateOf(false) }
    var showDialogSuccessFully by remember { mutableStateOf(false) }
    val appointmentOptions = listOf(
        "Normal Check-up",
        "Dental Check-up",
        "Root Canal",
        "Brain Check-up",
        "Hair Check-up",
        "Skin Check-up",
        "Heart Check-up",
        "Lungs Check-up",
        "Liver Check-up",
        "Intestine Check-up",
        "Kidney Check-up",
        "Bones Check-up",
        "Feet Check-up",
        "Hand Check-up",
        "ENT Check-up"
    )

    Column(
        modifier = Modifier
            .fillMaxSize() .statusBarsPadding().verticalScroll(rememberScrollState())
            .background(Color(0xFFFFFFFF))
    ) {

        TopBarHeader1(title = "Schedule New Appointment", onBackClick = {})

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 19.dp, vertical = 30.dp)
        ) {


            Text(
                text = "For Whom",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(8.dp))

            CustomPowerSpinner(
                selectedText = selectedMember,
                onSelectionChanged = { reason ->
                    selectedMember = reason
                },
                menuPadding = 24.dp,
                reasons = memberOptions // Pass the list of options here
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = "Appointment Type",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(8.dp))
            CustomPowerSpinner(
                selectedText = selectedAppointment,
                onSelectionChanged = { reason ->
                    selectedAppointment = reason
                },
                menuPadding = 24.dp,
                reasons = appointmentOptions // Pass the list of options here
            )

            Spacer(Modifier.height(24.dp))
            ProfileInputMultipleLineField2(
                label = "Description",
                isImportant = false,
                placeholder = "Type here....",
                value = description,
                onValueChange = { description = it },
                heightOfEditText = 135.dp,
                paddingHorizontal = 0.dp,
                borderColor = Color(0xFF697383),
                textColor = Color(0xFF697383)
            )

            Spacer(Modifier.width(24.dp))

            Row (modifier = Modifier.padding(horizontal = 5.dp)) {
                UniversalInputField(
                    title = "Date",
                    isImportant = false,
                    placeholder = "MM-DD-YYYY",
                    value = dateOfBirth,
                    modifier = Modifier.weight(1f),
                    rightIcon = R.drawable.ic_calender_icon
                ) {
                    showDialog = true
                }
                Spacer(Modifier.width(5.dp))
                UniversalInputField(
                    title = "Time",
                    isImportant = false,
                    placeholder = "00:00:00",
                    value = time,
                    modifier = Modifier.weight(1f),
                    rightIcon = R.drawable.ic_appointed_gray_icon
                ) {
                    showDialog1 = true
                }

            }
            Spacer(Modifier.width(24.dp))

            ProfileInputField(
                label = "Preferred Doctor",
                isImportant = false,
                placeholder = "e.g., Dr. John Deo",
                value = preferredDoctor,
                onValueChange = { preferredDoctor = it }
            )

            Spacer(Modifier.width(24.dp))

            ProfileInputField(
                label = "Preferred Clinic",
                isImportant = false,
                placeholder = "e.g., Bright Smile Dental",
                value = preferredClinic,
                onValueChange = { preferredClinic = it }
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Appointment Reminder",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(8.dp))

            CustomPowerSpinner(
                selectedText = selectedAppointmentReminder,
                onSelectionChanged = { reason ->
                    selectedAppointmentReminder = reason
                },
                menuPadding = 24.dp,
                reasons = selectedAppointmentReminderOptions // Pass the list of options here
            )

            Spacer(Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                CancelButton(title = "Cancel") {

                }

                ContinueButton(text = "Schedule") {
                    showDialogSuccessFully = true
                }
            }
            Spacer(Modifier.height(37.dp))
        }
    }
    if (showDialog) {
        CalendarDialog(
            onDismiss = { showDialog = false },
            onDateApplied = {
                // SELECTED DATE HERE
                showDialog = false
                dateOfBirth = it.toString()
            }
        )
    }
    if (showDialog1) {
        TimePickerDialog(
            onDismiss = {
                showDialog1 = false
            }, onTimeApplied = {
                time= it
                showDialog1 = false
            }
        )
    }

    if (showDialogSuccessFully) {
        SuccessfulDialog(title = "Appointment Scheduled \nSuccessfully", description = "Your appointment reminder are set.",
            onDismiss = { showDialogSuccessFully = false },
            onOkClick = { showDialogSuccessFully = false }
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ScheduleNewAppointmentScreenPreview() {
    val navController = rememberNavController()
    ScheduleNewAppointmentScreen(navController = navController)
}