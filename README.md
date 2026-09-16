# Coding — JWT Auth REST API

Spring Boot bilan yozilgan autentifikatsiya va foydalanuvchi boshqaruvi API.

## Texnologiyalar
- Java 21, Spring Boot 3.3
- PostgreSQL, Redis
- JWT (access + refresh token, blacklist)
- Spring Security, rol-based access
- Swagger, JUnit + Mockito + Testcontainers

## Xususiyatlar
- Register / Login
- JWT token (access + refresh)
- Logout (Redis blacklist)
- Rol himoyasi (USER / ADMIN)
- Global exception handling va validatsiya
- Unit va integratsion testlar

## Ishga tushirish
1. PostgreSQL va Redis o'rnating
2. `application.properties.example` dan `application.properties` yarating
3. `./gradlew bootRun`
4. Swagger: http://localhost:8080/swagger-ui.html
