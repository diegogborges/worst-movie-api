# Golden Raspberry Awards - Worst Movie Category API

This project is a RESTful API developed with Spring Boot (Java 21), designed to provide insights into the winners and nominees of the **Worst Movie** category of the **Golden Raspberry Awards (Razzies)**.

It reads movie data from a CSV file at application startup and stores it in an in-memory database (H2). The API exposes an endpoint to retrieve producers with the **shortest** and **longest intervals** between two awards in this category.

---

## Features

- Reads and imports CSV data into an embedded H2 database automatically at startup.
- Exposes a RESTful endpoint to get producers with:
    - the shortest interval between two wins.
    - the longest interval between two wins.
- Designed according to **Richardson Maturity Model Level 2**.
- Includes **integration tests** to verify correctness of the output based on provided data.

---

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- Maven
- Docker (Optional)

---

## How to Run the Application

### With Maven

```bash
mvn spring-boot:run
```

### With Docker

```bash
docker build -t worst-movie-api .
docker run -p 8080:8080 worst-movie-api
```

---

## How to Run the Integration Tests

You can run all integration tests using Maven:

```bash
mvn test
```
---

## API Specification

### Endpoint

```http
GET awards/v1/intervals
```

### Response

```json
{
  "min": [
    {
      "producer": "Producer 1",
      "interval": 1,
      "previousWin": 2008,
      "followingWin": 2009
    }
  ],
  "max": [
    {
      "producer": "Producer 2",
      "interval": 99,
      "previousWin": 1900,
      "followingWin": 1999
    }
  ]
}
```

- `min`: list of producers with the **shortest interval** between two awards.
- `max`: list of producers with the **longest interval** between two awards.

---

## CSV Input File

The CSV file containing the movie data should be placed in the `resources` folder with name Movielist.csv and is loaded automatically on startup.

---

## Author

Developed by Diego Gomes Borges.