# jogging3

New version of my private web application which I use to track my jogging activities. This is the backend part, the frontend runs with the angular-jogging project.

Java 13
Spring Boot 2.1.1
Spring REST
Angular 9

## Build the application:

./gradlew bootJar

docker build -t jogging:latest .
docker tag jogging jlweb58/jlweb58-repo:jogging3-1.0.2
docker push jlweb58/jlweb58-repo:jogging3-1.0.2

Run the database locally (only works when the app runs in docker):
docker run --net=host -d --rm -e MYSQL_ROOT_PASSWORD=pw -e MYSQL_DATABASE=db  jlweb58/jlweb58-repo:database_dump-latest

Run as docker image: 
docker run --net=host --rm -e SPRING_PROFILE=localhost jogging 
(if untagged)
docker run --net=host -it -d --restart unless-stopped jlweb58/jlweb58-repo:jogging3-1.0.2
(tagged version on production)

If the profile is omitted, default is prod
