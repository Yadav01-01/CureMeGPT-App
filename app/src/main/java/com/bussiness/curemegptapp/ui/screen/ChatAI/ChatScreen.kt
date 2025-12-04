package com.bussiness.curemegptapp.ui.screen.ChatAI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.input.BottomMessageBar
import com.bussiness.curemegptapp.ui.component.input.ChatHeader
import com.bussiness.curemegptapp.ui.component.input.CommunityChatSection
import com.bussiness.curemegptapp.viewmodel.ChatViewModel

@Composable
fun ChatScreen(navController: NavHostController) {

    val viewModel: ChatViewModel = hiltViewModel()
    val listState = rememberLazyListState()
    val messages by viewModel.messages.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /** HEADER */
        ChatHeader(
            logoRes = R.drawable.ic_logo,
            sideArrow = R.drawable.left_ic,
            filterIcon = R.drawable.filter_ic,
            menuIcon = R.drawable.menu_ic,
            onLeftIconClick = { navController.popBackStack() },
            onFilterClick = {},
            onMenuClick = {}
        )

        /** CHAT BODY + MESSAGE BAR */
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .imePadding()  // keyboard push up
        ) {

            // TODO: Replace with your messages list
             CommunityChatSection(
                messages = messages,
                listState = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 65.dp)
            )

            /** MESSAGE INPUT BAR */
            BottomMessageBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                viewModel = viewModel
            )
        }
    }
}
