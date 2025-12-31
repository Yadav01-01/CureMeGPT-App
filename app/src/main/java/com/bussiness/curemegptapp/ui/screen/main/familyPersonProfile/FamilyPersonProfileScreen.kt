package com.bussiness.curemegptapp.ui.screen.main.familyPersonProfile


import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.dialog.AlertCardDialog
import com.bussiness.curemegptapp.ui.theme.AppGradientColors
import com.bussiness.curemegptapp.ui.viewModel.main.Document
import com.bussiness.curemegptapp.ui.viewModel.main.FamilyMember
import com.bussiness.curemegptapp.ui.viewModel.main.FamilyProfileViewModel

@Composable
fun FamilyPersonProfileScreen(
    navController: NavHostController,
    viewModel: FamilyProfileViewModel = viewModel()
) {
    var selectedProfilePhotoUri by remember { mutableStateOf<Uri?>(null) }

    val familyMember by viewModel.familyMember.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val profilePhotoPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                selectedProfilePhotoUri = it
            }
        }

    fun openProfilePhotoPicker() {
        profilePhotoPickerLauncher.launch(arrayOf("image/*"))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Loading State
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Error State
        errorMessage?.let { message ->
            Text(
                text = message,
                modifier = Modifier.align(Alignment.Center),
                color = Color.Red
            )
        }

        // Success State
        familyMember?.let { member ->
            FamilyMemberProfileContent(
                member = member,
                navController = navController,
                selectedProfilePhotoUri = selectedProfilePhotoUri,
                onEditClick = {
                    // Handle edit click
                },
                onDeleteClick = {
                    showDeleteDialog = true
                },
                onDownloadClick = { documentId ->
                    // Handle download
                },
                openProfilePhotoPicker = {
                    openProfilePhotoPicker()
                }
            )
            /*
            FamilyMemberProfileContent(
    member = member,
    navController = navController,
    selectedProfilePhotoUri = selectedProfilePhotoUri,
    onEditClick = { },
    onDeleteClick = { showDeleteDialog = true },
    onDownloadClick = { },
    openProfilePhotoPicker = { openProfilePhotoPicker() }
)
             */
        } ?: run {
            if (!isLoading && errorMessage == null) {
                Text(
                    text = stringResource(R.string.no_family_member_found),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertCardDialog(
            icon = R.drawable.ic_delete_icon_new,
            title = stringResource(R.string.delete_member_dialog_title),
            message = stringResource(R.string.delete_member_dialog_message, familyMember?.name ?: stringResource(R.string.peter_logan_son_user).split(" (")[0]),
            confirmText = stringResource(R.string.delete_button),
            cancelText = stringResource(R.string.cancel_button),
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                showDeleteDialog = false
                viewModel.deleteFamilyMember()
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun FamilyMemberProfileContent(
    member: FamilyMember,
    navController: NavHostController,
    selectedProfilePhotoUri: Uri?,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDownloadClick: (String) -> Unit,
    openProfilePhotoPicker: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            // Header with curved background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                // Purple background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.linearGradient(AppGradientColors),
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                )

                // Top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(45.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_back_nav_blue_icon),
                                contentDescription = stringResource(R.string.back_button_description)
                            )
                        }

                        Text(
                            text = member.name,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.onest_medium)),
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = onEditClick,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_edit_icon_circle),
                                contentDescription = stringResource(R.string.edit_profile_description),
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    navController.navigate(AppDestination.EditFamilyMemberDetailsScreen)
                                }
                            )
                        }

                        IconButton(
                            onClick = onDeleteClick,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_delete_icon_circle),
                                contentDescription = stringResource(R.string.delete_profile_description)
                            )
                        }
                    }
                }

                // Profile image
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(156.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(13.dp)
                    ) {
           /*             Image(
                            painter = painterResource(id = R.drawable.ic_profile_image),
                            contentDescription = stringResource(R.string.profile_photo_description, member.name),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )*/
                        if (selectedProfilePhotoUri != null) {
                            AsyncImage(
                                model = selectedProfilePhotoUri,
                                contentDescription = stringResource(R.string.profile_photo_description, member.name),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_profile_image),
                                contentDescription = stringResource(R.string.profile_photo_description, member.name),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                    }

                    // Upload button
                    IconButton(
                        onClick = { openProfilePhotoPicker() },
                        modifier = Modifier
                            .size(39.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = (-8).dp, y = (-8).dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_upload_icon),
                            contentDescription = stringResource(R.string.upload_photo_description)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }

        // Personal Information
        item {
            SectionTitle(stringResource(R.string.personal_information_title))
            InfoCard {
                InfoRow(stringResource(R.string.full_name_label), member.name)
                InfoRow(stringResource(R.string.contact_number_label), member.contactNumber)
                InfoRow(stringResource(R.string.email_label), member.email)
                InfoRow(stringResource(R.string.relation_to_you_label), member.relation)
                InfoRow(stringResource(R.string.date_of_birth_label), member.dateOfBirth)
                InfoRow(stringResource(R.string.gender_label), member.gender)
                InfoRow(stringResource(R.string.height_label), member.height)
                InfoRow(stringResource(R.string.weight_label), member.weight, isLast = true)
            }
        }

        // General Health
        item {
            SectionTitle(stringResource(R.string.general_health_title))
            InfoCard {
                InfoRow(stringResource(R.string.blood_group_label), member.bloodGroup)
                InfoRow(stringResource(R.string.known_allergies_label), member.allergies)
                InfoRow(stringResource(R.string.emergency_contact_label), member.emergencyContact)
                InfoRow(stringResource(R.string.emergency_phone_label1), member.emergencyPhone, isLast = true)
            }
        }

        // Medical History
        item {
            SectionTitle(stringResource(R.string.medical_history_title))
            InfoCard {
                InfoRow(stringResource(R.string.chronic_conditions_label), member.chronicConditions)
                InfoRow(stringResource(R.string.surgical_history_label1), member.surgicalHistory)

                Column {
                    InfoRow(
                        stringResource(R.string.current_medications_label1),
                        if (member.currentMedications.isNotEmpty()) member.currentMedications[0]
                        else stringResource(R.string.not_available_placeholder)
                    )
                    member.currentMedications.drop(1).forEach { medication ->
                        InfoRow("", medication)
                    }
                }

                Column {
                    InfoRow(
                        stringResource(R.string.current_supplements_label1),
                        if (member.currentSupplements.isNotEmpty()) member.currentSupplements[0]
                        else stringResource(R.string.not_available_placeholder)
                    )
                    member.currentSupplements.drop(1).forEach { supplement ->
                        InfoRow("", supplement)
                    }
                }
            }
        }

        // Documents
        item {
            SectionTitle(stringResource(R.string.documents))
            member.documents.forEach { document ->
                DocumentItem(
                    document = document,
                    onDownloadClick = { onDownloadClick(document.id) }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun DocumentItem(
    document: Document,
    onDownloadClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 19.dp, vertical = 4.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D7F4))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_files_icon),
                    contentDescription = stringResource(R.string.file_icon_description),
                    modifier = Modifier.size(41.dp, 55.dp)
                )
                Text(
                    text = document.fileName,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF4338CA)
                )
            }

            IconButton(
                onClick = onDownloadClick,
                modifier = Modifier.size(48.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_download_icon),
                    contentDescription = stringResource(R.string.download_file_description)
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun InfoCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(width = 1.dp, color = Color(0xFFEAE7FA), shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCFBFF))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = Color(0xFF4338CA),
            modifier = Modifier.weight(1f),
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 15.sp,
            color = Color(0xFF181818),
            modifier = Modifier.weight(1f),
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FamilyPersonProfileScreenPreview() {
    val navController = rememberNavController()
    FamilyPersonProfileScreen(navController = navController)
}

