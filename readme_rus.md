# phonebook-backend (Русская версия)

Этот проект создан с помощью [Ktor Project Generator](https://start.ktor.io).

Ниже приведена русскоязычная версия документации, дублирующая основное README.

## Полезные ссылки

- [Документация Ktor](https://ktor.io/docs/home.html)
- [Страница Ktor на GitHub](https://github.com/ktorio/ktor)
- [Kotlin Slack (канал Ktor)](https://app.slack.com/client/T09229ZC6/C0A974TJ9) — для присоединения потребуется [запросить приглашение](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up)

## Возможности (подключенные плагины)

| Название                                                             | Описание                                                                                 |
|---------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing)                          | DSL для организации маршрутов                                                            |
| [Conditional Headers](https://start.ktor.io/p/conditional-headers)  | Пропускает тело ответа при валидных заголовках ETag/LastModified                         |
| [AutoHeadResponse](https://start.ktor.io/p/auto-head-response)      | Автоматические ответы на HEAD на основе уже определённых GET                             |
| [Compression](https://start.ktor.io/p/compression)                  | Сжатие ответов (GZIP и др.)                                                              |
| [Default Headers](https://start.ktor.io/p/default-headers)          | Добавляет набор стандартных заголовков к HTTP-ответам                                    |
| [Status Pages](https://start.ktor.io/p/status-pages)                | Базовая обработка исключений на уровне роутинга                                          |
| [CORS](https://start.ktor.io/p/cors)                                | Включает междоменные запросы (Cross-Origin Resource Sharing)                             |
| [Call Logging](https://start.ktor.io/p/call-logging)                | Логирование входящих HTTP-запросов                                                       |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)  | Автоконвертация контента в соответствии с заголовками Content-Type и Accept              |

## Сборка и запуск

Для сборки/запуска используйте следующие задачи Gradle:

| Задача                          | Описание                                                                 |
|---------------------------------|--------------------------------------------------------------------------|
| `./gradlew test`                | Запуск тестов                                                            |
| `./gradlew build`               | Полная сборка                                                            |
| `buildFatJar`                   | Сборка исполняемого JAR с зависимостями                                  |
| `buildImage`                    | Сборка Docker-образа на основе fat JAR                                   |
| `publishImageToLocalRegistry`   | Публикация Docker-образа в локальный реестр                              |
| `run`                           | Запуск сервера                                                           |
| `runDocker`                     | Запуск сервера из локального Docker-образа                               |

Если сервер успешно стартовал, вы увидите в логах примерно следующее:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## Архитектура (упрощённая Чистая архитектура)

Проект переложен на прагматичную модель «Чистой архитектуры» с чёткими слоями и зависимостями:

- Domain (чистый Kotlin, без зависимостей от фреймворков)
  - Сущности и бизнес-правила
  - Интерфейсы репозиториев
  - Use case'ы (предметно-ориентированная бизнес-логика)
  - Расположение:
    - `src/main/kotlin/com/example/contacts/domain/model/Contact.kt`
    - `src/main/kotlin/com/example/contacts/domain/repository/ContactsRepository.kt`
    - `src/main/kotlin/com/example/contacts/domain/usecase/GetContactsUseCase.kt`

- Data (реализации репозиториев; зависит только от Domain)
  - In-memory реализация для разработки/демо
  - Расположение:
    - `src/main/kotlin/com/example/contacts/data/InMemoryContactsRepository.kt`

- Presentation/Delivery (маршруты Ktor, DTO, мапперы; зависит от Domain; ничего не знает о Data)
  - DTO с аннотациями kotlinx.serialization для I/O API
  - Мапперы доменных сущностей в DTO
  - Ktor-маршруты используют use case'ы доменного слоя и маппят результаты в DTO
  - Расположение:
    - `src/main/kotlin/com/example/plugins/Routing.kt` (HTTP-эндпоинты)
    - `src/main/kotlin/com/example/contacts/presentation/dto/*.kt`
    - `src/main/kotlin/com/example/contacts/presentation/mappers/*.kt`

- DI/Composition Root
  - Минимальный ServiceLocator связывает Data -> Domain и предоставляет use case'ы слою представления
  - Расположение:
    - `src/main/kotlin/com/example/di/ServiceLocator.kt`

Примечания:
- Слой маршрутов зависит от `GetContactsUseCase` и возвращает DTO. Доменная модель не знает о сериализации.
- Текущий репозиторий — in-memory; его можно заменить реализацией поверх базы данных без изменений в роутинге или коде use case.
- Для продакшена можно заменить ServiceLocator на полноценный DI-фреймворк (например, Koin) или собрать композицию через плагины Ktor.

## Дополнительно

- Все комментарии в исходном коде приведены на русском языке.
- Данный файл является русскоязычной копией README. При расхождениях обновляйте оба файла синхронно.