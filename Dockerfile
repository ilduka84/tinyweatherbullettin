# our base build image
FROM maven:3.6.2-jdk-8 as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package


FROM openjdk:8
LABEL maintainer="yonni.sciunnach@gmail.com.com"

WORKDIR /usr/local/tinyweatherbullettin/

COPY --from=maven target/tinyweatherbulletin-api.jar ./
ENV SPRING_PROFILES_ACTIVE local

EXPOSE 8080

# remote debugging port for Ide
EXPOSE 8000


CMD ["java","-agentlib:jdwp=transport=dt_socket,address=8000,suspend=n,server=y","-Xss32m", "-Xmx256m", "-jar", "tinyweatherbulletin-api.jar", "--spring.profile.active=$SPRING_PROFILES_ACTIVE"]

HEALTHCHECK --interval=5m --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
