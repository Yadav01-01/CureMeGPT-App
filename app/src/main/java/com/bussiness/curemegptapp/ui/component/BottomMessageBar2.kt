package com.bussiness.curemegptapp.ui.component

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.viewModel.main.ChatDataViewModel
import com.bussiness.curemegptapp.ui.viewModel.main.ChatInputState
import com.bussiness.curemegptapp.ui.viewModel.main.ChatInputState1
import com.bussiness.curemegptapp.util.SpeechRecognizerManager
import timber.log.Timber

@Composable
fun BottomMessageBar2(
    modifier: Modifier = Modifier,
    state: ChatInputState1 = ChatInputState1(),
    viewModel: ChatDataViewModel = hiltViewModel(),
    onSendClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val profiles by viewModel.profiles.collectAsState()

    // Pickers
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.addImage(it) }
    }
    val pdfLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.addPdf(it) }
    }
    val users = listOf(
        "James (Myself)",
        "Rose Logan (Spouse)",
        "Peter Logan (Son)"
    )
    var selectedUser by remember { mutableStateOf("James (Myself)") }

    // Permission launcher for RECORD_AUDIO
    val recordPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            // nothing here; clicking mic will handle starting
        }
    }



    // Speech recognizer manager
    var speechMgr by remember { mutableStateOf<SpeechRecognizerManager?>(null) }
    var showUserDropdown by remember { mutableStateOf(false) }

    DisposableEffect(key1 = context) {
        onDispose {
            speechMgr?.stopListening()
            speechMgr = null
        }
    }

    val startVoiceRecording = { mgr: SpeechRecognizerManager?, vm: ChatDataViewModel ->
        mgr?.stopListening() // पहले से चल रहा हो तो बंद करें

        val newManager = SpeechRecognizerManager(
            context = context,
            onPartialResult = { partialText ->
                vm.onPartialVoiceResult(partialText)
            },
            onFinalResult = { finalText ->
                vm.onVoiceRecorded(finalText)
            },
            onError = { error ->
                // Handle error
                Timber.e("Speech recognition error: $error")
                vm.stopRecording()
            }
        )

        speechMgr = newManager
        vm.toggleRecording()
        newManager.startListening()
    }

    // Voice recording stop function
    val stopVoiceRecording = { mgr: SpeechRecognizerManager?, vm: ChatDataViewModel ->
        mgr?.stopListening()
        vm.stopRecording()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
//            .paint(
//                painter = painterResource(id = R.drawable.ic_curve_white_shape),
//                contentScale = ContentScale.FillWidth
//            )
            .padding(horizontal = 5.dp).padding(bottom = 8.dp)
    ) {

        Surface(
            modifier = Modifier
                .wrapContentWidth()

                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            color = Color.White,
            shadowElevation = if (showUserDropdown) 2.dp else 0.dp
        ) {
                //  }

                Surface(
                    modifier = Modifier
                        .wrapContentWidth().padding(horizontal = 10.dp).padding(top = 6.dp)
                        .clickable { showUserDropdown = !showUserDropdown },
                    shape = RoundedCornerShape(30.dp),
                    color = Color(0xFFF0EDFF),

                    ) {
                    Row(
                        modifier = Modifier.padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Image(
                            painter = painterResource(R.drawable.ic_chat_circle_person_icon),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )

                        Spacer(Modifier.width(4.dp))

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
            }


        if (showUserDropdown) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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


        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rounded text box
            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .background(Color(0xFFF5F0FF), RoundedCornerShape(28.dp))
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.attach_ic),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .wrapContentSize().align(alignment = Alignment.Bottom).padding(start = 13.dp, bottom = 13.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { imageLauncher.launch("image/*") }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    if (state.images.isNotEmpty() || state.pdfs.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        InlineAttachmentPreview(
                            images = state.images,
                            pdfs = state.pdfs,
                            onRemoveImage = viewModel::removeImage,
                            onRemovePdf = viewModel::removePdf
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

//                    if (state.showVoicePreview) {
////                        VoicePreviewCard1(
////                            transcription = state.transcribedText,
////                            onSeeText = { viewModel.showTranscribedText() },
////                            onClose = { viewModel.clearVoicePreview() }
////                        )
//                        VoicePreviewCard1(
//                            transcription = state.transcribedText,
//                            onSeeText = {
//                                // Text को message field में सेट करें
//                                viewModel.onMessageChange(state.transcribedText)
//                                viewModel.showTranscribedText()
//                            },
//                            onClose = {
//                                viewModel.clearVoicePreview()
//                            }
//                        )
//                        /*
//                         VoicePreviewCard(
//                        transcription = state.transcribedText,
//                        onSeeText = { viewModel.showTranscribedText() },
//                        onClose = { viewModel.clearVoicePreview() }
//                    )
//                         */
//                        Spacer(Modifier.height(8.dp))
//                    }
//                    else if (state.isRecording) {
//                        // Show recording indicator
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 12.dp),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            VoiceWaveform(
//                                isRecording = true,
//                                color = Color(0xFF6B4EFF)
//                            )
//                        }
//                    }
//                    else {
//                        TextField(
//                            value = state.message,
//                            onValueChange = { viewModel.onMessageChange(it) },
//                            modifier = Modifier.fillMaxWidth(),
//                            //fontFamily = FontFamily(Font(R.font.urbanist_regular)),
//                            textStyle = TextStyle(
//                                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
//                                fontSize = 13.sp,
//                                color = Color.Black
//                            ),
//                            placeholder = { Text("Ask anything…", fontSize = 12.sp, color = Color(0xFF949494), fontFamily = FontFamily(Font(R.font.urbanist_regular))) },
//                            maxLines = 4,
//                            colors = TextFieldDefaults.colors(
//                                focusedIndicatorColor = Color.Transparent,
//                                unfocusedIndicatorColor = Color.Transparent,
//                                focusedContainerColor = Color.Transparent,
//                                unfocusedContainerColor = Color.Transparent,
//                                // ⭐ Actual input text color
//                                focusedTextColor = Color.Black,
//                                unfocusedTextColor = Color.Black,
//
//                                // (Optional) Cursor color
//                                cursorColor = Color.Black
//                            )
//                        )
//                    }
//                }
//            }
                    if (state.isRecording || state.showVoicePreview) {
                        if (state.isRecording) {
                            // Show recording indicator with live transcription
                            VoicePreviewCard1(
                                transcription = state.transcribedText,
                                onSeeText = {
                                    // Recording के दौरान See text पर क्लिक करने पर
                                    speechMgr?.stopListening()
                                    viewModel.stopRecording()
                                    viewModel.showTranscribedText()
                                },
                                onClose = {
                                    speechMgr?.stopListening()
                                    viewModel.clearVoicePreview()
                                    viewModel.stopRecording()
                                }
                            )
                        } else if (state.showVoicePreview) {
                            // Show final transcription preview
                            VoicePreviewCard1(
                                transcription = state.transcribedText,
                                onSeeText = {
                                    // Final text को message field में सेट करें
                                    viewModel.showTranscribedText()
                                },
                                onClose = {
                                    viewModel.clearVoicePreview()
                                }
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    } else {
                        // Regular text field - केवल जब recording नहीं हो रही है
                        TextField(
                            value = state.message,
                            onValueChange = { viewModel.onMessageChange(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                                fontSize = 13.sp,
                                color = Color.Black
                            ),
                            placeholder = {
                                Text(
                                    "Ask anything…",
                                    fontSize = 12.sp,
                                    color = Color(0xFF949494),
                                    fontFamily = FontFamily(Font(R.font.urbanist_regular))
                                )
                            },
                            maxLines = 4,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Voice/Send Button
            val canSend = state.message.isNotBlank() || state.images.isNotEmpty() || state.pdfs.isNotEmpty()

            AnimatedContent(targetState = canSend, label = "send_button") { sending ->
                if (sending) {
                    IconButton(
                        onClick = {
                            viewModel.sendMessageFromInput()
                            onSendClicked()
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.send_ic),
                            contentDescription = "Send",
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            val hasPermission = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED

                            if (!hasPermission) {
                                recordPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                return@IconButton
                            }

                            if (!state.isRecording) {
                                speechMgr = SpeechRecognizerManager(
                                    context = context,
                                    onPartialResult = { partial ->
                                        viewModel.onMessageChange(partial)
                                    },
                                    onFinalResult = { final ->
                                        viewModel.onVoiceRecorded(final)
                                    },
                                    onError = { error ->
                                        // Handle error
                                    }
                                ).also { it.startListening() }
                                viewModel.toggleRecording()
                            } else {
                                speechMgr?.stopListening()
                                viewModel.stopRecording()
                            }
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.voiceinc_ic),
                            contentDescription = "Voice Input",
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun VoicePreviewCard1(
    transcription: String,
    onSeeText: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF4EFFF), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {

        // SEE TEXT
        Text(
            "See text",
            fontSize = 10.sp,
            color = Color(0xFF4338CA),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .clickable { onSeeText() }
        )

        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painterResource(R.drawable.ic_close),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.clickable { onClose() }
            )

            Spacer(Modifier.width(12.dp))

            // Waveform image
            Image(
                painter = painterResource(R.drawable.voice_waveform),
                contentDescription = null,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(12.dp))


        }
    }
}

