package com.example.contacts.data

import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.repository.ContactsRepository

/**
 * Простая реализация в памяти для демонстрации/разработки.
 */
class InMemoryContactsRepository : ContactsRepository {
    override fun getAll(): List<Contact> = listOf(
        Contact("John Doe", "123-456-7890"),
        Contact("Jane Smith", "987-654-3210"),
    )
}