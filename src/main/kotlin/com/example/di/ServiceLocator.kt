package com.example.di

import com.example.contacts.data.InMemoryContactsRepository
import com.example.contacts.domain.repository.ContactsRepository
import com.example.contacts.domain.usecase.GetContactsUseCase

/**
 * Очень легкий локатор обслуживания для демонстрационных целей.
 * В реальных проектах рассмотрим плагины Koin/Dagger или Ktor для Di.
 */
object ServiceLocator {
    // Data layer
    private val contactsRepository: ContactsRepository by lazy { InMemoryContactsRepository() }

    // Domain use cases
    val getContactsUseCase: GetContactsUseCase by lazy { GetContactsUseCase(contactsRepository) }
}