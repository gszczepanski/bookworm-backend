package org.bookworm.library.controllers;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.bookworm.library.AbstractOAuth2Config;
import org.bookworm.library.utils.BookwormRole;
import org.bookworm.library.utils.IntegrationTest;
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
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Category(IntegrationTest.class)
public class PublisherControllerTest extends AbstractOAuth2Config {

    @Value("${token.test.uri}")
    private String tokenURI;

    @Value("${app.test.uri}")
    private String testURI;

    protected String publisherPath;

    private final Integer PUBLISHER_ID_1 = 10;
    private final Integer PUBLISHER_ID_2 = 5;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        accessToken = fetchAccessToken(tokenURI, BookwormRole.EDITOR);

        RestAssured.baseURI = testURI;
        publisherPath = "/publishers";
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void save_publisher_and_return_publisher() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body("{ \"name\": \"Garden\" }")
                .when()
                .post(publisherPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.CREATED.value()));
        assertThat(response.jsonPath().getString("name"), equalTo("Garden"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_publisher.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void update_one_publisher_with_put_and_return_publisher() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body("{ \"name\": \"Market\", \"id\": \"" + PUBLISHER_ID_1 + "\" }")
                .when()
                .put(publisherPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("name"), equalTo("Market"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_publisher.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_all_publishers_and_return_publishers_list() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(publisherPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("totalElements"), equalTo("2"));
        List<Map<String, Integer>> publishers = response.jsonPath().getList("content");
        assertThat(publishers.get(0).get("id"), anyOf(equalTo(PUBLISHER_ID_1), equalTo(PUBLISHER_ID_2)));
        assertThat(publishers.get(1).get("id"), anyOf(equalTo(PUBLISHER_ID_1), equalTo(PUBLISHER_ID_2)));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_publisher.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_one_publisher_by_id_and_return_publisher() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(publisherPath + "/" + PUBLISHER_ID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("name"), equalTo("Roma Publishing"));
        assertThat(response.jsonPath().getString("id"), equalTo(PUBLISHER_ID_1.toString()));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_publisher.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void delete_one_publisher_by_id_and_return_ok() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .delete(publisherPath + "/" + PUBLISHER_ID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
    }
}
