package org.bookworm.library.auth;

import com.auth0.jwk.JwkProvider;
import org.bookworm.library.security.KeycloakJwkProvider;
import org.bookworm.library.utils.BookwormRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Based on: https://github.com/wkrzywiec/keycloak-security-example/blob/main/backend/src/test/java/io/wkrzywiec/keycloak/backend/movie/MovieControllerSecurityE2ETest.java
 */
@SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SqlGroup({
        @Sql(scripts = "classpath:test_data_4_book.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
})
public class BookControllerSecurityE2ETest {

    @Container
    private static GenericContainer keycloak = new GenericContainer(DockerImageName.parse("jboss/keycloak:15.1.0"))
            .withExposedPorts(8080)
            .withEnv("KEYCLOAK_USER", "admin")
            .withEnv("KEYCLOAK_PASSWORD", "admin")
            .withEnv("DB_VENDOR", "h2")
            .withEnv("KEYCLOAK_IMPORT", "/tmp/realm-bookworm.json")
            .withClasspathResourceMapping("realm-bookworm.json", "/tmp/realm-bookworm.json", BindMode.READ_ONLY)
            .withCommand("-Dkeycloak.profile.feature.upload_scripts=enabled")
            .waitingFor(Wait.forHttp("/auth/realms/master"));

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Try to get all books (request without Authorization header)")
    void requestAllBooksWithoutAuthorizationHeader() throws Exception {

        mockMvc.perform(
                        get("/books"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Get all books (request with Authorization header)")
    void getAllBooksWithAuthorizationHeader() throws Exception {

        String accessToken = fetchAccessToken(BookwormRole.EDITOR);

        mockMvc.perform(
                        get("/books")
                                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get a single book (request with Authorization header)")
    void getSingleBookWithAuthorizationHeader() throws Exception {

        String accessToken = fetchAccessToken(BookwormRole.EDITOR);

        mockMvc.perform(
                        get("/books/c0a80015-7d8c-1d2f-817d-8c2e93df2200")
                                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Try to get a single book having wrong role (request with Authorization header)")
    void getSingleHavingIncorrectUserRole() throws Exception {

        String accessToken = fetchAccessToken(BookwormRole.EMPTY);

        mockMvc.perform(
                        get("/books")
                                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    private String fetchAccessToken(BookwormRole role) {

        String username = role.equals(BookwormRole.EDITOR) ? "bookworm_john" : "bookworm_jill";

        String keycloakUrl = "http://" + keycloak.getHost() + ":" + keycloak.getMappedPort(8080) + "/auth/realms/bookworm";

        Map<String, String> formParams = Map.of(
                "grant_type", "password",
                "scope", "openid",
                "client_id", "bookworm_client",
                "client_secret", "a0f7590c-a779-4695-95cc-9d608142864b",
                "username", username,
                "password", "password"
        );

        var response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .accept("application/json, text/plain, */*")
                        .formParams(formParams)
                        .log().all()
                        .when()
                        .post(keycloakUrl + "/protocol/openid-connect/token")
                        .prettyPeek()
                        .then();

        response.statusCode(200);

        return response.extract().body().jsonPath().getString("access_token");
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration {

        @Bean
        @Primary
        public JwkProvider keycloakJwkProvider() {
            String jwkUrl = "http://" + keycloak.getHost() + ":" + keycloak.getMappedPort(8080) + "/auth/realms/bookworm" + "/protocol/openid-connect/certs";
            return new KeycloakJwkProvider(jwkUrl);
        }
    }
}
