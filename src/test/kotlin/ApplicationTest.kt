package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testContactsReturnsJson() = testApplication {
        application {
            module()
        }
        val response = client.get("/contacts")
        val dbgBody = response.bodyAsText()
        println("[DEBUG_LOG] /contacts response: status=${response.status}, body=$dbgBody")
        assertEquals(HttpStatusCode.OK, response.status)
        // Убедимся, что ответ действительно JSON и содержит ожидаемые данные
        val contentType = response.headers[HttpHeaders.ContentType]
        // Может быть application/json; charset=UTF-8 — проверим по префиксу
        assert(contentType != null && contentType.startsWith(ContentType.Application.Json.toString()))
        val body = dbgBody
        assert(body.contains("John Doe"))
        assert(body.contains("Jane Smith"))
    }
}
