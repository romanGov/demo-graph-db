#!/bin/bash

echo "=========================="
echo "stopping arcade db"
echo "----------------"

docker container stop arcade-db

echo "=========================="
echo "stopping postgresql demo graph db"
echo "----------------"

docker container stop graph-db-demo-postgres


echo "=========================="
echo "remove old images arcade db"
echo "----------------"

docker container rm arcade-db

echo "=========================="
echo "remove old images demo graph db postgres"
echo "----------------"

docker container rm graph-db-demo-postgres

echo "=========================="
echo "Creating arcade db"
echo "----------------"


docker run --name arcade-db \
            --rm -p 2480:2480 -p 2424:2424 -p 8182:8182 -p 6379:6379 -p 5432:5432 \
           -e JAVA_OPTS="-Darcadedb.server.rootPassword=playwithdata
                         -Darcadedb.server.defaultDatabases=demo_graph[root:playwithdata]"\
           -d arcadedata/arcadedb:latest

echo "=========================="
echo "Creating postgres demo graph  db"
echo "----------------"

docker run --name graph-db-demo-postgres   \
             -e POSTGRES_USER=demo     \
             -e POSTGRES_PASSWORD=123456    \
             -e POSTGRES_DB=demo_graph -p 7450:5432 \
             -d postgres

sleep 3
echo "=========================="
echo "launch gradle"
echo "----------------"

./gradlew clean bootJar

./gradlew flywayMigrate

echo "=========================="
echo "local env was created"
echo "----------------"