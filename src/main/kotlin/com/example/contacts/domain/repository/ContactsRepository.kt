package com.example.contacts.domain.repository

import com.example.contacts.domain.model.Contact

/**
 * Repository contract for accessing contacts data.
 */
interface ContactsRepository {
    fun getAll(): List<Contact>
}