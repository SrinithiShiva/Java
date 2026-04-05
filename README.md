# TaskManagement

Spring Boot task management API with JWT authentication. Supports tasks, assignments, comments, attachments, teams, and projects.

## Requirements
- Java 17
- MySQL

## Configuration
Edit `src/main/resources/application.properties`:
- `spring.datasource.*`
- `jwt.secret` (use a 32+ character secret)
- `jwt.expiration-ms`

## Run
```bash
gradle bootRun
```

## Auth
- Register: `POST /users/register`
- Login: `POST /users/login`
- Use the token in all other requests:
  `Authorization: Bearer <token>`

## Key Endpoints
### Users
- `POST /users/register`
- `POST /users/login`
- `GET /users/{userId}`
- `PUT /users/{userId}`
- `POST /users/{userId}/logout`

### Tasks
- `POST /tasks`
- `GET /tasks/assigned/{userId}`
- `PUT /tasks/{taskId}/status`
- `POST /tasks/{taskId}/assign`
- `GET /tasks/filter?status=OPEN|COMPLETED`
- `GET /tasks/search?query=...`
- `POST /tasks/{taskId}/comments`
- `POST /tasks/{taskId}/attachments`

### Teams
- `POST /teams`
- `POST /teams/{teamId}/invite`

### Projects
- `POST /projects`

## Notes
- Comments are stored in `Feedback` and can be linked via `user_task_id` or `task_id`.
- Attachments follow the comment pattern and link via `user_task_id`.
