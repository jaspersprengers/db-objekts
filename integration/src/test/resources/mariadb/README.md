# How to use the prepared docker image

The provided Dockerfile uses a multi-stage build, to facilitate experimentation with an already persisted database. This speeds up a container considerably.

```bash
# cd to the current folder
docker build -t db-objekts-mariadb .

docker run --name mariadbtest -e MYSQL_ROOT_PASSWORD=test -p 3306:3306 -d  db-objekts-mariadb
```
