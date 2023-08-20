Run using Docker Compose:

```bash
./gradlew jib && docker compose up
```

Test using Docker Compose:

```bash
./gradlew jib && docker compose -f compose.test.yaml up --force-recreate -V --build --abort-on-container-exit --exit-code-from test-app
```

Swagger UI:

/api/swagger-ui/index.html