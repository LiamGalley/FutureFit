package com.example.myapplication
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
    //Where you can change the sent query
    var gptQuery = mutableStateOf("")

    private val openAI = OpenAI(
        "API-KEY-HERE"
    )

    @OptIn(BetaOpenAI::class)
    fun fetchGPTResponse() {
        viewModelScope.launch {
            try {
                val chatCompletionRequest = ChatCompletionRequest(
                    model = ModelId("gpt-4o-mini"),
                    messages = listOf(
                        ChatMessage(
                            role = ChatRole.User,
                            content = gptQuery.value
                        )
                    )
                )

                val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
                gptResponse = completion.choices.first().message?.content ?: ""
            } catch (e: Exception) {
                gptResponse = "ERROR: ${e.cause?.message ?: "Unknown error"}"
            }
        }
    }

    fun setQuery(query: String) {
        gptQuery.value = query
    }
}
