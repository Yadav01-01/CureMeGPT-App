package com.bussiness.curemegptapp.ui.screen.auth.profileCompletion


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.TopBarHeader
import com.bussiness.curemegptapp.ui.dialog.AlertCardDialog
import com.bussiness.curemegptapp.ui.dialog.CompleteProfileDialog
import com.bussiness.curemegptapp.ui.viewModel.auth.ProfileCompletionViewModel

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
            .imePadding()
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
            onDismiss = { showAlertDialog = false
                navController.navigate("addFamilyMember?from=auth")
                        },
            onConfirm = {  showAlertDialog = false

                navController.navigate("openChat?from=auth")
                showAlertDialog2 = true
            }
        )
    }
//    if (showAlertDialog2) {
//        AlertCardDialog(
//            icon =  R.drawable.ic_medication,
//            title = "Medication Reminder",
//            message = "Time to take your",
//            highlightText = "Lisinopril 10mg.",
//            warningText = "Don't forget to take it with food",
//            cancelText = "Snooze",
//            confirmText = "Mark As Taken",
//            onDismiss = { showAlertDialog2 = false },
//            onConfirm = { showAlertDialog2 = false
//                showAlertDialog3 = true  }
//        )
//
//    }
//
//    if (showAlertDialog3) {
//        AlertCardDialog(
//            icon =  R.drawable.ic_appointment,
//            title = "Dental Cleaning Today",
//            message = "Don't forget your dental cleaning appointment ",
//            highlightText = "today at 2:00 PM with Dr. Sarah Johnson.",
//            cancelText = "Remind Me Later",
//            confirmText = "Got It",
//            onDismiss = { showAlertDialog3 = false },
//            onConfirm = { showAlertDialog3 = false
//                showCompleteDialog = true}
//        )
//
//        if (showCompleteDialog) {
//            CompleteProfileDialog(
//                icon = R.drawable.ic_person_complete_icon,
//                title = "Complete Your Profile",
//                checklist = listOf(
//                    "Faster AI answers tailored to you",
//                    "Safer medication & allergy checks",
//                    "Quicker reminders & records Complete Now"
//                ),
//                cancelText = "Remind Me Later",
//                confirmText = "Complete Now",
//                skipText = "Skip for Now",
//
//                onDismiss = {
//                    showCompleteDialog = false
//                },
//                onConfirm = {
//                    showCompleteDialog = false
//                   navController.navigate(AppDestination.MainScreen)
//                },
//                onSkip = {
//                    showCompleteDialog = false
//                    // TODO – "Skip for Now"
//                }
//            )
//        }
//    }
}







@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_6,
    showSystemUi = true
)
@Composable
fun ProfileCompletionScreenPreview() {
    val navController = rememberNavController()
    ProfileCompletionScreen(navController = navController)
}