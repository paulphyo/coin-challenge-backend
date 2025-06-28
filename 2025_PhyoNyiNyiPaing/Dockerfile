# Stage 1: dependencies
FROM eclipse-temurin:17-jdk-jammy as deps

WORKDIR /build

COPY coin-challenge-backend/mvnw mvnw
COPY coin-challenge-backend/.mvn/ .mvn/
COPY coin-challenge-backend/pom.xml pom.xml

RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

# Stage 2: package
FROM deps as package

WORKDIR /build/coin-challenge-backend

# Copy source files, but not mvnw or .mvn again to avoid overwriting permissions
COPY coin-challenge-backend/src ./src
COPY coin-challenge-backend/pom.xml ./pom.xml

# Ensure mvnw is executable again (some weird bug)
RUN chmod +x ../mvnw

RUN --mount=type=cache,target=/root/.m2 \
    ../mvnw package -DskipTests && \
    mv target/$(../mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(../mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

# Stage 3: final runtime image
FROM eclipse-temurin:17-jre-jammy AS final

ARG UID=10001

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

WORKDIR /app

COPY --from=package /build/coin-challenge-backend/target/app.jar app.jar

COPY coin-challenge-backend/config.yml config.yml

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "server", "config.yml"]
