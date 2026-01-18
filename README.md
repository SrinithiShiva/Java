# Theatre & Movie Show Database

## Overview

This project represents a database schema to manage theatres, movies, film personalities, languages, and show timings. It supports features such as listing shows at a theatre on a specific date, mapping movies to multiple theatres, and filtering by language or cast.

---

## Database Entities & Attributes

### 1. Theatre

Represents cinema theatres.

| Column    | Type         | Description                       |
| --------- | ------------ | --------------------------------- |
| theatreId | INT (PK)     | Unique identifier for the theatre |
| title     | VARCHAR(255) | Name of the theatre               |
| location  | VARCHAR(255) | Address or area of the theatre    |
| latitude  | DECIMAL(9,6) | Latitude of the theatre location  |
| longitude | DECIMAL(9,6) | Longitude of the theatre location |

**Sample Data**:

| theatreId | title         | location                | latitude  | longitude |
| --------- | ------------- | ----------------------- | --------- | --------- |
| 1         | Cineplex Mall | MG Road, Bangalore      | 12.971598 | 77.594566 |
| 2         | Galaxy Cinema | Brigade Road, Bangalore | 12.976231 | 77.603287 |
| 3         | Star Theatre  | Indiranagar, Bangalore  | 12.971891 | 77.641151 |

---

### 2. FilmPersonality

Stores directors and actors.

| Column        | Type         | Description                      |
| ------------- | ------------ | -------------------------------- |
| personalityId | INT (PK)     | Unique identifier for the person |
| name          | VARCHAR(255) | Name of the director or actor    |

**Sample Data**:

| personalityId | name               |
| ------------- | ------------------ |
| 1             | Christopher Nolan  |
| 2             | Robert Downey Jr.  |
| 3             | Leonardo DiCaprio  |
| 4             | Quentin Tarantino  |
| 5             | Scarlett Johansson |

---

### 3. Language

Stores supported movie languages.

| Column       | Type        | Description          |
| ------------ | ----------- | -------------------- |
| languageId   | INT (PK)    | Unique identifier    |
| languageName | VARCHAR(50) | Name of the language |

**Sample Data**:

| languageId | languageName |
| ---------- | ------------ |
| 1          | English      |
| 2          | Hindi        |
| 3          | Tamil        |
| 4          | Telugu       |

---

### 4. Movie

Represents movies with associated director, hero, language, and duration.

| Column     | Type         | Description                                      |
| ---------- | ------------ | ------------------------------------------------ |
| movieId    | INT (PK)     | Unique identifier for the movie                  |
| title      | VARCHAR(255) | Name of the movie                                |
| directorId | INT (FK)     | Reference to `FilmPersonality` (director)        |
| heroId     | INT (FK)     | Reference to `FilmPersonality` (hero/lead actor) |
| languageId | INT (FK)     | Reference to `Language` table                    |
| duration   | INT          | Total runtime in minutes                         |

**Sample Data**:

| movieId | title        | directorId | heroId | languageId | duration |
| ------- | ------------ | ---------- | ------ | ---------- | -------- |
| 1       | Inception    | 1          | 3      | 1          | 148      |
| 2       | Iron Man     | 4          | 2      | 1          | 126      |
| 3       | Pulp Fiction | 4          | 3      | 1          | 154      |

---

### 5. TheatreShow

Mapping table connecting movies with theatres, along with show date, timings, and status.

| Column    | Type     | Description                                     |
| --------- | -------- | ----------------------------------------------- |
| theatreId | INT (FK) | Reference to `Theatre`                          |
| movieId   | INT (FK) | Reference to `Movie`                            |
| date      | DATE     | Show date                                       |
| startTime | TIME     | Show start time                                 |
| endTime   | TIME     | Show end time                                   |
| status    | ENUM     | Show availability: active, cancelled, housefull |

**Sample Data**:

| theatreId | movieId | date       | startTime | endTime  | status    |
| --------- | ------- | ---------- | --------- | -------- | --------- |
| 1         | 1       | 2026-01-18 | 10:00:00  | 12:28:00 | active    |
| 1         | 2       | 2026-01-18 | 13:00:00  | 15:06:00 | housefull |
| 2         | 1       | 2026-01-18 | 11:00:00  | 13:28:00 | active    |
| 3         | 3       | 2026-01-18 | 18:00:00  | 20:34:00 | active    |

---

## Relationships

* One theatre can have multiple shows across different dates.
* One movie can run in multiple theatres at multiple time slots.
* `TheatreShow` acts as a mapping table between `Theatre` and `Movie`.
* `Movie` references `FilmPersonality` for director and hero details.
* `Movie` references `Language` to support language-based filtering.

---

## Sample SQL Queries

### 1. List all shows at a theatre on a given date

```sql
SELECT t.title AS theatre_name,
       m.title AS movie_name,
       m.duration,
       ts.startTime,
       ts.endTime,
       ts.status
FROM TheatreShow ts
INNER JOIN Theatre t ON ts.theatreId = t.theatreId
INNER JOIN Movie m ON ts.movieId = m.movieId
WHERE ts.date = '2026-01-18'
  AND t.title LIKE '%Cineplex Mall%';
```
