FROM eclipse-temurin:17-ubi9-minimal

COPY . ./app
WORKDIR ./app
RUN chmod +x mvnw
RUN ./mvnw install -Dmaven.test.skip=true

CMD ./mvnw spring-boot:run