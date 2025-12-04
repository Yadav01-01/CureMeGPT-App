package com.bussiness.curemegptapp.viewmodel

import android.app.Application
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import com.bussiness.curemegptapp.data.model.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val app: Application
) : ViewModel() {

    // ----------------------------------------------------------------
    // INITIAL SAMPLE MESSAGES
    // ----------------------------------------------------------------
    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                text = "hey last a few hours and usually get worse in the evening.",
                isUser = true,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 2
            ),
            ChatMessage(
                text = "I’m here to help, James! 😊\n\n" +
                        "How long do the headaches usually last?\n" +
                        "Do they get worse in morning/evening?",
                isUser = false,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60
            ),
            ChatMessage(
                text = "Yes, I feel some pain in my molar when drinking cold water.",
                isUser = true,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 45
            ),
            ChatMessage(
                text = "Thanks for sharing. Evening headaches can be linked to stress or teeth grinding.\n\nHave you noticed jaw clenching or tooth sensitivity?",
                isUser = false,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 4
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages


    // ----------------------------------------------------------------
    // MESSAGE TEXT
    // ----------------------------------------------------------------
    private val _currentMessage = MutableStateFlow("")
    val currentMessage: StateFlow<String> get() = _currentMessage

    fun onMessageChange(value: String) {
        _currentMessage.value = value.take(250)
    }


    // ----------------------------------------------------------------
    // SELECTED IMAGES
    // ----------------------------------------------------------------
    private val _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    val selectedImages: StateFlow<List<Uri>> get() = _selectedImages

    fun onImageSelected(uri: Uri) {
        _selectedImages.value += uri
    }

    fun removeImage(uri: Uri) {
        _selectedImages.value -= uri
    }


    // ----------------------------------------------------------------
    // SELECTED PDF FILES
    // ----------------------------------------------------------------
    data class PdfData(
        val uri: Uri,
        val name: String = "Document.pdf"
    )

    private val _selectedPdfs = MutableStateFlow<List<PdfData>>(emptyList())
    val selectedPdfs: StateFlow<List<PdfData>> get() = _selectedPdfs

    fun onPdfSelected(uri: Uri) {
        _selectedPdfs.value += PdfData(
            uri = uri,
            name = getPdfFileName(uri)
        )
    }

    fun removePdf(pdf: PdfData) {
        _selectedPdfs.value -= pdf
    }

    private fun getPdfFileName(uri: Uri): String {
        return app.contentResolver.query(uri, null, null, null, null)
            ?.use { cursor ->
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                if (index >= 0) cursor.getString(index) else "Document.pdf"
            } ?: "Document.pdf"
    }


    // ----------------------------------------------------------------
    // SEND MESSAGE (TEXT + IMAGES + PDF)
    // ----------------------------------------------------------------
    fun sendMessage() {
        val text = _currentMessage.value.trim()
        val imgs = _selectedImages.value
        val pdfs = _selectedPdfs.value

        if (text.isEmpty() && imgs.isEmpty() && pdfs.isEmpty())
            return

        val time = System.currentTimeMillis()

        val message = ChatMessage(
            text = text,
            isUser = true,
            timestamp = time,
            images = imgs,
            pdfs = pdfs
        )

        _messages.value += message

        // Reset inputs
        _currentMessage.value = ""
        _selectedImages.value = emptyList()
        _selectedPdfs.value = emptyList()
    }


    // ----------------------------------------------------------------
    // VOICE INPUT
    // ----------------------------------------------------------------
    fun startVoiceInput() {
        println("Voice input started… (implement STT here)")
    }
}
