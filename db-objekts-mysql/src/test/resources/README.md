# How to use the prepared docker image

The provided Dockerfile uses a multi-stage build, to facilitate experimentation with an already persisted database. This speeds up a container considerably.

https://www.mariadbtutorial.com/getting-started/mariadb-sample-database/

```bash
# cd to the current folder
docker build -t db-objekts-mysql .

docker run --name mysqltest -e MYSQL_ROOT_PASSWORD=test -p 3306:3306 -d  db-objekts-mysql
```
