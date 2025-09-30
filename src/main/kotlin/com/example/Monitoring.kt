package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

/**
 * Мониторинг/логирование входящих запросов.
 * По умолчанию логируем всё на уровне INFO и фильтруем только URL, начинающиеся с '/'.
 */
fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
        // Логировать только запросы к нашему API. Можно сузить или расширить по необходимости.
        filter { call -> call.request.path().startsWith("/") }
    }
}
