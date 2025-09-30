# phonebook-backend

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                               | Description                                                                        |
| --------------------------------------------------------------------|------------------------------------------------------------------------------------ |
| [Routing](https://start.ktor.io/p/routing)                         | Provides a structured routing DSL                                                  |
| [Conditional Headers](https://start.ktor.io/p/conditional-headers) | Skips response body, depending on ETag and LastModified headers                    |
| [AutoHeadResponse](https://start.ktor.io/p/auto-head-response)     | Provides automatic responses for HEAD requests                                     |
| [Compression](https://start.ktor.io/p/compression)                 | Compresses responses using encoding algorithms like GZIP                           |
| [Default Headers](https://start.ktor.io/p/default-headers)         | Adds a default set of headers to HTTP responses                                    |
| [Status Pages](https://start.ktor.io/p/status-pages)               | Provides exception handling for routes                                             |
| [CORS](https://start.ktor.io/p/cors)                               | Enables Cross-Origin Resource Sharing (CORS)                                       |
| [Call Logging](https://start.ktor.io/p/call-logging)               | Logs client requests                                                               |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation) | Provides automatic content conversion according to Content-Type and Accept headers |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```


# phonebook-backend

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                               | Description                                                                        |
| --------------------------------------------------------------------|------------------------------------------------------------------------------------ |
| [Routing](https://start.ktor.io/p/routing)                         | Provides a structured routing DSL                                                  |
| [Conditional Headers](https://start.ktor.io/p/conditional-headers) | Skips response body, depending on ETag and LastModified headers                    |
| [AutoHeadResponse](https://start.ktor.io/p/auto-head-response)     | Provides automatic responses for HEAD requests                                     |
| [Compression](https://start.ktor.io/p/compression)                 | Compresses responses using encoding algorithms like GZIP                           |
| [Default Headers](https://start.ktor.io/p/default-headers)         | Adds a default set of headers to HTTP responses                                    |
| [Status Pages](https://start.ktor.io/p/status-pages)               | Provides exception handling for routes                                             |
| [CORS](https://start.ktor.io/p/cors)                               | Enables Cross-Origin Resource Sharing (CORS)                                       |
| [Call Logging](https://start.ktor.io/p/call-logging)               | Logs client requests                                                               |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation) | Provides automatic content conversion according to Content-Type and Accept headers |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## Architecture (Clean Architecture layout)

The project was refactored to follow a pragmatic Clean Architecture structure with clear layering and dependencies:

- Domain (pure Kotlin, no framework dependencies)
  - Entities and business rules
  - Repository interfaces
  - Use cases (application-specific business logic)
  - Location:
    - src/main/kotlin/com/example/contacts/domain/model/Contact.kt
    - src/main/kotlin/com/example/contacts/domain/repository/ContactsRepository.kt
    - src/main/kotlin/com/example/contacts/domain/usecase/GetContactsUseCase.kt

- Data (implements repositories; depends on Domain only)
  - In-memory repository for development/demo
  - Location:
    - src/main/kotlin/com/example/contacts/data/InMemoryContactsRepository.kt

- Presentation/Delivery (Ktor routes, DTOs, mappers; depends on Domain; knows nothing about Data)
  - DTOs with kotlinx.serialization for API input/output
  - Mappers from domain entities to DTOs
  - Ktor routing uses domain use cases and maps to DTOs
  - Locations:
    - src/main/kotlin/com/example/plugins/Routing.kt (HTTP endpoints)
    - src/main/kotlin/com/example/contacts/presentation/dto/*.kt
    - src/main/kotlin/com/example/contacts/presentation/mappers/*.kt

- DI/Composition Root
  - Minimal ServiceLocator wires Data -> Domain and exposes use cases to the presentation layer
  - Location:
    - src/main/kotlin/com/example/di/ServiceLocator.kt

Notes:
- The routing layer depends on GetContactsUseCase and returns DTOs. The domain model stays free of serialization.
- The current repository is in-memory; it can be replaced with a database-backed implementation without touching the routing or domain use case code.
- For production, consider replacing ServiceLocator with a DI framework (e.g., Koin) or Ktor's plugin-based composition.
