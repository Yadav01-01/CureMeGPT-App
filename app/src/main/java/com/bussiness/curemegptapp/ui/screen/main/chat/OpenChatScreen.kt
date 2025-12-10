package com.bussiness.curemegptapp.ui.screen.main.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.curemegptapp.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton

//OpenChatScreen


@Composable
fun OpenChatScreen() {
    var showMenu by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf("James (Myself)") }
    var showUserDropdown by remember { mutableStateOf(false) }
    var showCaseDialog by remember { mutableStateOf(false) }

    val users = listOf(
        "James (Myself)",
        "Rose Logan (Spouse)",
        "Peter Logan (Son)"
    )

    val suggestedQuestions = listOf(
        "Why do my teeth hurt when I drink something cold?",
        "What's the best way to treat bleeding gums at home?",
        "Do I need to remove my wisdom tooth if it hurts?",
        "Why do I keep getting frequent headaches?",
        "How can I tell if my stomach pain is serious?",
        "What should I do if I feel dizzy often?"
    )

    val fitnessQuestions = listOf(
        "Get fit question 1?",
        "Get fit question 2?",
        "Get fit question 3?"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopBar(onMenuClick = { showMenu = true })

            // Main Content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Logo
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .border(4.dp, Color(0xFF5B47DB), RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "*",
                            color = Color(0xFF5B47DB),
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Greeting
                    Row {
                        Text(
                            text = "Good afternoon, ",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "James",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF5B47DB)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // User Selector
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showUserDropdown = !showUserDropdown },
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF0EDFF)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_person_complete_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text("Ask for:", fontWeight = FontWeight.Medium)
                                Text(
                                    selectedUser,
                                    color = Color(0xFF5B47DB),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Image(

                                painter =  painterResource(if (showUserDropdown) R.drawable.ic_show_drop_down_icon
                                else R.drawable.ic_hide_drop_down_icon),
                                contentDescription = null,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // User Dropdown
                    if (showUserDropdown) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Column {
                                users.forEachIndexed { index, user ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                if (user == "Rose Logan (Spouse)") {
                                                    showCaseDialog = true
                                                } else {
                                                    selectedUser = user
                                                }
                                                showUserDropdown = false
                                            }
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            user,
                                            color = if (user == selectedUser) Color(0xFF5B47DB)
                                            else Color.Black,
                                            fontWeight = if (user == selectedUser) FontWeight.Medium
                                            else FontWeight.Normal
                                        )
                                        if (user == selectedUser) {
                                            Text("✓", fontSize = 20.sp, color = Color(0xFF5B47DB))
                                        }
                                    }
                                    if (index < users.size - 1) {
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // New Case Chat Button
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5B47DB)
                        )
                    ) {
                        Image(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("New Case Chat", fontSize = 16.sp)
                    }

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

            // Bottom Input Bar
            BottomInputBar()
        }

        // Menu Drawer
        if (showMenu) {
            MenuDrawer(
                onDismiss = { showMenu = false },
                selectedUser = selectedUser,
                onUserChange = { selectedUser = it }
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

@Composable
fun TopBar(onMenuClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
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
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF5B47DB), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "*",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "CureMeGPT",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = { }) {
                    Image(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = "Back")
                }
                IconButton(onClick = onMenuClick) {
                    Image(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = "Menu")
                }
            }
        }
    }
}

@Composable
fun QuestionCard(question: String, isHealthQuestion: Boolean) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
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
                    painter = painterResource(R.drawable.ic_show_drop_down_icon),
                    contentDescription = null,

                    modifier = Modifier.size(20.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFF5B47DB), RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("G", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Text(question, fontSize = 14.sp, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun BottomInputBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFF5F5F5)
            ) {
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = null, tint = Color.Gray)
                    Text("Ask anything", color = Color.Gray, fontSize = 14.sp)
                }
            }
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { },
                shape = CircleShape,
                color = Color(0xFF5B47DB)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(R.drawable.ic_show_drop_down_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MenuDrawer(onDismiss: () -> Unit, selectedUser: String, onUserChange: (String) -> Unit) {
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

    val caseHistory = listOf(
        "Case 1", "Case 2", "Case 3", "Case 4", "Case 5", "Medicine Advice + De..."
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(340.dp)
                .align(Alignment.CenterEnd)
                .clickable(enabled = false) { },
            color = Color.White
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
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(0xFF5B47DB), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("*", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                            Text("CureMeGPT", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = "Close")
                        }
                    }

                    // Search Bar
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF5F5F5)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = null, tint = Color.Gray)
                            Text("Search", color = Color.Gray, fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // New Chat Button
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B47DB))
                    ) {
                        Icon(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("New Chat")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // New Case Chat Button
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B47DB))
                    ) {
                        Icon(painter = painterResource(R.drawable.ic_show_drop_down_icon), contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("New Case Chat")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Chat History Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showChatHistory = !showChatHistory }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Chat History", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                        Icon(
                            if (showChatHistory) painter = painterResource(R.drawable.ic_show_drop_down_icon) else painter = painterResource(R.drawable.ic_show_drop_down_icon),
                            contentDescription = null
                        )
                    }

                    if (showChatHistory) {
                        // User Selector in Chat History
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(selectedUser, color = Color(0xFF5B47DB), fontWeight = FontWeight.Medium)
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
                            }
                        }
                    }
                }

                // Chat History Items
                if (showChatHistory) {
                    items(chatHistory.size) { index ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF0EDFF)
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
                                        Icon(
                                            Icons.Default.AccessTime,
                                            contentDescription = null,
                                            tint = Color(0xFF5B47DB),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(chatHistory[index], fontSize = 14.sp)
                                    }
                                    IconButton(
                                        onClick = { selectedChatIndex = if (selectedChatIndex == index) null else index },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.MoreVert,
                                            contentDescription = null,
                                            tint = Color(0xFF5B47DB)
                                        )
                                    }
                                }
                            }

                            // Options Menu
                            if (selectedChatIndex == index) {
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 60.dp)
                                        .width(180.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    shadowElevation = 8.dp
                                ) {
                                    Column {
                                        OptionMenuItem(text = "Rename", icon = "✏️")
                                        OptionMenuItem(text = "Share Chat", icon = "🔗")
                                        OptionMenuItem(text = "Delete", icon = "🗑️", isDelete = true)
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Case Chat History Button
                    Button(
                        onClick = { showCaseHistory = !showCaseHistory },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B47DB))
                    ) {
                        Text("Case Chat History")
                    }

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
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                // Case History Items
                if (showCaseHistory) {
                    items(caseHistory.size) { index ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF0EDFF)
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
                                    Icon(
                                        Icons.Default.AccessTime,
                                        contentDescription = null,
                                        tint = Color(0xFF5B47DB),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(caseHistory[index], fontSize = 14.sp)
                                }
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = null,
                                        tint = Color(0xFF5B47DB)
                                    )
                                }
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

@Composable
fun OptionMenuItem(text: String, icon: String, isDelete: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 16.sp)
        Text(
            text,
            fontSize = 14.sp,
            color = if (isDelete) Color.Red else Color.Black
        )
    }
}

@Composable
fun CaseDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFF5B47DB), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Description,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Column {
                            Text(
                                "Start a New Case Chat?",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "This new case chat will be created only for Rose Logan (Spouse). Once created, you cannot switch members in the middle. The full case history will be saved in Rose Logan (Spouse)'s records.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(2.dp, Color.LightGray)
                    ) {
                        Text("Cancel", color = Color.Black)
                    }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B47DB))
                    ) {
                        Text("Yes, Create Case Chat", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}