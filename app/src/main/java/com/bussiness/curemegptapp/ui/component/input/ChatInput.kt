package com.bussiness.curemegptapp.ui.component.input

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.data.model.ChatMessage
import com.bussiness.curemegptapp.ui.theme.AppGradientColors
import com.bussiness.curemegptapp.viewmodel.ChatViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun ChatHeader(
    logoRes: Int,
    sideArrow: Int,
    filterIcon: Int,
    menuIcon: Int,
    modifier: Modifier = Modifier,
    onLeftIconClick: () -> Unit,
    onFilterClick: () -> Unit,
    onMenuClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White).padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .wrapContentWidth()
                .height(30.dp) // Adjust size according to your logo
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Notification Icon
            IconButton(onClick = { onLeftIconClick() }) {
                Icon(
                    painter = painterResource(sideArrow),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .width(12.dp)
                        .height(24.dp),
                    tint = Color.Unspecified
                )
            }


            // Profile Image
            Row(
                modifier = modifier
                    .width(74.dp)
                    .height(52.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ---- Profile Circular Image ----
                IconButton(onClick = { onFilterClick() }) {
                    Icon(
                        painter = painterResource(filterIcon),
                        contentDescription = "filter",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // ---- Arrow Icon ----
                IconButton(onClick = { onMenuClick() }) {
                    Icon(
                        painter = painterResource(menuIcon),
                        contentDescription = "menu",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }

        }
    }
}

@Composable
fun BottomMessageBar(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel
) {
    var showAttachmentMenu by remember { mutableStateOf(false) }

    val message by viewModel.currentMessage.collectAsState()
    val images by viewModel.selectedImages.collectAsState()
    val pdfFiles by viewModel.selectedPdfs.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> if (uri != null) viewModel.onImageSelected(uri) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> if (uri != null) viewModel.onPdfSelected(uri) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {

        // --------------------
        //  IMAGE + PDF PREVIEWS
        // --------------------
        if (images.isNotEmpty() || pdfFiles.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {

                // IMAGE PREVIEW CHIP LIST
                images.forEach { uri ->
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F0FF))
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            painter = painterResource(R.drawable.remove_ic),
                            contentDescription = "Remove",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(22.dp)
                                .background(Color(0x80000000), CircleShape)
                                .align(Alignment.TopEnd)
                                .clickable { viewModel.removeImage(uri) }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // PDF PREVIEW CHIP
                pdfFiles.forEach { pdf ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF5F0FF))
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.pdf_ic),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = pdf.name,
                            fontSize = 13.sp,
                            maxLines = 1
                        )
                        Spacer(Modifier.width(6.dp))
                        Icon(
                            painter = painterResource(R.drawable.remove_ic),
                            contentDescription = "Remove",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { viewModel.removePdf(pdf) }
                        )
                    }
                }
            }
        }

        // --------------------
        //  MAIN INPUT BAR
        // --------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // INPUT BUBBLE
            Row(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 54.dp, max = 150.dp)
                    .background(Color(0xFFF5F0FF), RoundedCornerShape(40.dp))
                    .padding(start = 4.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ATTACH ICON
                Box {
                    IconButton(onClick = { showAttachmentMenu = true }) {
                        Icon(
                            painter = painterResource(R.drawable.attach_ic),
                            contentDescription = "Attach",
                            tint = Color.Unspecified
                        )
                    }

                    DropdownMenu(
                        expanded = showAttachmentMenu,
                        onDismissRequest = { showAttachmentMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Image") },
                            onClick = {
                                showAttachmentMenu = false
                                imagePickerLauncher.launch("image/*")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("PDF") },
                            onClick = {
                                showAttachmentMenu = false
                                pdfPickerLauncher.launch("application/pdf")
                            }
                        )
                    }
                }

                Spacer(Modifier.width(4.dp))

                // AUTO EXPANDING TEXTFIELD
                TextField(
                    value = message,
                    onValueChange = { viewModel.onMessageChange(it) },
                    placeholder = { Text("Ask anything…") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF4338CA),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    maxLines = 4,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.width(10.dp))

            // --------------------
            //  MIC / SEND button (Animated)
            // --------------------
            val hasText = message.trim().isNotEmpty() ||
                    images.isNotEmpty() || pdfFiles.isNotEmpty()

            val scale by animateFloatAsState(if (hasText) 1f else 0f)

            IconButton(
                modifier = Modifier
                    .size(52.dp),
                onClick = {  if (hasText) viewModel.sendMessage()
                else viewModel.startVoiceInput()}
            ) {
                if (hasText) {
                    Icon(
                        painter = painterResource(R.drawable.send_ic),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.voiceinc_ic),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }
    }
}



@Composable
fun CommunityChatSection(
    messages: List<ChatMessage>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        var lastDate: String? = null

        items(messages) { message ->
            val currentDate = formatDate(message.timestamp)

            // Show date separator when new date starts
//            if (currentDate != lastDate) {
//                DateSeparator(dateText = currentDate)
//                lastDate = currentDate
//            }

            ChatBubble(message)
        }
    }
}



