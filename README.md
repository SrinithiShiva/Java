# ShareRecipe

A modern recipe publishing platform built with Spring Boot. It provides a secure REST API for public browsing, chef onboarding, JWT-protected authoring, image uploads with resizing, chef following, and an async worker that publishes queued recipes.

## Features

- Public recipe listing with keyword search, date filters, and pagination.
- Chef sign-up/login with JWT access tokens and optional refresh tokens.
- Draft and publish flows with async queueing.
- Image upload with automatic resizing + thumbnails.
- Follow/unfollow chefs and view followed chefs' recipes.
- Separate worker app that processes the publish queue.

## Tech Stack

- Java 17
- Spring Boot 3.x (Web, Data JPA, Security, Validation)
- MySQL
- JWT (jjwt)

## Project Structure

- `src/main/java/com/example/sharerecipe`: API application
- `src/main/java/com/example/sharerecipe/worker`: Worker components
- `src/main/resources/application.properties`: API config
- `src/main/resources/application-worker.properties`: Worker config

## Configuration

Update `src/main/resources/application.properties` with your MySQL credentials and JWT secret:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `app.jwt.secret` (Base64-encoded secret)

Optional toggles:

- `app.auth.refresh-enabled=true|false`
- `app.auth.email-verification-enabled=true|false`

## Run the API

```bash
./gradlew bootRun
```

The API runs on `http://localhost:8080` by default.

## Run the Worker

```bash
./gradlew bootRun --main-class com.example.sharerecipe.ShareRecipeWorkerApplication
```

The worker runs without a web server and polls the publish queue at a fixed interval.

## API Overview

### Auth

- `POST /auth/signup`
- `POST /auth/login`
- `POST /auth/refresh`
- `GET /auth/verify?token=...`

### Recipes

- `GET /recipes` (public)
- `GET /recipes/followed` (JWT required)
- `POST /recipes` (multipart, JWT required)
- `PUT /recipes/{id}` (JWT required)
- `POST /recipes/{id}/publish` (JWT required)
- `DELETE /recipes/{id}` (JWT required)
- `POST /recipes/{id}/images` (multipart, JWT required)

Filters for `GET /recipes` and `/recipes/followed`:

- `keyword`
- `published_from` (ISO-8601)
- `published_to` (ISO-8601)
- `chef_id`
- `chef_handle`
- `page`, `page_size`

### Follow

- `POST /chefs/{id}/follow` (JWT required)
- `DELETE /chefs/{id}/follow` (JWT required)

## Authoring Payloads

### Create Recipe (multipart)

`POST /recipes` with:

- `data` (JSON)
- `images` (1..n files)

Example `data`:

```json
{
  "title": "Spicy Tomato Pasta",
  "summary": "Quick weeknight pasta",
  "ingredients": ["pasta", "tomatoes", "garlic"],
  "steps": ["Boil pasta", "Cook sauce", "Combine"],
  "labels": ["italian", "quick"],
  "draft": false
}
```

### Update Recipe

`PUT /recipes/{id}`

```json
{
  "title": "Updated title",
  "summary": "Updated summary",
  "ingredients": ["..."],
  "steps": ["..."],
  "labels": ["..."]
}
```

## Notes

- Published recipes are only visible after the worker processes the queue.
- The JWT access token TTL and refresh TTL are configurable.
- The worker uses the same database; it runs under the `worker` Spring profile.

## License

MIT
