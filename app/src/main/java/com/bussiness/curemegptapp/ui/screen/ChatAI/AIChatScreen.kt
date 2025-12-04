package com.bussiness.curemegptapp.ui.screen.ChatAI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.input.AIChatHeader
import com.bussiness.curemegptapp.ui.component.input.BottomMessageBar
import com.bussiness.curemegptapp.ui.component.input.NewCaseContent
import com.bussiness.curemegptapp.viewmodel.ChatViewModel

@Composable
fun AIChatScreen(navController: NavHostController) {
    val viewModel: ChatViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AIChatHeader(
            logoRes = R.drawable.ic_logo,
            sideArrow = R.drawable.left_ic,
            filterIcon = R.drawable.filter_ic,
            onLeftIconClick = { navController.popBackStack() },
            onFilterClick = {},
        )

        Spacer(Modifier.height(10.dp))

        NewCaseContent(
            userName = "James",
            selectedProfile = "James (Myself)",
            profileList = listOf("Family Member", "Friend", "Other"),
            questions = listOf(
                "Why do my teeth hurt when I drink something cold?",
                "What’s the best way to treat bleeding gums at home?",
                "Do I need to remove my wisdom tooth if it hurts?",
                "Why do I keep getting frequent headaches?",
                "How can I tell if my stomach pain is serious?",
                "What should I do if I feel dizzy often?"
            ),
            onProfileChange = {},
            onNewCaseClick = {},
            onQuestionClick = {}
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        BottomMessageBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            viewModel = viewModel
        )
    }

}