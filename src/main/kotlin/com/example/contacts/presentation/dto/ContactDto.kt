package com.example.contacts.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContactDto(
    val name: String,
    val phone: String,
)