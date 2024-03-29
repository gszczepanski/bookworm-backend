package org.bookworm.library.controllers;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.bookworm.library.AbstractOAuth2Config;
import org.bookworm.library.utils.BookwormRole;
import org.bookworm.library.utils.IntegrationTest;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Category(IntegrationTest.class)
public class AuthorControllerTest extends AbstractOAuth2Config {

    @Value("${token.test.uri}")
    private String tokenURI;

    @Value("${app.test.uri}")
    private String testURI;

    protected String authorsPath;

    private final String AUTHOR_UUID_1 = "c0a80015-7d8c-1d2f-817d-8c2e93df2201";
    private final String AUTHOR_UUID_2 = "c0a80015-7d8c-1d2f-817d-8c2e93df2202";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        accessToken = fetchAccessToken(tokenURI, BookwormRole.EDITOR);

        RestAssured.baseURI = testURI;
        authorsPath = "/authors";
    }

    private JSONObject createPublisherData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastName", "Clarke");
        jsonObject.put("firstName", "Arthur");
        jsonObject.put("displayName", "Arthur C. Clarke");
        jsonObject.put("comment", "some comment");

        return jsonObject;
    }

    private JSONObject createPublisherDataForUpdate() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", AUTHOR_UUID_1);
        jsonObject.put("lastName", "Aldiss");
        jsonObject.put("firstName", "Brian");
        jsonObject.put("displayName", "Brian Aldiss");
        jsonObject.put("comment", "some comment");

        return jsonObject;
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void save_author_and_return_author() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createPublisherData())
                .when()
                .post(authorsPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.CREATED.value()));
        assertThat(response.jsonPath().getString("displayName"), equalTo("Arthur C. Clarke"));
        assertThat(response.jsonPath().getString("id"), notNullValue());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_author.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void update_one_author_with_put_and_return_author() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createPublisherDataForUpdate())
                .when()
                .put(authorsPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("lastName"), equalTo("Aldiss"));
        assertThat(response.jsonPath().getString("displayName"), equalTo("Brian Aldiss"));
        assertThat(response.jsonPath().getString("id"), equalTo(AUTHOR_UUID_1));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_author.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_all_authors_and_return_authors_list() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(authorsPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("totalElements"), equalTo("2"));
        List<Map<String, String>> authors = response.jsonPath().getList("content");
        assertThat(authors.get(0).get("id"), anyOf(equalTo(AUTHOR_UUID_1), equalTo(AUTHOR_UUID_2)));
        assertThat(authors.get(1).get("id"), anyOf(equalTo(AUTHOR_UUID_1), equalTo(AUTHOR_UUID_2)));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_author.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_one_author_by_id_and_return_author() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(authorsPath + "/" + AUTHOR_UUID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("displayName"), equalTo("Brian Aldiss"));
        assertThat(response.jsonPath().getString("id"), equalTo(AUTHOR_UUID_1));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_author.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void delete_one_author_by_id_and_return_ok() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .delete(authorsPath + "/" + AUTHOR_UUID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
    }
}
