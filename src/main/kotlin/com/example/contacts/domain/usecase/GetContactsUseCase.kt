package com.example.contacts.domain.usecase

import com.example.contacts.domain.repository.ContactsRepository
import com.example.contacts.domain.model.Contact

/**
 * Use case for retrieving all contacts.
 */
class GetContactsUseCase(
    private val repository: ContactsRepository
) {
    operator fun invoke(): List<Contact> = repository.getAll()
}