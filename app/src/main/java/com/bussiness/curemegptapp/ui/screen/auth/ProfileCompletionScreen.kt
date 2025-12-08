package com.bussiness.curemegptapp.ui.screen.auth


import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.data.model.ProfileData
import com.bussiness.curemegptapp.ui.component.*
import com.bussiness.curemegptapp.ui.viewModel.auth.ProfileCompletionViewModel

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.dialog.AlertCardDialog
import com.bussiness.curemegptapp.ui.dialog.CalendarDialog
import com.bussiness.curemegptapp.ui.dialog.CompleteProfileDialog
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileCompletionScreen(
    navController: NavHostController,
    viewModel: ProfileCompletionViewModel = hiltViewModel()
) {
    val currentStep by remember { viewModel.currentStep }
    val profileData by remember { viewModel.profileData }
    var showAlertDialog by remember { mutableStateOf(false) }
    var showAlertDialog2 by remember { mutableStateOf(false) }
    var showAlertDialog3 by remember { mutableStateOf(false) }
    var showCompleteDialog by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(0xFFFFFFFF))
    ) {
        // Top Bar
        TopBarHeader(
            currentStep = currentStep,
            onBackClick = { viewModel.goToPreviousStep() }
        )

        when (currentStep) {
            0 -> PersonalInfoStep(
                viewModel = viewModel,
                profileData = profileData,
                onNext = { viewModel.goToNextStep() }
            )
            1 -> GeneralInfoStep(
                viewModel = viewModel,
                profileData = profileData,
                onNext = { viewModel.goToNextStep() }
            )
            2 -> HistoryStep(
                viewModel = viewModel,
                profileData = profileData,
                onNext = { viewModel.goToNextStep() }
            )
            3 -> DocumentsStep(
                viewModel = viewModel,
                profileData = profileData,
                onNext = {
                    viewModel.submitProfile()
                    showAlertDialog = true  // Dialog show करें
                }
            )
        }
    }

    if (showAlertDialog) {
        AlertCardDialog(
            icon = R.drawable.ic_check,
            title = "Profile Setup Completed!",
            message = "You can now add your family members or start asking AI for help.",
            confirmText = "Go to Ask AI",
            cancelText = "Add Member",
            onDismiss = { showAlertDialog = false},
            onConfirm = {  showAlertDialog = false
                showAlertDialog2 = true}
        )

    }
    if (showAlertDialog2) {
        AlertCardDialog(
            icon =  R.drawable.ic_medication,
            title = "Medication Reminder",
            message = "Time to take your",
            highlightText = "Lisinopril 10mg.",
            warningText = "Don't forget to take it with food",
            cancelText = "Snooze",
            confirmText = "Mark As Taken",
            onDismiss = { showAlertDialog2 = false },
            onConfirm = { showAlertDialog2 = false
                showAlertDialog3 = true  }
        )

    }

    if (showAlertDialog3) {
        AlertCardDialog(
            icon =  R.drawable.ic_appointment,
            title = "Dental Cleaning Today",
            message = "Don't forget your dental cleaning appointment ",
            highlightText = "today at 2:00 PM with Dr. Sarah Johnson.",
            cancelText = "Remind Me Later",
            confirmText = "Got It",
            onDismiss = { showAlertDialog3 = false },
            onConfirm = { showAlertDialog3 = false
                showCompleteDialog = true}
        )

        if (showCompleteDialog) {
            CompleteProfileDialog(
                icon = R.drawable.ic_person_complete_icon,
                title = "Complete Your Profile",
                checklist = listOf(
                    "Faster AI answers tailored to you",
                    "Safer medication & allergy checks",
                    "Quicker reminders & records Complete Now"
                ),
                cancelText = "Remind Me Later",
                confirmText = "Complete Now",
                skipText = "Skip for Now",

                onDismiss = {
                    showCompleteDialog = false
                },
                onConfirm = {
                    showCompleteDialog = false
                   navController.navigate(AppDestination.MainScreen)
                },
                onSkip = {
                    showCompleteDialog = false
                    // TODO – "Skip for Now"
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalInfoStep(
    viewModel: ProfileCompletionViewModel,
    profileData: ProfileData,
    onNext: () -> Unit
) {
    var fullName by remember { mutableStateOf(profileData.fullName) }
    var contactNumber by remember { mutableStateOf(profileData.contactNumber) }
    var email by remember { mutableStateOf(profileData.email) }
    var dateOfBirth by remember { mutableStateOf(profileData.dateOfBirth) }
    var gender by remember { mutableStateOf(profileData.gender) }
    var showDialog by remember { mutableStateOf(false) }

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


    // Height and weight with units
    var heightValue by remember {
        mutableStateOf(profileData.height.split(" ").getOrNull(0) ?: "")
    }
    var heightUnit by remember {
        mutableStateOf(profileData.height.split(" ").getOrNull(1) ?: "Cm")
    }

    var weightValue by remember {
        mutableStateOf(profileData.weight.split(" ").getOrNull(0) ?: "")
    }
    var weightUnit by remember {
        mutableStateOf(profileData.weight.split(" ").getOrNull(1) ?: "Kg")
    }

    var selectedProfilePhotoUri by remember { mutableStateOf(profileData.profilePhotoUri) }
    var selectedProfilePhotoName by remember {
        mutableStateOf(profileData.profilePhotoUri?.lastPathSegment ?: "No file chosen")
    }

    val profilePhotoPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                selectedProfilePhotoUri = it
                selectedProfilePhotoName = it.lastPathSegment ?: "selected_file"
            }
        }

    fun openProfilePhotoPicker() {
        profilePhotoPickerLauncher.launch(arrayOf("image/*"))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ProfileInputField(
            label = "Full Name",
            isImportant = true,
            placeholder = "e.g., James Carter",
            value = fullName,
            onValueChange = { fullName = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileInputField(
            label = "Contact Number",
            placeholder = "555 123 456",
            value = contactNumber,
            onValueChange = { contactNumber = it },
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileInputField(
            label = "Email Address",
            placeholder = "james@gmail.com",
            value = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(24.dp))

        UniversalInputField(
            title = "Date of Birth",
            isImportant = true,
            placeholder = "MM-DD-YYYY",
            value = dateOfBirth,
            rightIcon = R.drawable.ic_calender_icon
        ) {
            showDialog = true
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderDropdown(
            selected = gender,
            onSelected = { gender = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Dropdown1(
            label = "Height (Cm/Ft)",
            isImportant = true,
            placeholder = "e.g., 172 Cm",
            value = heightValue,
            onValueChange = { heightValue = it },
            dropdownItems = listOf("Cm", "Ft"),
            selectedUnit = heightUnit,
            onUnitSelected = { newUnit ->
                heightUnit = newUnit
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Dropdown1(
            label = "Weight (Kg/Lb)",
            isImportant = true,
            placeholder = "e.g., 78 Kg",
            value = weightValue,
            onValueChange = { weightValue = it },
            dropdownItems = listOf("Kg", "Lb"),
            selectedUnit = weightUnit,
            onUnitSelected = { newUnit ->
                weightUnit = newUnit
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfilePhotoPicker(
            label = "Profile Photo (Optional)",
            fileName = selectedProfilePhotoName,
            onChooseClick = { openProfilePhotoPicker() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = "Save & Continue",
            onClick = {
                val height = if (heightValue.isNotBlank()) "$heightValue $heightUnit" else ""
                val weight = if (weightValue.isNotBlank()) "$weightValue $weightUnit" else ""

                viewModel.updatePersonalInfo(
                    fullName = fullName,
                    contactNumber = contactNumber,
                    email = email,
                    dateOfBirth = dateOfBirth,
                    gender = gender,
                    height = height,
                    weight = weight,
                    profilePhotoUri = selectedProfilePhotoUri
                )
                onNext()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun GeneralInfoStep(
    viewModel: ProfileCompletionViewModel,
    profileData: ProfileData,
    onNext: () -> Unit
) {
    var bloodGroup by remember { mutableStateOf(profileData.bloodGroup) }
    var selectedAllergies by remember { mutableStateOf(profileData.allergies.toSet()) }
    var customAllergy by remember { mutableStateOf("") }
    var emergencyName by remember { mutableStateOf(profileData.emergencyContactName) }
    var emergencyPhone by remember { mutableStateOf(profileData.emergencyContactPhone) }

    val allergyOptions = listOf(
        "Drug", "Food", "Environmental", "Aspirin",
        "Latex", "Ibuprofen", "Shellfish", "Nuts",
        "Penicillin", "Others"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        BloodGroupDropdown(
            selected = bloodGroup,
            onSelected = { bloodGroup = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Known Allergies *",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 600.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = false
        ) {
            items(allergyOptions) { item ->
                val isSelected = item in selectedAllergies
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .border(
                            1.dp,
                            if (isSelected) Color(0xFF5B4FFF) else Color(0xFFE0E0E0),
                            RoundedCornerShape(40.dp)
                        )
                        .background(
                            if (isSelected) Color(0x205B4FFF) else Color.Transparent
                        )
                        .clickable {
                            selectedAllergies = if (isSelected)
                                selectedAllergies - item
                            else
                                selectedAllergies + item
                        }
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        fontSize = 13.sp,
                        color = if (isSelected) Color(0xFF5B4FFF) else Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if ("Others" in selectedAllergies) {
            ProfileInputWithoutLabelField(
                placeholder = "Write allergy",
                value = customAllergy,
                onValueChange = { customAllergy = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        ProfileInputField(
            label = "Emergency Contact Name (Optional)",
            isImportant = false,
            placeholder = "e.g. Bob Dsouza",
            value = emergencyName,
            onValueChange = { emergencyName = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInputField(
            label = "Emergency Phone Number (Optional)",
            isImportant = false,
            placeholder = "e.g. 555 945 325",
            value = emergencyPhone,
            onValueChange = { emergencyPhone = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = "Save & Continue",
            onClick = {
                val allergiesList = selectedAllergies.toMutableList()
                if ("Others" in selectedAllergies && customAllergy.isNotEmpty()) {
                    allergiesList.add(customAllergy)
                }

                viewModel.updateGeneralInfo(
                    bloodGroup = bloodGroup,
                    allergies = allergiesList,
                    emergencyName = emergencyName,
                    emergencyPhone = emergencyPhone
                )
                onNext()
            }
        )
    }
}

@Composable
fun HistoryStep(
    viewModel: ProfileCompletionViewModel,
    profileData: ProfileData,
    onNext: () -> Unit
) {
    var selectedConditions by remember { mutableStateOf(setOf<String>()) }
    var surgicalHistory by remember { mutableStateOf("") }
    var currentMedications by remember { mutableStateOf(listOf("")) }
    var currentSupplements by remember { mutableStateOf(listOf("")) }
    var customCondition by remember { mutableStateOf("") }
    val conditions = listOf(
        "Diabetes", "Asthma", "Hypertension", "Thyroid",
        "Arthritis", "Heart Disease", "Anxiety", "Depression", "Others"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Chronic Conditions *",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ⭐ Same UI as allergies → 3-column Grid chips
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 600.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = false
        ) {

            items(conditions) { condition ->

                val isSelected = condition in selectedConditions

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .border(
                            1.dp,
                            if (isSelected) Color(0xFF5B4FFF) else Color(0xFFE0E0E0),
                            RoundedCornerShape(40.dp)
                        )
                        .background(
                            if (isSelected) Color(0x205B4FFF) else Color.Transparent
                        )
                        .clickable {
                            selectedConditions =
                                if (isSelected) selectedConditions - condition
                                else selectedConditions + condition
                        }
                        .padding(vertical = 6.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = condition,
                        fontSize = 13.sp,
                        color = if (isSelected) Color(0xFF5B4FFF) else Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // ⭐ Custom allergy field — only if Others selected
        if ("Others" in selectedConditions) {

            ProfileInputWithoutLabelField(
                placeholder = "Write Condition",
                value = customCondition,
                onValueChange = { customCondition = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInputMultipleLineField(
            label = "Surgical History (Optional)",
            isImportant = false,
            placeholder = "Any previous surgeries or major medical procedures...",
            value = surgicalHistory,
            onValueChange = { surgicalHistory = it }
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Current Medications (Optional)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))

        currentMedications.forEachIndexed { index, medication ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = medication,
                    onValueChange = { newValue ->
                        // ⭐ Sirf first item editable hona chahiye
                        if (index == 0) {
                            val updated = currentMedications.toMutableList()
                            updated[index] = newValue
                            currentMedications = updated
                        }
                    },
                    placeholder = {
                        if (index == 0)
                            Text("Any medications you're currently taking...")
                        else
                            Text("Added item")  // non editable item placeholder
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30.dp))
                        .border(1.dp, Color(0xFFC3C6CB), RoundedCornerShape(30.dp)),
                    enabled = index == 0,    // ⭐ only first row editable
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )

                if (index == 0) {
                    // ⭐ ADD ICON only for the FIRST item
                    Image(
                        painter = painterResource(id = R.drawable.ic_add_icon),
                        contentDescription = "Add",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {

                                // ⭐ Add tabhi hoga jab first field empty na ho
                                if (currentMedications[0].isNotBlank()) {
                                    //   currentMedications = currentMedications + medication
                                    val updated = currentMedications.toMutableList()

                                    // ⭐ 1. Add a new empty item at bottom
                                    updated.add(currentMedications[0])

                                    // ⭐ 2. Clear first input field
                                    updated[0] = ""

                                    currentMedications = updated
                                }
                            }
                    )
                } else {
                    // ⭐ Other items → REMOVE icon
                    Image(
                        painter = painterResource(id = R.drawable.ic_remove_icon),
                        contentDescription = "Remove",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                val updated = currentMedications.toMutableList()
                                updated.removeAt(index)
                                currentMedications = updated
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }



        Spacer(modifier = Modifier.height(16.dp))


        // ⭐ Current Supplements
        Text(
            text = "Current Supplements (Optional)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))

        currentSupplements.forEachIndexed { index, supplement ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = supplement,
                    onValueChange = { newValue ->
                        // ⭐ Sirf first item editable hona chahiye
                        if (index == 0) {
                            val updated = currentSupplements.toMutableList()
                            updated[index] = newValue
                            currentSupplements = updated
                        }
                    },
                    placeholder = {
                        if (index == 0)
                            Text("Any supplements you're currently taking...")
                        else
                            Text("Added item")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30.dp))
                        .border(
                            1.dp,
                            Color(0xFFC3C6CB),
                            RoundedCornerShape(30.dp)
                        ),
                    enabled = index == 0,   // ⭐ only first item editable
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        disabledContainerColor = Color.White, // ⭐ FIX
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )

                if (index == 0) {
                    // ⭐ ADD icon - first item only
                    Image(
                        painter = painterResource(id = R.drawable.ic_add_icon),
                        contentDescription = "Add",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {

                                if (currentSupplements[0].isNotBlank()) {

                                    val updated = currentSupplements.toMutableList()

                                    // ⭐ Add new item in list
                                    updated.add(currentSupplements[0])

                                    // ⭐ Clear first input
                                    updated[0] = ""

                                    currentSupplements = updated
                                }
                            }
                    )
                } else {
                    // ⭐ REMOVE icon - for added items
                    Image(
                        painter = painterResource(id = R.drawable.ic_remove_icon),
                        contentDescription = "Remove",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                val updated = currentSupplements.toMutableList()
                                updated.removeAt(index)
                                currentSupplements = updated
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }


        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = "Save & Continue",
            onClick = {
                val conditionsList = selectedConditions.toMutableList()
                if ("Others" in selectedConditions && customCondition.isNotEmpty()) {
                    conditionsList.add(customCondition)
                }

                viewModel.updateMedicalHistory(
                    chronicConditions = conditionsList,
                    surgicalHistory = surgicalHistory,
                    currentMedications = currentMedications.filter { it.isNotBlank() },
                    currentSupplements = currentSupplements.filter { it.isNotBlank() }
                )
                onNext()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DocumentsStep(
    viewModel: ProfileCompletionViewModel,
    profileData: ProfileData,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val filePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris?.forEach { uri ->
            viewModel.addUploadedFile(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ProfilePhotoPicker(
            label = "Upload Files (X-Rays, Dental Scans, Prescriptions, Lab Reports)",
            fileName = if (profileData.uploadedFiles.isEmpty()) "No file chosen"
            else "${profileData.uploadedFiles.size} files selected",
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
                .clickable {
                    filePickerLauncher.launch(arrayOf("image/*", "application/pdf", "application/dicom"))
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE7E6F8),
                    shape = RoundedCornerShape(30.dp)
                )
                .background(
                    color = Color(0xFFF9F9FD),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(20.dp)
        ) {
            Text(
                text = "Attached Files",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (profileData.uploadedFiles.isEmpty()) {
                Text(
                    text = "No files uploaded",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            } else {
                profileData.uploadedFiles.forEach { fileUri ->
                    FileAttachment(
                        fileName = fileUri.lastPathSegment ?: "file",
                        onDeleteClick = { viewModel.removeUploadedFile(fileUri) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        DisclaimerBox(
            title = "You're almost ready!",
            description = "You can always add more details, upload documents, or update your profile later from the settings menu.",
            titleColor = Color(0xFF4338CA),
            backColor = Color(0x084338CA)
        )

        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = "Get Started",
            onClick = {
                viewModel.submitProfile()

                // Toast show karein summary
                val summary = """
                    Profile Completed!
                    Name: ${profileData.fullName}
                    Contact: ${profileData.contactNumber}
                    Email: ${profileData.email}
                    Files: ${profileData.uploadedFiles.size}
                """.trimIndent()

                Toast.makeText(context, "Profile data logged in Logcat", Toast.LENGTH_LONG)
                    .show()
                onNext()

            }
        )

        Spacer(modifier = Modifier.height(16.dp))


    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProfileCompletionScreenPreview() {
    val navController = rememberNavController()
    ProfileCompletionScreen(navController = navController)
}