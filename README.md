## BookWorm Library Web Application Backend [Work In Progress]

### Java Spring Boot REST API

This is a toy app I once made in my free time. Application is dockerized - use `run_prod.sh` to build and start
containers.

Go to [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html) to see API documentation.

For `run_prod.sh` and tests `mvn verify -DargLine="-Dkarate.env=test"` to run without errors You must put
`127.0.0.1 docker` in `/etc/hosts`

### Services are available on ports

Development environment:

- Bookworm Swagger UI: [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html)
- Keycloak: [http://localhost:8080/](http://localhost:8080/) - admin:admin
- Prometheus: [http://localhost:9090/](http://localhost:9090/)
- Grafana: [http://localhost:3000/](http://localhost:3000/) - admin:admin
- PgAdmin4: [http://localhost/](http://localhost/) - grzegorz@shire.org:password
- Postgres: [tcp://localhost/5432](tcp://localhost/5432)

Production environment (only bookworm-backend:8000 is exposed on docker host):

- Bookworm Swagger UI: [http://bookworm-backend:8000/swagger-ui.html](http://bookworm-backend:8000/swagger-ui.html)
- Keycloak: [http://bookworm-keycloak:8080/](http://bookworm-keycloak:8080/)
- Prometheus: [http://bookworm-prometheus:9090/](http://bookworm-prometheus:9090/)
- Grafana: [http://bookworm-grafana:3000/](http://bookworm-grafana:3000/)
- PgAdmin4: [http://bookworm-pgadmin/](http://bookworm-pgadmin/)
- Postgres: [tcp://bookworm-postgres/5432](tcp://bookworm-postgres/5432)

### Running with Spring Cloud

Uncomment `id "org.springframework.cloud.contract" version "2.1.5.RELEASE"` in build.gradle to be able to run cloud version.
`bootstrap.properties` ensures that app uses config server if one is available.
When using config server add `cloud` to `dev`/`prod` profile to register with eureka (discovery service).

    ./gradlew bootRun --args='--spring.profiles.active=dev,cloud'

### Integration and E2E Tests

Karate tests run on  _Java 8_ only. Use `run_dev.sh` and `boot-run` to run tests on _dev_ with

- maven: `mvn test -DargLine="-Dkarate.env=dev"`
- gradle: `./gradlew test -Dkarate.env=dev`

Alternatively, you can run tests also with `run_prod.sh` on _prod_ (inside docker created network).

There are also properties for _test_ env for karate and java to use with Gitlab CI pipeline.

### How to export Realm config from Keycloak

Enable _configuration 4 realm data export_ config in `docker-compose.yml` and run `run_dev.sh` to start keycloak with
postgres.

	docker/docker-compose up bookworm_postgres bookworm_keycloak

Execute command:

	docker exec -it bookworm-keycloak /opt/jboss/keycloak/bin/standalone.sh \
	-Djboss.socket.binding.port-offset=100 \
	-Dkeycloak.migration.action=export \
	-Dkeycloak.migration.provider=singleFile \
	-Dkeycloak.migration.realmName=bookworm \
	-Dkeycloak.migration.usersExportStrategy=REALM_FILE \
	-Dkeycloak.migration.file=/tmp/realm-bookworm.json

Move `/tmp/realm-bookworm.json` to `docker/keycloak` and `src/test/resources`.

### How to export database to Liquibase changelog file

	liquidbase --url=jdbc:postgresql://localhost:5432/bookworm_library --username=bookworm_user \
	--password=xyzXYZxyz --changeLogFile=output2.xml --diffTypes=data generateChangeLog

### SonarQube call with Gradle

	./gradlew sonarqube -Dsonar.projectKey=bookworm-backend \
	-Dsonar.host.url=http://localhost:9000 -Dsonar.login=PUT-SONAR-TOKEN-HERE

### SpringBoot dashboard for Grafana

	https://grafana.com/grafana/dashboards/12464

### How to register local GitLab Runner with container support

#### Docker-in-Docker with TLS enabled in the Docker executor method

[https://docs.gitlab.com/ee/ci/docker/using_docker_build.html](https://docs.gitlab.com/ee/ci/docker/using_docker_build.html)

    sudo gitlab-runner register -n \
    --url https://gitlab.com/ \
    --registration-token PUT-GITLAB-TOKEN-HERE \
    --executor docker \
    --description "Bookworm Runner" \
    --docker-image "docker:20.10.12" \
    --docker-privileged \
    --docker-volumes "/certs/client"
