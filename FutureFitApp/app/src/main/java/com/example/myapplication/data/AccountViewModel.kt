package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.interfaces.AccountDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val dao: AccountDao
): ViewModel() {

    //TODO need email to search for under here...
    private val _account = dao.getAccountByEmail("").stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(AccountState())
    val state = combine(_state, _account){
        state, account ->
        state.copy(
            account = account
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AccountState())

    fun onEvent(event: AccountEvent){
        when(event){
            AccountEvent.RegisterAccount -> {
                val emailAddress = state.value.emailAddress
                val firstName = state.value.firstName
                val lastName = state.value.lastName

                if(firstName.isBlank() || lastName.isBlank() || emailAddress.isBlank()){
                    return
                }

                val account = Account(
                    emailAddress = emailAddress,
                    firstName = firstName,
                    lastName = lastName
                )
                viewModelScope.launch {
                    dao.upsertAccount(account)
                }
                _state.update { it.copy(
                    emailAddress = "",
                    firstName = "",
                    lastName = ""
                ) }
            }
            is AccountEvent.SetEmailAddress -> {
                _state.update { it.copy(
                    emailAddress = event.emailAddress
                ) }
            }
            is AccountEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                ) }
            }
            is AccountEvent.SetLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                ) }
            }
        }
    }
}