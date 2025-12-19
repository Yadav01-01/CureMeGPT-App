package com.bussiness.curemegptapp.ui.screen.auth.profileCompletion

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.curemegptapp.data.model.ProfileData
import com.bussiness.curemegptapp.ui.component.DisclaimerBox
import com.bussiness.curemegptapp.ui.component.FileAttachment
import com.bussiness.curemegptapp.ui.component.GradientButton
import com.bussiness.curemegptapp.ui.component.ProfilePhotoPicker
import com.bussiness.curemegptapp.ui.viewModel.auth.ProfileCompletionViewModel


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
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            ProfilePhotoPicker(
                label = "Upload Files (X-Rays, Dental Scans, Prescriptions, Lab Reports)",
                fileName = if (profileData.uploadedFiles.isEmpty()) "No file chosen"
                else "${profileData.uploadedFiles.size} files selected",
                onChooseClick = {
                    filePickerLauncher.launch(
                        arrayOf(
                            "image/*",
                            "application/pdf",
                            "application/dicom"
                        )
                    )
                }
            )

            Text(
                "PDF, JPG, PNG, DICOM Supported",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        filePickerLauncher.launch(
                            arrayOf(
                                "image/*",
                                "application/pdf",
                                "application/dicom"
                            )
                        )
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
        }
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