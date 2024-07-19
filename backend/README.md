# Animusic

## Запуск базы для тестов

```bash
docker run -d -p 5433:5432 --name animusic_db -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test -e POSTGRES_DB=animusic postgres:14
```


