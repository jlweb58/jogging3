# jogging3

New version of my private web application which I use to track my jogging activities. This is the backend part, the frontend runs with the angular-jogging project.

Java 13
Spring Boot 2.1.1
Spring REST
Angular 9

Build the application:

./gradlew bootJar

Run as docker image: 
docker run --net=host --rm -e SPRING_PROFILE=localhost jogging 
(if untagged)
docker run --net=host -it -d --restart unless-stopped jlweb58/jlweb58-repo:jogging3-0.9.0
(tagged version on production)

If the profile is omitted, default is prod
