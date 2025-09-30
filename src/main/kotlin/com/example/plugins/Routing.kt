package com.example.plugins

import com.example.contacts.presentation.dto.ContactsResponse
import com.example.contacts.presentation.mappers.toDtoList
import com.example.di.ServiceLocator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*




/**
 * Конфигурация роутинга приложения.
 * Здесь же подключаем полезные плагины для уровня маршрутов:
 * - AutoHeadResponse: автоматически отвечает на HEAD на основе GET.
 * - StatusPages: простая обработка необработанных исключений -> 500.
 */
fun Application.configureRouting() {
    // Автоответы на HEAD-запросы для определённых GET-маршрутов
    install(AutoHeadResponse)

    // Базовая обработка исключений. В проде стоит логировать cause и возвращать JSON-ошибки.
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            // Примечание: сообщение исключения уходит клиенту — OK для дев-окружения,
            // но в продакшене лучше скрывать детали и возвращать стандартный ответ.
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        // Простой health-check/hello endpoint
        get("/") {
            call.respondText("Hello World!")
        }

        // Примерный эндпоинт телефонной книги. Сейчас возвращает статический список.
        // TODO: заменить на слой хранения (БД/репозиторий) и добавить CRUD-операции.
        get("/contacts") {
            val contacts = ServiceLocator.getContactsUseCase.invoke()
            val response = ContactsResponse(contacts = contacts.toDtoList())
            call.respond(response)
        }
    }
}
