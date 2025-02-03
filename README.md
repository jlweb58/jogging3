# jogging3

New version of my private web application which I use to track my jogging activities. This is the backend part, the frontend runs with the angular-jogging project.

Java 21
Spring Boot 3.4.0
Spring REST
Angular 19

## Build the application:

./gradlew bootJar

docker build -t jogging:latest .
docker tag jogging jlweb58/jlweb58-repo:jogging3-1.1.0
docker push jlweb58/jlweb58-repo:jogging3-1.1.0

Run the database locally (only works when the app runs in docker):
docker run --net=host -d --rm -e MYSQL_ROOT_PASSWORD=pw -e MYSQL_DATABASE=db  jlweb58/jlweb58-repo:database_dump-latest

# Build for production
docker build -t jogging:latest . --platform linux/amd64

Run as docker image: 
docker run --net=host --rm -e SPRING_PROFILE=localhost jogging 
(if untagged)
docker run --net=host -it -d -e TZ="Europe/Berlin" --restart unless-stopped jlweb58/jlweb58-repo:jogging3-1.1.0
(tagged version on production)

If the profile is omitted, default is prod

## Better way to run both DB and app with docker on localhost for testing:
* docker network create mynetwork
* application-localhost.yml: Datasource URL should be "jdbc:mysql://activitydb/jlweb"
* docker run --name activitydb --network mynetwork -e MYSQL_ROOT_PASSWORD=xxx -e MYSQL_DATABASE=jlweb  jlweb58/jlweb58-repo:database_dump-latest
* docker run -p9005:9005 --network mynetwork --rm -e SPRING_PROFILE=localhost jogging

## TODO
* Update Spring Boot to 3.4. This will break the WebSecurityConfig, requiring considerable refactoring!


