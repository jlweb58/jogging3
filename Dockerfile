FROM adoptopenjdk/openjdk13:ubi
RUN mkdir /opt/app
ENV SPRING_PROFILE=prod
COPY build/libs/jogging3.jar /opt/app
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-Dlog4j2.formatMsgNoLookups=true", "-jar", "/opt/app/jogging3.jar"]
