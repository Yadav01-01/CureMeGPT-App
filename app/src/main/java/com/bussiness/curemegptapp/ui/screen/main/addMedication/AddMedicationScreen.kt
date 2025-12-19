package com.bussiness.curemegptapp.ui.screen.main.addMedication

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.CancelButton
import com.bussiness.curemegptapp.ui.component.ContinueButton
import com.bussiness.curemegptapp.ui.component.FileAttachment
import com.bussiness.curemegptapp.ui.component.ProfileInputField
import com.bussiness.curemegptapp.ui.component.ProfileInputMultipleLineField2
import com.bussiness.curemegptapp.ui.component.ProfileInputSmallField
import com.bussiness.curemegptapp.ui.component.ProfilePhotoPicker
import com.bussiness.curemegptapp.ui.component.RoundedCustomCheckbox
import com.bussiness.curemegptapp.ui.component.TopBarHeader1
import com.bussiness.curemegptapp.ui.component.UniversalInputField
import com.bussiness.curemegptapp.ui.component.input.CustomPowerSpinner
import com.bussiness.curemegptapp.ui.dialog.CalendarDialog
import com.bussiness.curemegptapp.ui.dialog.SuccessfulDialog
import com.bussiness.curemegptapp.ui.dialog.TimePickerDialog

//AddMedicationScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMedicationScreen(
    navController: NavHostController
) {
    var dateOfBirth by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var currentReminder by remember { mutableStateOf("") }
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var selectedMyself by remember { mutableStateOf("Myself") }
    var selectFrequency by remember { mutableStateOf("Select Frequency") }
    var selectDayName by remember { mutableStateOf("Select Frequency") }
    val myselfOptions =
        listOf("Myself", "Jane Smith", "Alice Johnson", "Bob Williams") // Added example options
    val selectedMedicationTypeOptions =
        listOf("Medicine", "Supplements")
    val selectDayNameOptions =
        listOf(   "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday") // Added example options
    val selectFrequencyOptions =
        listOf("Daily", "Alternate Days", "Weekly") // Added example options
    var selectedMedicationType by remember { mutableStateOf("Select Appointment Type") }
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

    var currentReminderTime by remember { mutableStateOf(listOf("")) }

    var uploadedFiles by remember { mutableStateOf<Uri?>(null) }
    var checked by remember { mutableStateOf(false) }   // ⭐ FIXED

    val filePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            uploadedFiles = uri   // ⭐ Only one file stored
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize() .statusBarsPadding().verticalScroll(rememberScrollState())
            .background(Color(0xFFFFFFFF))
    ) {

        TopBarHeader1(title = "Add Medication", onBackClick = {})

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 19.dp, vertical = 30.dp)
        ) {


            Text(
                text = "For Family Member",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(8.dp))

            CustomPowerSpinner(
                selectedText = selectedMyself,
                onSelectionChanged = { reason ->
                    selectedMyself = reason
                },
                horizontalPadding = 24.dp,
                reasons = myselfOptions // Pass the list of options here
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = "Medication Type",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(8.dp))
            CustomPowerSpinner(
                selectedText = selectedMedicationType,
                onSelectionChanged = { reason ->
                    selectedMedicationType = reason
                },
                horizontalPadding = 24.dp,
                reasons = selectedMedicationTypeOptions // Pass the list of options here
            )




            Row (Modifier.padding(vertical = 24.dp)) {
                ProfileInputSmallField(
                    label = "Medication Name",
                    isImportant = false,
                    placeholder = "e.g., Amoxicillin",
                    value = medicationName,
                    onValueChange = { medicationName = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(5.dp))

                ProfileInputSmallField(
                    label = "Dosage",
                    isImportant = false,
                    placeholder = "e.g., 500mg",
                    value = dosage,
                    onValueChange = { dosage = it },
                    modifier = Modifier.weight(1f)
                )

            }



            Text(
                text = "Frequency",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(8.dp))

            CustomPowerSpinner(
                selectedText = selectFrequency,
                onSelectionChanged = { reason ->
                    selectFrequency = reason
                },
                horizontalPadding = 24.dp,
                reasons = selectFrequencyOptions // Pass the list of options here
            )

            if (selectFrequency == "Weekly")
            {
                Text(
                    text = "Day",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                    fontWeight = FontWeight.Normal
                )

                Spacer(Modifier.height(8.dp))

                CustomPowerSpinner(
                    selectedText = selectDayName,
                    onSelectionChanged = { reason ->
                        selectDayName = reason
                    },
                    horizontalPadding = 24.dp,
                    reasons = selectDayNameOptions // Pass the list of options here
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Reminder time",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.urbanist_regular))
            )
            Spacer(modifier = Modifier.height(8.dp))

            currentReminderTime.forEachIndexed { index, reminderValue ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        value = reminderValue,   // ⭐ FIXED — Row ka real value
                        onValueChange = { newValue ->
                            if (index == 0) {   // ⭐ Only first editable
                                val updated = currentReminderTime.toMutableList()
                                updated[index] = newValue
                                currentReminderTime = updated
                            }
                        },
                        placeholder = {
                            Text("00:00:00")
                        },
                        trailingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_appointed_gray_icon),
                                contentDescription = "clock",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        enabled = index == 0,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(30.dp))
                            .border(1.dp, Color(0xFF697383), RoundedCornerShape(30.dp)),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    if (index == 0) {
                        // ⭐ ADD BUTTON (only for first)
                        Image(
                            painter = painterResource(id = R.drawable.ic_add_icon),
                            contentDescription = "Add",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable( interactionSource = remember { MutableInteractionSource() },
                                    indication = null) {

                                    if (currentReminderTime[0].isNotBlank()) {

                                        val updated = currentReminderTime.toMutableList()

                                        // ⭐ Add item with first value
                                        updated.add(updated[0])

                                        // ⭐ Clear first field
                                        updated[0] = ""

                                        currentReminderTime = updated
                                    }
                                }
                        )
                    } else {
                        // ⭐ REMOVE BUTTON (for other rows)
                        Image(
                            painter = painterResource(id = R.drawable.ic_remove_icon),
                            contentDescription = "Remove",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable( interactionSource = remember { MutableInteractionSource() },
                                    indication = null) {
                                    val updatedList = currentReminderTime.toMutableList()
                                    updatedList.removeAt(index)
                                    currentReminderTime = updatedList
                                }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.width(24.dp))

            Row (modifier = Modifier.padding(horizontal = 5.dp, vertical = 24.dp)) {
                UniversalInputField(
                    title = "Start Date",
                    isImportant = false,
                    placeholder = "MM-DD-YYYY",
                    value = startDate,
                    modifier = Modifier.weight(1f),
                    rightIcon = R.drawable.ic_calender_icon
                ) {
                    showDialog = true
                }
                Spacer(Modifier.width(5.dp))
                UniversalInputField(
                    title = "End Date (Optional)",
                    isImportant = false,
                    placeholder = "MM-DD-YYYY",
                    value = endDate,
                    modifier = Modifier.weight(1f),
                    rightIcon = R.drawable.ic_calender_icon
                ) {
                    showDialog1 = true
                }

            }


            ProfilePhotoPicker(
                label = "Upload Prescription (Optional)",
                fileName = uploadedFiles?.let { getFileName(it) } ?: "No file chosen",
                onChooseClick = {
                    filePickerLauncher.launch(arrayOf("image/*", "application/pdf", "application/dicom"))
                }
            )


            Text(
                "PDF, JPG, PNG, DICOM Supported",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable( interactionSource = remember { MutableInteractionSource() },
                        indication = null) {
                        filePickerLauncher.launch(arrayOf("image/*", "application/pdf", "application/dicom"))
                    }
            )
            Spacer(Modifier.height(24.dp))
            ProfileInputMultipleLineField2(
                label = "Notes",
                isImportant = false,
                placeholder = "Special instructions, side effects to watch for....",
                value = description,
                onValueChange = { description = it },
                heightOfEditText = 135.dp,
                paddingHorizontal = 0.dp,
                borderColor = Color(0xFF697383),
                textColor = Color(0xFF697383)
            )

            Spacer(Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable( interactionSource = remember { MutableInteractionSource() },
                        indication = null) {  checked = !checked },
                verticalAlignment = Alignment.Top
            ) {


                RoundedCustomCheckbox(
                    checked = checked,
                    onCheckedChange = {  checked = it  }
                )


                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Enable reminder",
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                CancelButton(title = "Cancel") {

                }

                ContinueButton(text = "Add Medication") {
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
        CalendarDialog(
            onDismiss = { showDialog = false },
            onDateApplied = {
                // SELECTED DATE HERE
                showDialog = false
                dateOfBirth = it.toString()
            }
        )
    }

    if (showDialogSuccessFully) {
        SuccessfulDialog(title = "Medication Added \nSuccessfully", description = "Your medication has been saved and reminders are set.",
            onDismiss = { showDialogSuccessFully = false },
            onOkClick = { showDialogSuccessFully = false }
        )
    }

}

fun getFileName(uri: Uri): String {
    return uri.lastPathSegment?.substringAfterLast("/") ?: "File"
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddMedicationScreenPreview() {
    val navController = rememberNavController()
    AddMedicationScreen(navController = navController)
}