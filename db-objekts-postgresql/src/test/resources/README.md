# Run a postgresql in docker

```bash
# cd to the current folder

docker run --name db-objekts-postgres -p 5432:5432 -e POSTGRES_PASSWORD=test -e POSTGRES_USER=test -d postgres:15.1
```
