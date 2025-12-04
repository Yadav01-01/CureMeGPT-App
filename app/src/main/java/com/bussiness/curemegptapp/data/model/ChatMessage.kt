package com.bussiness.curemegptapp.data.model

import android.net.Uri
import com.bussiness.curemegptapp.viewmodel.ChatViewModel

data class ChatMessage(
    val text: String = "",
    val isUser: Boolean = false,
    val timestamp: Long = 0L,

    val images: List<Uri> = emptyList(),
    val pdfs: List<ChatViewModel.PdfData> = emptyList(),

    val attachmentUri: Uri? = null,
    val attachmentType: String? = null
)


