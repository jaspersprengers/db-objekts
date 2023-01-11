# How to use the prepared docker image

```bash
# cd to the current folder
docker build -t db-objekts-mysql .

docker run --name db-objekts-postgres -e POSTGRES_PASSWORD=test -e POSTGRES_USER=test -d postgres:15.1
```
