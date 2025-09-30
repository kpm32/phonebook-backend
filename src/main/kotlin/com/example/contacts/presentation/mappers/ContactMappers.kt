package com.example.contacts.presentation.mappers

import com.example.contacts.domain.model.Contact
import com.example.contacts.presentation.dto.ContactDto

fun Contact.toDto(): ContactDto = ContactDto(
    name = this.name,
    phone = this.phone,
)

fun List<Contact>.toDtoList(): List<ContactDto> = map { it.toDto() }