@Composable
fun ChatBubble(message: ChatMessage) {

    val bubbleColor = if (message.isUser) Color(0xFF4338CA) else Color(0xFFF8F8F8)
    val alignment = if (message.isUser) Arrangement.End else Arrangement.Start

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = alignment,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {

            // Bot profile image
            if (!message.isUser) {
                Image(
                    painter = painterResource(id = R.drawable.ap_button),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Chat bubble
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
            ) {

                // Chat bubble
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(bubbleColor)
                        .widthIn(
                            max = if (message.isUser)
                                LocalConfiguration.current.screenWidthDp.dp * 0.8f
                            else
                                LocalConfiguration.current.screenWidthDp.dp * 0.7f
                        )
                ) {
                    Text(
                        text = message.text,
                        color = if (message.isUser) Color.White else Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                // Bot reaction bar (left side)
                if (!message.isUser) {
                    ReactionBar(
                        onCopy = { /* TODO */ },
                        onLike = { /* TODO */ },
                        onDislike = { /* TODO */ }
                    )
                }

                // User action bar (right side)
                else {
                    UserActionBar(
                        onCopy = { /* TODO */ },
                        onEdit = { /* TODO */ }
                    )
                }
            }


        }
        }
}



fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatDate(timestamp: Long): String {
    val cal = Calendar.getInstance()
    val today = Calendar.getInstance()

    cal.timeInMillis = timestamp

    return when {
        cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> "Today"

        cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) - 1 -> "Yesterday"

        else -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}

@Composable
fun DateSeparator(dateText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFDDE5E8), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = dateText,
                fontSize = 12.sp,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun ReactionBar(
    onCopy: () -> Unit = {},
    onLike: () -> Unit = {},
    onDislike: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 4.dp, top = 6.dp)
    ) {
        ReactionIcon(R.drawable.copy_ic, "Copy", onCopy)
        ReactionIcon(R.drawable.like_ic, "Like", onLike)
        ReactionIcon(R.drawable.dislike_ic, "Dislike", onDislike)
    }
}

@Composable
fun UserActionBar(
    onCopy: () -> Unit = {},
    onEdit: () -> Unit = {},
){
    Box {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp, top = 6.dp)
        ) {
            ReactionIcon(R.drawable.copy_ic, "Copy", onCopy)
            ReactionIcon(R.drawable.chat_edit_ic, "Like", onEdit)
        }
    }
}

@Composable
fun ReactionIcon(
    @DrawableRes icon: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(30.dp)
            .clickable { onClick() },
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
        }

    }
}



@Composable
fun AIChatHeader(
    logoRes: Int,
    sideArrow: Int,
    filterIcon: Int,
    modifier: Modifier = Modifier,
    onLeftIconClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .wrapContentWidth()
                .height(30.dp) // Adjust size according to your logo
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Notification Icon
            IconButton(onClick = { onLeftIconClick() }) {
                Icon(
                    painter = painterResource(sideArrow),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .width(12.dp)
                        .height(24.dp),
                    tint = Color.Unspecified
                )
            }


            // Profile Image
            IconButton(onClick = { onFilterClick() }) {
                Icon(
                    painter = painterResource(filterIcon),
                    contentDescription = "filter",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}


@Composable
fun NewCaseContent(
    userName: String,
    selectedProfile: String,
    profileList: List<String>,
    questions: List<String>,
    onProfileChange: (String) -> Unit,
    onNewCaseClick: () -> Unit,
    onQuestionClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Top Icon
        Image(
            painter = painterResource(R.drawable.main_ic),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(24.dp))

        // Greeting
        val gradient = Brush.linearGradient(
            colors = AppGradientColors
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = buildAnnotatedString {
                append("Good afternoon, ")

                withStyle(
                    style = SpanStyle(
                        brush = gradient,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(userName)
                }
            },
            fontSize = 26.sp,
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(Modifier.height(8.dp))

        // Profile Selector
        Row( modifier = Modifier
            .height(39.dp)
            .clip(RoundedCornerShape(90.dp))
            .background(Color(0xFFF5F0FF))
            .padding(horizontal = 14.dp, vertical = 0.dp)
            .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(R.drawable.mage_users),
                    contentDescription = null,
                    modifier = Modifier.wrapContentSize()
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Ask for:",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(Modifier.width(6.dp))

            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = selectedProfile,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4B4BFF)
                    )
                    Icon(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // New Case Chat Button
        Box(
            modifier = Modifier
                .width(291.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(45.dp))
                .background(Brush.linearGradient(AppGradientColors))
                .clickable { onNewCaseClick() }
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(R.drawable.page_img),
                    contentDescription = null,
                    modifier = Modifier.wrapContentSize()
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "New Case Chat",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }



        Spacer(Modifier.height(20.dp))

        // Scrollable Question List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(questions) { question ->
                QuestionItem(
                    question = question,
                    onClick = { onQuestionClick(question) }
                )
            }
        }
    }
}

@Composable
fun QuestionItem(
    question: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.list_ic),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = question,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
            modifier = Modifier.weight(1f)
        )
    }
}
