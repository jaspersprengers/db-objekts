# taken from https://gist.github.com/bcremer/9ea822259214fa1161e15bc2203a20d9
FROM mariadb:10.6 as builder

USER root

RUN mkdir -p /initialized-db && chown -R mysql:mysql /initialized-db

# That file does the DB initialization but also runs mysql daemon, by removing the last line it will only init
RUN ["sed", "-i", "s/exec \"$@\"/echo \"not running $@\"/", "/usr/local/bin/docker-entrypoint.sh"]

# needed for intialization
ENV MYSQL_ROOT_PASSWORD=test
ENV MYSQL_DATABASE=test
ENV MYSQL_USER=test
ENV MYSQL_PASSWORD=test

USER mysql

COPY schema.sql /docker-entrypoint-initdb.d/

# Need to change the datadir to something else that /var/lib/mysql because the parent docker file defines it as a volume.
# https://docs.docker.com/engine/reference/builder/#volume :
#       Changing the volume from within the Dockerfile: If any build steps change the data within the volume after
#       it has been declared, those changes will be discarded.
RUN ["docker-entrypoint.sh", "mysqld", "--datadir", "/initialized-db"]

FROM mariadb:10.6

COPY --from=builder /initialized-db /initialized-db

USER root
RUN chown -R mysql:mysql /initialized-db

USER mysql
CMD ["--datadir", "/initialized-db"]
