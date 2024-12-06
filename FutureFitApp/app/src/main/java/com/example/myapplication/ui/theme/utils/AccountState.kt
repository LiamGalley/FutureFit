package com.example.myapplication.ui.theme.utils

import com.example.myapplication.data.Entities.Account

data class AccountState(
    val account: List<Account> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val emailAddress: String = ""
)