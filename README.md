## About

A simple login page using Spring Boot and MongoDB.

## Setup

```bash
./mvnw spring-boot:run
```

There is an endpoint at *http://localhost:8080/candidate* for POST request to add entry into MongoDB.

For example,

```bash
curl --header "Content-Type: application/json" \
--request POST \
--data '{"name" : "Tom", "password" : "Lee" }' \
http://localhost:8080/candidate
```