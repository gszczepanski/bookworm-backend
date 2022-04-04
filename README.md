## BookWorm Library Web Application Backend [Work In Progress]

### Java Spring Boot REST API

This is a toy app I once made in my free time. Application is dockerized - use `run_prod.sh` to build and start
containers (must have `docker` and `docker-compose` installed).

When running on development environment, go to [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html) to see API documentation.

### Where to look for services

Development environment: start support services with run_dev.sh and use gradle to start backend on localhost:8000

	./gradlew bootRun --args='--spring.profiles.active=dev'

- Bookworm Swagger UI: [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html)
- Keycloak: [http://localhost:8080/](http://localhost:8080/) - admin:admin
- Prometheus: [http://localhost:9090/](http://localhost:9090/)
- Grafana: [http://localhost:3000/](http://localhost:3000/) - admin:admin
- PgAdmin4: [http://localhost/](http://localhost/) - grzegorz@shire.org:password
- Postgres: [tcp://localhost/5432](tcp://localhost/5432)

Production environment: start it with `run_prod.sh`, only `bookworm-backend:8000` will be exposed on docker host (swagger-ui not available):

- Bookworm Backend: [http://localhost:8000/](http://localhost:8000/)
- Keycloak: [http://bookworm-keycloak:8080/](http://bookworm-keycloak:8080/)
- Prometheus: [http://bookworm-prometheus:9090/](http://bookworm-prometheus:9090/)
- Grafana: [http://bookworm-grafana:3000/](http://bookworm-grafana:3000/)
- PgAdmin4: [http://bookworm-pgadmin/](http://bookworm-pgadmin/)
- Postgres: [tcp://bookworm-postgres/5432](tcp://bookworm-postgres/5432)

### Running with Spring Cloud

Uncomment `id "org.springframework.cloud.contract" version "2.1.5.RELEASE"` in `build.gradle` to be able to run cloud version.
`bootstrap.properties` ensures that app uses config server if one is available.
When using config server add `cloud` to `dev`/`prod` profile to register with _eureka_ (discovery service).

    ./gradlew bootRun --args='--spring.profiles.active=dev,cloud'

### SpringBoot dashboard for Grafana

- add Prometheus on Graphana _Data Sources_ config page, service address when running with `prod` profile will be `http://bookworm-prometheus:9090/` 
- import dashboard definition to see any statistics from application
- run tests to create some traffic 

Dashboard definition `Spring Boot Statistics` can be found on Grafana webpages:

    https://grafana.com/grafana/dashboards/12464

### Test examples included in project

Use `run_dev.sh` and `./gradlew bootRun` to run tests on _dev_ with

[- maven: `mvn test -DargLine="-Dkarate.env=dev"`]:maven-not-present
- gradle: `./gradlew test -Dkarate.env=dev`

Alternatively, you can run tests also with `run_prod.sh` on _prod_ (inside docker created network).
To run tests on that environment put `127.0.0.1 docker` in `/etc/hosts` and use `mvn verify -DargLine="-Dkarate.env=test"`. 

There are also properties for _test_ env for karate and spring. Those properties are used with Gitlab CI pipeline.

#### Unit tests with junit4, mockito and assertj

- Tests are located in `src/test/java/org/bookworm/library/services` folder

#### Integration tests based on RestAssured library

- Tests are located in `src/test/java/org/bookworm/library/controllers` folder
- REST Assured webpage: [https://rest-assured.io/](https://rest-assured.io/)

#### Integration REST API tests based on karate library

- Tests are located in `src/test/java/org/bookworm/library/karate` folder
- Karate webpage: [https://github.com/karatelabs/karate](https://github.com/karatelabs/karate)

#### Integration security tests based on spock and groovy

- Tests are located in `src/test/groovy` folder
- Spock framework webpage: [https://spockframework.org/](https://spockframework.org/)
- Groovy webpage: [https://groovy-lang.org/](https://groovy-lang.org/)

#### E2E Tests based on Testcontainers

- Tests are located in `src/test/java/org/bookworm/library/auth` folder
- Testcontainers webpage: [https://www.testcontainers.org/](https://www.testcontainers.org/)

### Liquibase 

Project use liquibase to manage database schema changes.
Schema changelog files are located in `src/main/resources/db/changelog` folder.

#### How to export database to Liquibase changelog file

Install liquibase with appropriate package manager.

	liquidbase --url=jdbc:postgresql://localhost:5432/bookworm_library --username=bookworm_user \
	--password=xyzXYZxyz --changeLogFile=output2.xml --diffTypes=data generateChangeLog

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

Command will create `/tmp/realm-bookworm.json` config file. Move file to `docker/keycloak` and `src/test/resources`.

### SonarQube call with Gradle

	./gradlew sonarqube -Dsonar.projectKey=bookworm-backend \
	-Dsonar.host.url=http://localhost:9000 -Dsonar.login=PUT-SONAR-TOKEN-HERE

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
