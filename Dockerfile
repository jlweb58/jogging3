FROM alpine/java:21-jdk
RUN mkdir /opt/app
ENV SPRING_PROFILE=prod
ARG VERSION
ENV APP_VERSION=${VERSION}
ARG DB_USERNAME
ARG DB_PASSWORD
ENV SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
COPY build/libs/jogging3-*.jar /opt/app/jogging3.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "/opt/app/jogging3.jar"]
