package com.bussiness.curemegptapp.ui.screen.main.rescheduleAppointment

//RescheduleAppointmentScreen


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
import androidx.compose.ui.res.stringResource
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
import com.bussiness.curemegptapp.ui.component.ProfileInputMultipleLineField2
import com.bussiness.curemegptapp.ui.component.TopBarHeader1
import com.bussiness.curemegptapp.ui.component.UniversalInputField
import com.bussiness.curemegptapp.ui.component.input.CustomPowerSpinner
import com.bussiness.curemegptapp.ui.dialog.CalendarDialog
import com.bussiness.curemegptapp.ui.dialog.SuccessfulDialog
import com.bussiness.curemegptapp.ui.dialog.TimePickerDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RescheduleAppointmentScreen(
    navController: NavHostController
) {
    // DUMMY DATA FOR RESCHEDULING APPOINTMENT
    var dateOfBirth by remember { mutableStateOf("06-20-2024") } // DUMMY: Next week Thursday
    var time by remember { mutableStateOf("14:30:00") } // DUMMY: Afternoon appointment
    var preferredDoctor by remember { mutableStateOf("Dr. Sarah Miller") } // DUMMY: Cardiologist
    var preferredClinic by remember { mutableStateOf("Heart Care Specialists") } // DUMMY: Clinic name
    var selectedMember by remember { mutableStateOf("Myself") } // DUMMY: For myself
    var selectedAppointmentReminder by remember { mutableStateOf("30 Min Before") } // DUMMY: Standard reminder
    val memberOptions = listOf(
        stringResource(R.string.reschedule_for_myself),
        stringResource(R.string.member_jane_smith),
        stringResource(R.string.member_alice_johnson),
        stringResource(R.string.member_bob_williams)
    )
    val selectedAppointmentReminderOptions = listOf(
        stringResource(R.string.appointment_reminder_10_min),
        stringResource(R.string.appointment_reminder_30_min),
        stringResource(R.string.appointment_reminder_2_hrs),
        stringResource(R.string.appointment_reminder_24_hrs)
    )
    var selectedAppointment by remember { mutableStateOf("Heart Check-up") } // DUMMY: Cardiology appointment
    var description by remember { mutableStateOf("Follow-up appointment for hypertension management. Need to discuss medication adjustments and recent ECG results. Please bring all previous reports.") } // DUMMY: Detailed description
    var showDialog by remember { mutableStateOf(false) }
    var showDialog1 by remember { mutableStateOf(false) }
    var showDialogSuccessFully by remember { mutableStateOf(false) }
    val appointmentOptions = listOf(
        stringResource(R.string.appointment_normal_checkup),
        stringResource(R.string.appointment_dental_checkup),
        stringResource(R.string.appointment_root_canal),
        stringResource(R.string.appointment_brain_checkup),
        stringResource(R.string.appointment_hair_checkup),
        stringResource(R.string.appointment_skin_checkup),
        stringResource(R.string.appointment_heart_checkup),
        stringResource(R.string.appointment_lungs_checkup),
        stringResource(R.string.appointment_liver_checkup),
        stringResource(R.string.appointment_intestine_checkup),
        stringResource(R.string.appointment_kidney_checkup),
        stringResource(R.string.appointment_bones_checkup),
        stringResource(R.string.appointment_feet_checkup),
        stringResource(R.string.appointment_hand_checkup),
        stringResource(R.string.appointment_ent_checkup)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFFFFFF))
    ) {

        TopBarHeader1(
            title = stringResource(R.string.reschedule_appointment_title),
            onBackClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 19.dp, vertical = 30.dp)
        ) {

            Text(
                text = stringResource(R.string.for_whom_label),
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
                horizontalPadding = 24.dp,
                reasons = memberOptions
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.appointment_type_label),
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
                horizontalPadding = 24.dp,
                reasons = appointmentOptions
            )

            Spacer(Modifier.height(24.dp))

            ProfileInputMultipleLineField2(
                label = stringResource(R.string.description_label),
                isImportant = false,
                placeholder = stringResource(R.string.type_here_placeholder),
                value = description,
                onValueChange = { description = it },
                heightOfEditText = 135.dp,
                paddingHorizontal = 0.dp,
                borderColor = Color(0xFF697383),
                textColor = Color(0xFF697383)
            )

            Spacer(Modifier.width(24.dp))

            Row(modifier = Modifier.padding(horizontal = 5.dp)) {
                UniversalInputField(
                    title = stringResource(R.string.date_label),
                    isImportant = false,
                    placeholder = stringResource(R.string.date_format_placeholder),
                    value = dateOfBirth,
                    modifier = Modifier.weight(1f),
                    rightIcon = R.drawable.ic_calender_icon
                ) {
                    showDialog = true
                }
                Spacer(Modifier.width(5.dp))
                UniversalInputField(
                    title = stringResource(R.string.time_label),
                    isImportant = false,
                    placeholder = stringResource(R.string.time_format_placeholder),
                    value = time,
                    modifier = Modifier.weight(1f),
                    rightIcon = R.drawable.ic_appointed_gray_icon
                ) {
                    showDialog1 = true
                }
            }

            Spacer(Modifier.width(24.dp))

            ProfileInputField(
                label = stringResource(R.string.preferred_doctor_label),
                isImportant = false,
                placeholder = stringResource(R.string.doctor_placeholder),
                value = preferredDoctor,
                onValueChange = { preferredDoctor = it }
            )

            Spacer(Modifier.width(24.dp))

            ProfileInputField(
                label = stringResource(R.string.preferred_clinic_label),
                isImportant = false,
                placeholder = stringResource(R.string.clinic_placeholder),
                value = preferredClinic,
                onValueChange = { preferredClinic = it }
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.appointment_reminder_label),
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
                horizontalPadding = 24.dp,
                reasons = selectedAppointmentReminderOptions
            )

            Spacer(Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                CancelButton(
                    title = stringResource(R.string.cancel_button)
                ) {
                    navController.popBackStack()
                }

                ContinueButton(
                    text = stringResource(R.string.reschedule_button)
                ) {
                    // In real app, this would update appointment in database
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
                showDialog = false
                dateOfBirth = it.toString()
            }
        )
    }

    if (showDialog1) {
        TimePickerDialog(
            onDismiss = {
                showDialog1 = false
            },
            onTimeApplied = {
                time = it
                showDialog1 = false
            }
        )
    }

    if (showDialogSuccessFully) {
        SuccessfulDialog(
            title = stringResource(R.string.reschedule_success_title),
            description = stringResource(R.string.reschedule_success_description),
            onDismiss = {
                showDialogSuccessFully = false
                navController.popBackStack()
            },
            onOkClick = {
                showDialogSuccessFully = false
                navController.popBackStack()
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RescheduleAppointmentScreenPreview() {
    val navController = rememberNavController()
    RescheduleAppointmentScreen(navController = navController)
}


