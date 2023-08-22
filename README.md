Dev run using Docker Compose:

```bash
./gradlew jibDockerBuild && docker compose up --build
```

Prod run using Docker Compose:

```bash
./gradlew jib && docker compose up --build
```

Test using Docker Compose:

```bash
./gradlew jib && docker compose -f compose.test.yaml up --force-recreate -V --build --abort-on-container-exit --exit-code-from test-app
```

# TODO add pre-commit hook

# TODO add kafka tasks to sync areas, companies, vacancies

Swagger UI:

/api/swagger-ui/index.html