/*
@Composable
fun FamilyPersonProfileScreen(
    navController: NavHostController,
    viewModel: FamilyProfileViewModel = viewModel()
) {

    val familyMember by viewModel.familyMember.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Loading State
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Error State
        errorMessage?.let { message ->
            Text(
                text = message,
                modifier = Modifier.align(Alignment.Center),
                color = Color.Red
            )
        }

        // Success State
        familyMember?.let { member ->
            FamilyMemberProfileContent(
                member = member,
                navController = navController,
                onEditClick = {
                    // Handle edit click
                },
                onDeleteClick = {
                    showDeleteDialog = true

                },
                onDownloadClick = { documentId ->
                    // Handle download
                }
            )
        } ?: run {
            if (!isLoading && errorMessage == null) {
                Text(
                    text = "No family member found",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertCardDialog(
            icon = R.drawable.ic_delete_icon_new,
            title = "Delete Member?",
            message = "Are you sure you want to delete Peter’s profile? This action cannot be undone.",
            confirmText = "Delete",
            cancelText = "Cancel",
            onDismiss = { showDeleteDialog = false},
            onConfirm = {  showDeleteDialog = false
                viewModel.deleteFamilyMember()
                navController.popBackStack()
            }
        )

    }
}

@Composable
fun FamilyMemberProfileContent(
    member: FamilyMember,
    navController: NavHostController,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDownloadClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            // Header with curved background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                // Purple background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.linearGradient(AppGradientColors),
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                )

                // Top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(45.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_back_nav_blue_icon),
                                contentDescription = "Back"
                            )
                        }

                        Text(
                            text = member.name,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.onest_medium)),
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = onEditClick,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_edit_icon_circle),
                                contentDescription = "Edit",
                                modifier = Modifier.clickable( interactionSource = remember { MutableInteractionSource() },
                                    indication = null){
                                    navController.navigate(AppDestination.EditFamilyMemberDetailsScreen)
                                }
                            )
                        }

                        IconButton(
                            onClick = onDeleteClick,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_delete_icon_circle),
                                contentDescription = "Delete"
                            )
                        }
                    }
                }

                // Profile image
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(156.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(13.dp)
                    ) {
                        // यहाँ आप Glide या Coil का use करके network image load कर सकते हैं
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile_image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Upload button
                    IconButton(
                        onClick = { /* Handle upload */ },
                        modifier = Modifier
                            .size(39.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = (-8).dp, y = (-8).dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_upload_icon),
                            contentDescription = "Upload"
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }

        // Personal Information
        item {
            SectionTitle("Personal Information")
            InfoCard {
                InfoRow("Full Name", member.name)
                InfoRow("Contact Number", member.contactNumber)
                InfoRow("Email Address", member.email)
                InfoRow("Relation to You", member.relation)
                InfoRow("Date of Birth", member.dateOfBirth)
                InfoRow("Gender", member.gender)
                InfoRow("Height (cm/ft)", member.height)
                InfoRow("Weight (kg/lb)", member.weight, isLast = true)
            }
        }

        // General Health
        item {
            SectionTitle("General Health")
            InfoCard {
                InfoRow("Blood Group", member.bloodGroup)
                InfoRow("Known Allergies", member.allergies)
                InfoRow("Emergency Contact", member.emergencyContact)
                InfoRow("Emergency Ph.", member.emergencyPhone, isLast = true)
            }
        }

        // Medical History
        item {
            SectionTitle("Medical History")
            InfoCard {
                InfoRow("Chronic Conditions", member.chronicConditions)
                InfoRow("Surgical History", member.surgicalHistory)

                Column {
                    InfoRow("Current Medications", if (member.currentMedications.isNotEmpty()) member.currentMedications[0] else "--")
                    member.currentMedications.drop(1).forEach { medication ->
                        InfoRow("", medication)
                    }
                }

                Column {
                    InfoRow("Current Supplements", if (member.currentSupplements.isNotEmpty()) member.currentSupplements[0] else "--")
                    member.currentSupplements.drop(1).forEach { supplement ->
                        InfoRow("", supplement)
                    }
                }
            }
        }

        // Documents
        item {
            SectionTitle("Documents")
            member.documents.forEach { document ->
                DocumentItem(
                    document = document,
                    onDownloadClick = { onDownloadClick(document.id) }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }


}

@Composable
fun DocumentItem(
    document: Document,
    onDownloadClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 19.dp, vertical = 4.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D7F4))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_files_icon),
                    contentDescription = "File Icon",
                    modifier = Modifier.size(41.dp, 55.dp)
                )
                Text(
                    text = document.fileName,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF4338CA)
                )
            }

            IconButton(
                onClick = onDownloadClick,
                modifier = Modifier.size(48.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_download_icon),
                    contentDescription = "Download"
                )
            }
        }
    }
}


