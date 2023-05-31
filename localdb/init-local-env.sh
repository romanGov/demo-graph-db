#!/bin/bash

echo "=========================="
echo "stopping containers"
echo "----------------"

docker container stop arcade-db
docker container stop graph-db-demo-postgres
docker container stop rabbitmq-demo-graph-db

echo "=========================="
echo "remove old images"
echo "----------------"

docker container rm arcade-db
docker container rm graph-db-demo-postgres
docker container rm rabbitmq-demo-graph-db

echo "=========================="
echo "Creating containers"
echo "----------------"


docker run --name arcade-db \
            --rm -p 2480:2480 -p 2424:2424 -p 8182:8182 -p 6379:6379 -p 5432:5432 \
           -e JAVA_OPTS="-Darcadedb.server.rootPassword=playwithdata
                         -Darcadedb.server.plugins=GremlinServer:com.arcadedb.server.gremlin.GremlinServerPlugin
                         -Darcadedb.server.defaultDatabases=graph[root:playwithdata]"\
           -d arcadedata/arcadedb:latest


docker run --name graph-db-demo-postgres   \
             -e POSTGRES_USER=demo     \
             -e POSTGRES_PASSWORD=123456    \
             -e POSTGRES_DB=demo_graph -p 7450:5432 \
             -d postgres

docker run --name rabbitmq-demo-graph-db  \
             -e RABBITMQ_DEFAULT_USER=rabbitmq     \
             -e RABBITMQ_DEFAULT_PASS=rabbitmq    \
             -e RABBITMQ_DEFAULT_VHOST='/' -p 5670:5672 -p 15670:15672\
             -d rabbitmq:3.8-management


sleep 3
echo "=========================="
echo "launch gradle"
echo "----------------"

./gradlew flywayMigrate
./gradlew clean bootJar


echo "=========================="
echo "local env was created"
echo "----------------"