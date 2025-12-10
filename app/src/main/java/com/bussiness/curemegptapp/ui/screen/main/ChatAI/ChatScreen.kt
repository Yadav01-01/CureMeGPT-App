package com.bussiness.curemegptapp.ui.screen.main.ChatAI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.bussiness.curemegptapp.ui.component.BottomMessageBar
import com.bussiness.curemegptapp.ui.component.input.ChatHeader
import com.bussiness.curemegptapp.ui.component.input.CommunityChatSection
import com.bussiness.curemegptapp.ui.viewModel.main.ChatViewModel

@Composable
fun ChatScreen(navController: NavHostController) {

    val viewModel: ChatViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val listState = rememberLazyListState()

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
                .imePadding()
        ) {
            CommunityChatSection(
                messages = messages,
                listState = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 90.dp)
            )


            BottomMessageBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 10.dp),
                state = uiState,
                viewModel = viewModel
            )
        }
    }
}