/*
@Composable
fun FamilyPersonProfileScreen(navController: NavHostController, viewModel: FamilyProfileViewModel = viewModel()) {

    val familyMember by viewModel.familyMember.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        // Error State


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                // Header with curved background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    // Purple background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = Brush.linearGradient(AppGradientColors),
                                RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                            )
                    )

                    // Top bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 48.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_back_nav_blue_icon),
                                contentDescription = "Back",
                                modifier = Modifier
                                    .size(45.dp)
                            )

                            Text(
                                text = "Rose Logan",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.onest_medium)),
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_edit_icon_circle),
                                contentDescription = "Edit",
                                modifier = Modifier
                                    .size(48.dp)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_delete_icon_circle),
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .size(48.dp)
                            )

                        }
                    }

                    // Profile image
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 40.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(156.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(13.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_profile_image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                        }
                        // Upload button

                        Image(
                            painter = painterResource(id = R.drawable.ic_upload_icon),
                            contentDescription = "Upload",
                            modifier = Modifier
                                .size(39.dp)
                                .align(Alignment.BottomEnd)
                                .offset(x = (-8).dp, y = (-8).dp)

                        )

                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(56.dp))
            }

            // Personal Information
            item {
                SectionTitle("Personal Information")
                InfoCard {
                    InfoRow("Full Name", "Rose Logan")

                    InfoRow("Contact Number", "+1 555 987 654")

                    InfoRow("Email Address", "rosy@gmail.com")

                    InfoRow("Relation to You", "Spouse")

                    InfoRow("Date of Birth", "05/08/1995")

                    InfoRow("Gender", "Female")

                    InfoRow("Height (cm/ft)", "150 Cm")

                    InfoRow("Weight (kg/lb)", "55 Kg", isLast = true)
                }
            }

            //  Spacer(Modifier.height(20.dp))

            // General Health
            item {
                SectionTitle("General Health")
                InfoCard {
                    InfoRow("Blood Group", "O+")

                    InfoRow("Known Allergies", "Nuts")

                    InfoRow("Emergency Contact", "--")

                    InfoRow("Emergency Ph.", "--", isLast = true)
                }
            }

            // Medical History
            item {
                SectionTitle("Medical History")
                InfoCard {
                    InfoRow("Chronic Conditions", "Hypertension")

                    InfoRow("Surgical History", "--")

                    Column {
                        InfoRow("Current Medications", "--")
                        repeat(4) {
                            InfoRow("", "--")
                        }
                    }

                    Column {
                        InfoRow("Current Supplements", "--")
                        repeat(3) {
                            InfoRow("", "--")

                        }
                    }
                }
            }

            // Documents
            item {
                SectionTitle("Documents")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 19.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D7F4))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth() .padding(horizontal = 5.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_files_icon),
                                contentDescription = "File Icon",
                                modifier = Modifier.size(41.dp,55.dp)
                            )
                            Text(
                                text = "Demo_1.Pdf",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF4338CA)
                            )
                        }
//                        IconButton(
//                            onClick = {},
//                            modifier = Modifier
//                                .size(48.dp)
//                                .background(Color(0xFFF8F8F8), CircleShape)
//                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_download_icon),
                                contentDescription = "Download",
                                modifier = Modifier.size(48.dp)
                            )
                     //   }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
*/

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun InfoCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(width = 1.dp, color = Color(0xFFEAE7FA), shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCFBFF))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = Color(0xFF4338CA),
            modifier = Modifier.weight(1f),
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 15.sp,
            color = Color(0xFF181818),
            modifier = Modifier.weight(1f),
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            fontWeight = FontWeight.Normal
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FamilyPersonProfileScreenPreview() {
    val navController = rememberNavController()
    FamilyPersonProfileScreen(navController = navController)
}*/