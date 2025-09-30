package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

/**
 * Настройка сериализации/десериализации через ContentNegotiation + kotlinx.serialization(Json).
 *
 * Заметки:
 * - prettyPrint: удобно для чтения ответов во время разработки.
 * - isLenient: позволяет более свободный JSON во входящих данных.
 * - ignoreUnknownKeys: игнорирует лишние поля в запросах (не падаем при эволюции API).
 * - explicitNulls: false — не посылать явные null'ы, что делает JSON компактнее.
 */
@OptIn(ExperimentalSerializationApi::class)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true        // Форматируем ответы для удобства отладки
                isLenient = true          // Разрешаем «мягкий» парсинг входящего JSON
                ignoreUnknownKeys = true  // Игнорируем неизвестные ключи в запросах
                explicitNulls = false     // Не включаем явные null в сериализованный JSON
            }
        )
    }
}
