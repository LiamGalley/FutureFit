package com.example.myapplication.data.ViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI

class GPTViewModel : ViewModel() {
    var gptResponse by mutableStateOf("")

    private val openAI = OpenAI(
        "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"
    )

    @OptIn(BetaOpenAI::class)
    suspend fun fetchGPTResponse(query: String): String {
        return try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-4o-mini"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.User,
                        content = query
                    )
                )
            )
            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
            completion.choices.first().message?.content ?: ""
        } catch (e: Exception) {
            "ERROR: ${e.message ?: "Unknown error"}"
        }
    }

}