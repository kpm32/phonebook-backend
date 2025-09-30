package com.example.contacts.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContactsResponse(
    val contacts: List<ContactDto>
)