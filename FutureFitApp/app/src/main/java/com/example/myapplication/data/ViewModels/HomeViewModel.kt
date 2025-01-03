package com.example.myapplication.data.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DataStores.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    //private val repository: Repository
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _userId: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    private val _userEmail: MutableStateFlow<String> = MutableStateFlow("")
    private val _quote: MutableState<String> = mutableStateOf("")

    init{
        // CALL QUERY TO FILL ABOVE FIELDS

        viewModelScope.launch {
            _userId.value = dataStoreManager.userIdFlow.first()
            _userName.value = dataStoreManager.userNameFlow.first()
            _userEmail.value = dataStoreManager.userEmailFlow.first()
        }
    }

    fun getQuote(){
        // GET QUOTE FROM CHAT GPT
    }
}