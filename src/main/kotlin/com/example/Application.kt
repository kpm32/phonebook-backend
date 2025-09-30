package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

// Точка входа в приложение. Поднимает embedded-сервер Ktor на Netty.
// В проде порт/хост лучше забирать из конфигурации (application.conf/env).
fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}


/**
 * Главный модуль Ktor. Здесь подключаем все плагины/конфигурации приложения
 * в рекомендуемом порядке: сетевые плагины, мониторинг, сериализация, роутинг.
 */
fun Application.module() {
    // Базовые HTTP-плагины: CORS/Compression/Headers и т.п.
    configureHTTP()
    // Логирование входящих запросов
    configureMonitoring()
    // ContentNegotiation + kotlinx.serialization(Json)
    configureSerialization()
    // Маршруты приложения
    configureRouting()
}
