package com.bussiness.curemegptapp.ui.screen.main.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.ui.component.BottomMessageBar1
import com.bussiness.curemegptapp.ui.component.GradientRedButton
import com.bussiness.curemegptapp.ui.component.input.AIChatHeader
import com.bussiness.curemegptapp.ui.component.input.RightSideDrawer
import com.bussiness.curemegptapp.ui.dialog.CaseDialog
import com.bussiness.curemegptapp.ui.theme.gradientBrush
import com.bussiness.curemegptapp.ui.viewModel.main.ChatViewModel


@Composable
fun OpenChatScreen(navController: NavHostController) {
    var showMenu by remember { mutableStateOf(false) }
   // val uiState by viewModel.uiState.collectAsState()
    var selectedUser by remember { mutableStateOf("James (Myself)") }
    var showUserDropdown by remember { mutableStateOf(false) }
    var showCaseDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val users = listOf(
        "James (Myself)",
        "Rose Logan (Spouse)",
        "Peter Logan (Son)"
    )
    val chatViewModel: ChatViewModel = hiltViewModel()
    val chatInputState by chatViewModel.uiState.collectAsState()
    val messages by chatViewModel.messages.collectAsState() // ये add करें
    // Navigation trigger state
    var shouldNavigate by remember { mutableStateOf(false) }


    // Track when a new message is sent
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty() && shouldNavigate) {
            // Navigate to chat detail screen
            navController.navigate(AppDestination.ChatDataScreen) {
                popUpTo("openChatScreen") { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
            shouldNavigate = false
        }
    }



    // Define questions for each user
    val userQuestionsMap = mapOf(
        "James (Myself)" to Pair(
            // Suggested Questions for James
            listOf(
                "Why do my teeth hurt when I drink something cold?",
                "What's the best way to treat bleeding gums at home?",
                "Do I need to remove my wisdom tooth if it hurts?",
                "Why do I keep getting frequent headaches?",
                "How can I tell if my stomach pain is serious?",
                "What should I do if I feel dizzy often?"
            ),
            // Fitness Questions for James
            listOf(
                "Get fit question 1?",
                "Get fit question 2?",
                "Get fit question 3?"
            )
        ),
        "Rose Logan (Spouse)" to Pair(
            // Suggested Questions for Rose
            listOf(
                "What are common symptoms of migraines in women?",
                "How to manage back pain during pregnancy?",
                "Best exercises for postpartum recovery?",
                "Diet tips for improving skin health?",
                "How to improve sleep quality naturally?",
                "Managing stress and anxiety effectively?"
            ),
            // Fitness Questions for Rose
            listOf(
                "Postpartum fitness routine?",
                "Yoga for stress relief?",
                "Healthy meal planning?"
            )
        ),
        "Peter Logan (Son)" to Pair(
            // Suggested Questions for Peter
            listOf(
                "Common childhood allergies and symptoms?",
                "Best nutrition for growing children?",
                "How to boost immune system naturally?",
                "Managing childhood asthma?",
                "Healthy screen time limits for kids?",
                "When to consult doctor for fever in children?"
            ),
            // Fitness Questions for Peter
            listOf(
                "Fun exercises for kids?",
                "Building healthy habits early?",
                "Outdoor activities for children?"
            )
        )
    )

    // Get questions based on selected user
    val (suggestedQuestions, fitnessQuestions) = userQuestionsMap[selectedUser] ?: Pair(
        listOf("No questions available"),
        listOf("No fitness questions available")
    )


    var showDrawer by remember { mutableStateOf(false) }


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

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize().statusBarsPadding()
                        .padding(horizontal = 20.dp)
                ) {

                    AIChatHeader(
                        logoRes = R.drawable.ic_logo,
                        sideArrow = R.drawable.left_ic,
                        filterIcon = R.drawable.filter_ic,
                        onLeftIconClick = { navController.popBackStack() },
                        onFilterClick = { showDrawer = true },
                    )

                    // Main Content
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(32.dp))

                            // Logo
                            Image(
                                painter = painterResource(R.drawable.main_ic),
                                contentDescription = null,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Greeting
                            Row {
                                Text(
                                    text = "Good afternoon, ",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "James",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF4338CA)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // User Selector
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth().padding(horizontal = 18.dp)
                                    .clickable { showUserDropdown = !showUserDropdown },
                                shape = RoundedCornerShape(30.dp),
                                color = Color(0xFFF0EDFF),

                            ) {
                                Row(
                                    modifier = Modifier.padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_chat_circle_person_icon),
                                            contentDescription = null,
                                            modifier = Modifier.wrapContentSize()
                                        )
                                        Text("Ask for:", fontWeight = FontWeight.Medium, fontSize = 14.sp,)

                                    }

                                    Text(
                                        selectedUser,
                                        color = Color(0xFF5B47DB),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Image(

                                        painter = painterResource(
                                            if (showUserDropdown) R.drawable.ic_dropdown_show
                                            else R.drawable.ic_dropdown_icon
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 6.dp)
                                    )
                                }
                            }


                            if (showUserDropdown) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    ) {
                                        users.forEachIndexed { index, user ->

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(44.dp)
                                                    .clickable(
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        indication = null
                                                    ) {

                                                        selectedUser = user
                                                        showUserDropdown = false
                                                    }
                                                    .padding(horizontal = 16.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxSize(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    // User name with proper styling
                                                    Text(
                                                        text = user,
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                                                        fontWeight = if (user == selectedUser) FontWeight.Medium else FontWeight.Normal,
                                                        color = if (user == selectedUser) Color(0xFF4338CA) else Color(0xFF374151)
                                                    )

                                                    // Tick icon only for selected user
                                                    if (user == selectedUser) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.ic_tick_icon),
                                                            contentDescription = "Selected",
                                                            modifier = Modifier.size(20.dp)
                                                        )
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            GradientRedButton(
                                text = "New Case Chat",
                                icon = R.drawable.page_img,
                                modifier = Modifier.fillMaxWidth(),
                                height = 56.dp,
                                fontSize = 14.sp,
                                imageSize = 16.dp,
                                gradientColors = listOf(
                                    Color(0xFF4338CA),
                                    Color(0xFF211C64)
                                ),
                                onClick = {  showCaseDialog = true }
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // Suggested Questions
                        items(suggestedQuestions) { question ->
                            QuestionCard(question = question, isHealthQuestion = true)
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // Fitness Questions
                        items(fitnessQuestions) { question ->
                            QuestionCard(question = question, isHealthQuestion = false)
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }


                    BottomMessageBar1(
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 5.dp).padding(bottom = 10.dp),
                        state = chatInputState,
                        viewModel = chatViewModel,
                        onSendClicked = {
                            // When send button is clicked, trigger navigation
                            shouldNavigate = true
                        }
                    )
                }



                // Case Dialog
                if (showCaseDialog) {
                    CaseDialog(
                        onDismiss = { showCaseDialog = false },
                        onConfirm = {
                            selectedUser = "Rose Logan (Spouse)"
                            showCaseDialog = false
                        }
                    )
                }
            }
        }

}


