package com.example.myapplication.data.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Database.Repository
import com.example.myapplication.data.Entities.Account
import kotlinx.coroutines.launch

class DatabaseViewModel(
    private val repository: Repository
) : ViewModel() {
    fun getAccountByEmail(email: String) = repository.getAccountByEmail(email).asLiveData(viewModelScope.coroutineContext)

    fun upsertAccount(account: Account){
        viewModelScope.launch {
            repository.upsertAccount(account)
        }
    }
    fun deleteAccount(account: Account){
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}