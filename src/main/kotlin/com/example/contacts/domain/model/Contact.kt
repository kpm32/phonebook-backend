package com.example.contacts.domain.model

/**
 * Domain entity for a contact. Pure Kotlin model without serialization annotations.
 */
data class Contact(
    val name: String,
    val phone: String,
)