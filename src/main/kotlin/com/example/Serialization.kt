package com.example


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
