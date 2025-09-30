package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*

/**
 * Базовая HTTP-конфигурация приложения: кэширующие заголовки, компрессия, default headers и CORS.
 *
 * Важно: настройки CORS следует ограничивать для продакшена (origin/headers/methods),
 * чтобы не открывать API всем доменам.
 */
fun Application.configureHTTP() {
    // Skip тела ответа, если валидны ETag/Last-Modified в запросе
    install(ConditionalHeaders)

    // GZIP/Deflate и др. — уменьшение размера ответов
    install(Compression)

    // Добавляем дефолтные заголовки ко всем ответам
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // будет отправляться с каждым ответом
    }

    // Разрешаем междоменные запросы для фронтенда
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        anyHost() // ВНИМАНИЕ: в продакшене заменить на конкретные host'ы (allowHost)
    }
}
