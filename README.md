# Clients — сервис учета клиентов

Бэкенд-часть приложения для учета клиентов компании «Лига Цифровой Экономики».
Сервис предоставляет REST API для создания, чтения, обновления и удаления клиентов (CRUD), данные хранятся в PostgreSQL.

## Технологический стек

- Java 21
- Spring Boot 4.1.2 (Web, Validation, Data JPA)
- Hibernate (Spring Data JPA)
- PostgreSQL 16
- Flyway — миграции БД
- Maven
- Lombok
- springdoc-openapi (Swagger UI)
- Docker Compose — для запуска БД

## Структура проекта

```
com.clients.clients
├── config        # SwaggerConfig — настройка OpenAPI
├── controller    # ClientController — REST-эндпоинты
├── dto           # Request/Response DTO (records)
├── entity        # Сущность Client и enum Status
├── exception     # ClientNotFoundException
├── handler       # GlobalExceptionHandler — единый формат ошибок
├── repository    # ClientRepository (JpaRepository)
├── service       # ClientService — бизнес-логика, транзакции
└── utils         # ClientMapper — преобразование Entity <-> DTO
```

## Модель данных

Таблица `client_account`:

| Колонка       | Тип                      | Ограничения    | Описание                 |
|---------------|--------------------------|----------------|--------------------------|
| `id`          | bigint                   | PK, not null   | Идентификатор            |
| `full_name`   | text                     | not null       | ФИО                      |
| `gender`      | varchar(1)               |                | Пол                      |
| `status`      | varchar(20)              | not null       | Статус (ACTIVE/INACTIVE) |
| `create_dttm` | timestamp with time zone | not null       | Дата создания            |
| `modify_dttm` | timestamp with time zone | not null       | Дата обновления          |

Схема создается автоматически миграцией Flyway (`classpath:db/migration`).
Hibernate работает в режиме `ddl-auto: validate` — он только проверяет соответствие сущностей схеме.

## Запуск

### Требования

- JDK 21 или выше
- Maven 3.9.9
- Docker и Docker Compose

### 1. Поднять базу данных

```bash
docker compose up -d
```

Будет запущен контейнер `clients-postgres` (PostgreSQL 16):

| Параметр | Значение     |
|----------|--------------|
| Хост     | `localhost`  |
| Порт     | `5433`       |
| База     | `clients_db` |
| Логин    | `postgres`   |
| Пароль   | `postgres`   |

### 2. Запустить приложение

```bash
./mvnw spring-boot:run
```

При старте Flyway применит миграции и создаст таблицу `client_account`.
Приложение будет доступно на `http://localhost:8081`.

### Конфигурация

Основные настройки находятся в `src/main/resources/application.yml`:

| Параметр                     | Значение по умолчанию                        |
|------------------------------|----------------------------------------------|
| `server.port`                | `8081`                                       |
| `spring.datasource.url`      | `jdbc:postgresql://localhost:5433/clients_db`|
| `spring.datasource.username` | `postgres`                                   |
| `spring.datasource.password` | `postgres`                                   |
| `swagger-settings.server-url`| `http://localhost:8081`                      |

## Документация API (Swagger)

После запуска приложения Swagger UI доступен по адресу:

- http://localhost:8081/swagger-ui/index.html

OpenAPI-спецификация (JSON): http://localhost:8081/v3/api-docs

## REST API

Базовый путь: `/api/clients`

| Метод    | Путь                 | Описание                  | Успех           | Ошибки                |
|----------|----------------------|---------------------------|-----------------|-----------------------|
| `POST`   | `/api/clients`       | Создать клиента           | `201 Created`   | `400`                 |
| `GET`    | `/api/clients`       | Получить всех клиентов    | `200 OK`        |                       |
| `GET`    | `/api/clients/{id}`  | Получить клиента по ID    | `200 OK`        | `404`                 |
| `PUT`    | `/api/clients/{id}`  | Обновить клиента          | `200 OK`        | `400`, `404`          |
| `DELETE` | `/api/clients/{id}`  | Удалить клиента (hard)    | `200 OK`        | `404`                 |

## Особенности реализации

- Слоистая архитектура: Controller → Service → Repository.
- Транзакции: сервис помечен `@Transactional(readOnly = true)`, методы изменения данных — `@Transactional`.
- Даты создания и обновления проставляются на стороне приложения (`OffsetDateTime`), `modify_dttm` обновляется при каждом `PUT`.
- Статус по умолчанию — `ACTIVE`.
- Удаление «жесткое» — запись физически удаляется из БД.
- Схемой БД управляет Flyway, Hibernate только валидирует её.
- Валидация входных данных через Jakarta Validation, ошибки обрабатываются в `GlobalExceptionHandler`.
- Логирование ключевых операций через SLF4J (`@Slf4j`).

## Остановка

```bash
docker compose down        # остановить БД (данные сохранятся в volume)
docker compose down -v     # остановить БД и удалить данные
```
