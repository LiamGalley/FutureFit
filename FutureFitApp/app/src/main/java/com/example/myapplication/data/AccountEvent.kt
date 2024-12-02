package com.example.myapplication.data

sealed interface AccountEvent {
    object RegisterAccount: AccountEvent
    data class SetEmailAddress(val emailAddress: String): AccountEvent
    data class SetFirstName(val firstName: String): AccountEvent
    data class SetLastName(val lastName: String): AccountEvent
}