@Composable
fun QuestionCard(question: String, isHealthQuestion: Boolean) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            if (isHealthQuestion) {
                Image(
                    painter = painterResource(R.drawable.ic_circle_page_image),
                    contentDescription = null,

                    modifier = Modifier.size(51.dp).align(alignment = Alignment.CenterVertically)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_g_icon),
                    contentDescription = null,

                    modifier = Modifier.size(51.dp).align(alignment = Alignment.CenterVertically)
                )
            }
            Text(question, fontSize = 16.sp, modifier = Modifier.weight(1f).align(alignment = Alignment.CenterVertically), fontFamily = FontFamily(Font(R.font.urbanist_regular)))
        }
    }
}


@Composable
fun MenuDrawer(onDismiss: () -> Unit, selectedUser: String, onUserChange: (String) -> Unit,onClickNewCaseChat:()-> Unit) {
    var showChatHistory by remember { mutableStateOf(false) }
    var showCaseHistory by remember { mutableStateOf(false) }
    var selectedChatIndex by remember { mutableStateOf<Int?>(null) }

    val chatHistory = listOf(
        "Toothache & Fever Co...",
        "Cough, Cold & Oral He...",
        "Medicine",
        "Cough, C...",
        "General Check-up & H...",
        "Medicine Advice + De..."
    )

//    val caseHistory = listOf(
//        "Case 1", "Case 2", "Case 3", "Case 4", "Case 5", "Medicine Advice + De..."
//    )
    // Define case history for each user
    val userCaseHistoryMap = mapOf(
        "James (Myself)" to listOf(
            "Toothache Treatment - Nov 2023",
            "Annual Checkup - Aug 2023",
            "Headache Consultation - Jun 2023",
            "Stomach Issue - Mar 2023",
            "Dental Cleaning - Jan 2023",
            "General Health Review"
        ),
        "Rose Logan (Spouse)" to listOf(
            "Pregnancy Checkup - Dec 2023",
            "Migraine Treatment - Oct 2023",
            "Postpartum Recovery - Sep 2023",
            "Skin Consultation - Jul 2023",
            "Sleep Disorder - May 2023",
            "Stress Management"
        ),
        "Peter Logan (Son)" to listOf(
            "Childhood Allergy Test - Nov 2023",
            "Growth Monitoring - Sep 2023",
            "Immunization Update - Aug 2023",
            "Asthma Management - Jun 2023",
            "Pediatric Checkup - Apr 2023",
            "Nutrition Counseling"
        )
    )

    // Get case history based on selected user
    val caseHistory = userCaseHistoryMap[selectedUser] ?: listOf("No case history available")

    var searchQuery by remember { mutableStateOf("") }
    var showUserDropdown1 by remember { mutableStateOf(false) }
    val users = listOf(
        "James (Myself)",
        "Rose Logan (Spouse)",
        "Peter Logan (Son)"
    )
    Box(
        modifier = Modifier
            .fillMaxSize().clip(shape = RoundedCornerShape(topStart = 40.dp,bottomStart = 40.dp))
            .background(Color.White)
            .clickable { onDismiss() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight().padding(vertical = 10.dp)
                .width(340.dp)
                .align(Alignment.CenterEnd)
                .clickable(enabled = false) { },
            color = Color.White,
            shape = RoundedCornerShape(topStart = 40.dp,bottomStart = 40.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = "Logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .height(30.dp) // Adjust size according to your logo
                            )
                        }

                    }


                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(40.dp),
                        color = Color(0xFFF4F4F4),
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search",
                                    color = Color(0xFFBCBCBC),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.urbanist_regular))
                                )
                            },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_search_icon),
                                    contentDescription = "Search",
                                    modifier = Modifier.size(18.dp)
                                )
                            },

                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF4F4F4),
                                unfocusedContainerColor = Color(0xFFF4F4F4),
                                disabledContainerColor = Color(0xFFF4F4F4),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))



                    GradientRedButton(
                        text = "New Chat",
                        icon = R.drawable.ic_add_chat_icon,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        height = 56.dp,
                        fontSize = 16.sp,
                        imageSize = 24.dp,
                        gradientColors = listOf(
                            Color(0xFF4338CA),
                            Color(0xFF211C64)
                        ),
                        onClick = { /* Your action */ }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GradientRedButton(
                        text = "New Case Chat",
                        icon = R.drawable.page_img,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        height = 56.dp,
                        fontSize = 16.sp,
                        imageSize = 24.dp,
                        gradientColors = listOf(
                            Color(0xFF4338CA),
                            Color(0xFF211C64)
                        ),
                        onClick = { onClickNewCaseChat()  }
                    )

                    Spacer(modifier = Modifier.height(12.dp))



                    Spacer(modifier = Modifier.height(24.dp))

                    // Chat History Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showChatHistory = !showChatHistory }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Chat History", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(if (showChatHistory) R.drawable.ic_dropdown_show else R.drawable.ic_dropdown_icon),
                            contentDescription = null
                        )
                    }

                    if (showChatHistory) {
                        // User Selector in Chat History

                        Box(

                            modifier = Modifier
                                .wrapContentHeight().fillMaxWidth()
                                .padding(horizontal = 16.dp).clip(RoundedCornerShape(24.dp)).background(gradientBrush).clickable(){

                                }.padding(start = 10.dp),

                        ) {
                            Text("Case Chat History",color = Color.White, fontWeight = FontWeight.Medium)
                        }

                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth().clickable(){
                                showUserDropdown1 = true
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(30.dp),
                        border = BorderStroke(1.dp, Color(0xFFE7E9EC)),
                        color = Color.Unspecified
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(selectedUser, color = Color(0xFF4338CA), fontWeight = FontWeight.Medium)
                            Image(painter = painterResource( if(showUserDropdown1) R.drawable.ic_dropdown_show else R.drawable.ic_dropdown_icon), contentDescription = null)
                        }
                    }

                    if (showUserDropdown1) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth().padding(horizontal = 5.dp)
                                .wrapContentHeight(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                users.forEachIndexed { index, user ->

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(44.dp)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null
                                            ) {

                                                onUserChange(user)
                                                showUserDropdown1 = false
                                            }
                                            .padding(horizontal = 16.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // User name with proper styling
                                            Text(
                                                text = user,
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                                                fontWeight = if (user == selectedUser) FontWeight.Medium else FontWeight.Normal,
                                                color = if (user == selectedUser) Color(0xFF4338CA) else Color(0xFF374151)
                                            )

                                            // Tick icon only for selected user
                                            if (user == selectedUser) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_tick_icon),
                                                    contentDescription = "Selected",
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }



                }



                // Chat History Items
                if (showChatHistory) {
                    item {

                        Spacer(modifier = Modifier.height(16.dp))

                        // Case Chat History Button


                        if (showCaseHistory) {
                            Spacer(modifier = Modifier.height(12.dp))

                            // User Selector in Case History
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color.LightGray)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(selectedUser, color = Color(0xFF5B47DB), fontWeight = FontWeight.Medium)
                                    Icon(painter = painterResource( R.drawable.ic_show_drop_down_icon), contentDescription = null, tint = Color.Gray)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                }




                    items(caseHistory.size) { index ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(30.dp),
                            color = Color(0xFFF5F0FF)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { }
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Image(
                                        painter = painterResource( R.drawable.ic_clock_new_icon),
                                        contentDescription = null,

                                        modifier = Modifier.size(31.dp)
                                    )
                                    Text(caseHistory[index], fontSize = 14.sp)
                                }
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(26.dp)
                                ) {
                                    Icon(
                                        painter = painterResource( R.drawable.ic_horizontal_menu_icon),
                                        contentDescription = null,
                                        tint = Color.Unspecified,

                                    )
                                }
                            }
                        }
                    }


                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun OpenChatScreenPreview() {
    val navController = rememberNavController()
    OpenChatScreen(navController = navController)
}

