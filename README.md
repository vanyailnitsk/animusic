# Animusic - сервис для прослушивания музыки из Аниме

---

## **Основные функции и возможности**

---
&ast; - в разработке

- **Прослушивание саундтреков**: Пользователи могут просматривать и слушать саундтреки из различных аниме. Каждый саундтрек снабжен информацией о названии аниме, типе трека и другими деталями.
- **Поиск** : Пользователи могут легко находить саундтреки из аниме с помощью функции поиска по конкретному аниме.
- **Создание и управление плейлистами***: Пользователи могут создавать собственные плейлисты, добавлять в них треки и управлять порядком воспроизведения.
- **Авторизация и учетные записи***: Регистрированные пользователи могут создавать учетные записи, входить в систему и сохранять свои плейлисты и настройки.
- **Социальное взаимодействие***: Пользователи могут делиться своими плейлистами с другими пользователями и обсуждать музыку в комментариях.

## **Backend-приложение**

---

Backend-часть проекта включает в себя следующий функционал:

- **CRUD для треков и аниме**: Администраторы могут управлять коллекцией саундтреков и информацией об аниме. Они могут добавлять, редактировать и удалять записи.
- Создание саундтреков по запросу: Администраторы могут создавать саундтреки на основе существующего аудио-файла или с помощью URL видео из YouTube
- **Управление учетными записями пользователей**: Администраторы имеют доступ к системе управления учетными записями пользователей. Это включает в себя возможность блокировки и удаления учетных записей при необходимости.
- **Авторизация и аутентификация**: Backend обеспечивает безопасность данных и аутентификацию пользователей, а также управление сеансами.
- **API для взаимодействия с фронтендом**: Фронтенд и бэкенд взаимодействуют через REST API, обеспечивая передачу данных и обновлений.

## Структура проекта

---

Проект имеет стандартную структуру Spring Boot - приложения и содержит следующие директории:
configs - конфигурации приложения (безопасности, конфиги с тестовыми данными)

controllers - REST-контроллеры

dto - Data Access Objects - классы для передачи данных в JSON

models - ORM-модели хранимых данных

repositories - JPA-репозитории для управления данными в БД

services - сервисные классы, которые управляют данными в БД с помощью манипуляций с репозиториями

## **Требования и зависимости**

---

Для запуска приложения вам понадобятся следующие компоненты:

- Java 17+
- Maven
- PostgreSQL
- youtube-dl CLI

## Запуск приложения локально

---

Для локального запуска приложения нужно выполнить следующие шаги:

Клонировать репозиторий и перейти в папку проекта:

```bash
git clone https://github.com/vanyailnitsk/animusic-backend.git
cd animusic
```

Запустить PostgreSQL, создать базу данных “animusic” и передать права доступа пользователю

```bash
psql
CREATE DATABASE animusic;
grant all privileges on database animusic to postgres;
grant all privileges on database animusic to имя_пользователя;
```

Создать директорию, где будут хранится аудио-файлы приложения. Например,

```bash
mkdir animusic-audio
cd animusic-audio
```

Далее необходимо сконфигурировать файл application.properties, находящийся в папке проекта src/main/resources

spring.datasource.url = jdbc:postgresql://localhost:5432/animusic. Если PostgreSQL запущен на другом порте, то вместо 5432 указываем его

```bash
spring.datasource.url = jdbc:postgresql://localhost:5432/animusic
spring.datasource.username=//ваше имя пользователя
spring.datasource.password=//ваш пароль
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=false
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB
audiotracks.directory=//абсолютный путь до директории с аудиофайлами
server.error.include-message=always
```

После чего остается только запустить приложение с помощью Maven:

Если вы используете Windows, то выполняем команду

```bash
./mvnw.cmd spring-boot:run
```

Если у вас macOS или Linux, а также установлен Maven и переменная окружения mvn, то переходим в корневую директорию проекта и запускаем приложение командой

```bash
mvn spring-boot:run
```

Если же Maven не установлен, то выполняем команду

```bash
./mvnw spring-boot:run
```

## Использование

---

### A****udio-management-controller****

1) POST /api/audio/create-from-youtube - создать саундтрек по URL YouTube- видео

Request body example

```json
{
  "originalTitle": "string",
  "animeTitle": "string",
  "anime": "string",
  "trackType": "string",
  "videoUrl": "string"
}
```

Response example

```json
{
  "id": 0,
  "originalTitle": "string",
  "animeTitle": "string",
  "type": "OPENING",
  "pathToFile": "string",
  "animeName": "string"
}
```

2) POST /api/soundtrack/create-from-file - создать саундтрек с помощью аудиофайла 

Пример использования:

```json
curl --location 'localhost:8080/api/audio/create-from-file' \
--form 'file=@"/Users/admin/Downloads/Attack on Titan - Opening Feuerroter Pfeil und Bogen [TubeRipper.com].mp3"' \
--form 'originalTitle="Guren no Umiya"' \
--form 'animeTitle="Opening 1"' \
--form 'trackType="OPENING"' \
--form 'anime="Naruto Shippuden"'
```

### Anime-controller

1) POST /api/anime/create - создать аниме

****Request body****

```json
{
  "id": 0,
  "title": "string",
  "studio": "string",
  "releaseYear": {
    "value": 0,
    "leap": true
  },
  "description": "string",
  "folderName": "string",
}
```

**Response**

```json
{
  "id": 0,
  "title": "string",
  "studio": "string",
  "releaseYear": {
    "value": 0,
    "leap": true
  },
  "description": "string",
  "folderName": "string",
  "soundtracks": [
    {
      "id": 0,
      "originalTitle": "string",
      "anime": "string",
      "animeTitle": "string",
      "type": "OPENING",
      "pathToFile": "string",
      "animeName": "string"
    }
  ]
}
```

2) GET /api/anime/navigation - список id и названий всех аниме для навигационной панели на фронтенде

**Response**

```json
[
  {
    "id": 0,
    "title": "string"
  }
]
```

3) GET /api/anime/info/{animeId} - получение всей информации про аниме

**Parameters**

animeId

**Response**

```json
{
  "id": 0,
  "title": "string",
  "studio": "string",
  "releaseYear": {
    "value": 0,
    "leap": true
  },
  "description": "string",
  "folderName": "string",
  "soundtracks": [
    {
      "id": 0,
      "originalTitle": "string",
      "anime": "string",
      "animeTitle": "string",
      "type": "OPENING",
      "pathToFile": "string",
      "animeName": "string"
    }
  ]
}
```

3) GET /api/anime/all-soundtracks-by-anime-id/{animeId} - получение списка треков из определенного аниме

**Parameters**

animeId

**Response**

```json
[
  {
    "id": 0,
    "originalTitle": "string",
    "animeTitle": "string",
    "type": "OPENING",
    "pathToFile": "string",
    "animeName": "string"
  }
]
```
