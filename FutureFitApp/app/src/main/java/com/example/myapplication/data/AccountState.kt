package com.example.myapplication.data

import com.example.myapplication.data.Entities.Account

data class AccountState(
    val account: List<Account> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val emailAddress: String = ""
)