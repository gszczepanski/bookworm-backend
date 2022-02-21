package org.bookworm.library.auth;

import org.bookworm.library.AbstractOAuth2Config;
import org.bookworm.library.utils.BookwormRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

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
@SqlGroup({
        @Sql(scripts = "classpath:test_data_4_book.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
})
public class BookControllerSecurityTest extends AbstractOAuth2Config {

    @Value("${token.test.uri}")
    private String tokenURI;

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

        String accessToken = fetchAccessToken(tokenURI, BookwormRole.EDITOR);

        mockMvc.perform(
                        get("/books")
                                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get a single book (request with Authorization header)")
    void getSingleBookWithAuthorizationHeader() throws Exception {

        String accessToken = fetchAccessToken(tokenURI, BookwormRole.EDITOR);

        mockMvc.perform(
                        get("/books/c0a80015-7d8c-1d2f-817d-8c2e93df2200")
                                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Try to get a single book having wrong role (request with Authorization header)")
    void getSingleHavingIncorrectUserRole() throws Exception {

        String accessToken = fetchAccessToken(tokenURI, BookwormRole.EMPTY);

        mockMvc.perform(
                        get("/books")
                                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
