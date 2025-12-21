package com.bussiness.curemegptapp.ui.screen.main.chat

//ChatDataScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.BottomMessageBar2
import com.bussiness.curemegptapp.ui.component.input.ChatHeader
import com.bussiness.curemegptapp.ui.component.input.CommunityChatSection
import com.bussiness.curemegptapp.ui.viewModel.main.ChatDataViewModel
// Dono imports important hain:
import androidx.constraintlayout.compose.*  // ConstraintLayout, createRefs, etc.
import androidx.constraintlayout.compose.Dimension  // Dimension class ke liye
import com.bussiness.curemegptapp.ui.component.input.RightSideDrawer
import com.bussiness.curemegptapp.ui.dialog.DeleteChatDialog
import com.bussiness.curemegptapp.ui.dialog.SwitchToDialog

@Composable
fun ChatDataScreen(navController: NavHostController) {
    val viewModel: ChatDataViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val listState = rememberLazyListState()
    var showSwitchDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showDrawer by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf("James (Myself)") }
    var showCaseDialog by remember { mutableStateOf(false) }

    RightSideDrawer(
        drawerState = showDrawer,
        onClose = { showDrawer = false },
        drawerWidth = 320.dp,
        drawerContent = {
            MenuDrawer(
                onDismiss = { showDrawer = false },
                selectedUser = selectedUser,
                onUserChange = {
                    selectedUser = it
                    showDrawer = false
                },
                onClickNewCaseChat = {
                    showCaseDialog = true
                }
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {

            // Background Image
            Image(
                painter = painterResource(id = R.drawable.chat_background),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                ChatHeader(
                    logoRes = R.drawable.ic_logo,
                    sideArrow = R.drawable.ic_cross_icon,
                    filterIcon = R.drawable.ic_filter_menu_icon3,
                    menuIcon = R.drawable.ic_menu_icon3,
                    onLeftIconClick = { navController.popBackStack() },
                    onFilterClick = {

                        showDrawer = true
                    },
                    //onMenuClick = {}
                    menuContent = {
                        SwitchShareDeletePopUpMenu(
                            switchText = "Switch to Case",
                            onSwitchClick = {
                                showSwitchDialog = true
                            },
                            onShareClick = {
                                // Share logic
                                val shareText = """
            Hey 👋
            
            This is a dummy chat message.
            Just testing share feature.
            
            Shared from CureMeGPT App 🚀
        """.trimIndent()

                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }

                                context.startActivity(
                                    Intent.createChooser(intent, "Share chat via")
                                )
                            },
                            onDeleteClick = {
                                showDeleteDialog = true
                            }
                        )
                    }
                )

                /** CHAT BODY + MESSAGE BAR */
                ConstraintLayout(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    // Create references
                    val (chatSection, messageBar) = createRefs()


                    CommunityChatSection(
                        messages = messages,
                        listState = listState,
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .constrainAs(chatSection) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(messageBar.top)
                                height = Dimension.fillToConstraints
                            }
                    )

                    BottomMessageBar2(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(messageBar) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(bottom = 10.dp),
                        state = uiState,
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    if (showSwitchDialog) {
        SwitchToDialog(
            title = "Switch to Case Chat?",
            description = "This chat will be converted into a case chat. Your conversation will be tracked with case history and medical records. Do you want to continue?",
            buttonText = "Stay on Normal Chat",
            onDismiss = {
                showSwitchDialog = false
            },
            onConfirm = {
                showSwitchDialog = false
            }
        )
    }
    if (showDeleteDialog) {
        DeleteChatDialog(
            title = "Delete Chat?",
            message = "Once deleted, this chat and its medical history cannot be recovered.",
            warningText = "Deleting may affect AI’s ability to suggest based on your past health history.",
            bottomText = "You’re always in control — deleted chats cannot be restored.",
            cancelText = "Cancel",
            confirmText = "Yes, Delete Chat",
            onDismiss = { showDeleteDialog = false},
            onConfirm = { showDeleteDialog = false}
        )
    }

}


@Preview(showBackground = true)
@Composable
fun ChatDataScreenPreview() {
    val navController = rememberNavController()
    ChatDataScreen(navController = navController)
}

