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
public class BookControllerTest extends AbstractOAuth2Config {

    @Value("${token.test.uri}")
    private String tokenURI;

    @Value("${app.test.uri}")
    private String testURI;

    protected String booksPath;

    private final Integer PUBLISHER_ID = 10;
    private final String BOOK_UUID_1 = "c0a80015-7d8c-1d2f-817d-8c2e93df2200";
    private final String BOOK_UUID_2 = "c0a80015-7d8c-1d2f-817d-8c2e93df2210";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        accessToken = fetchAccessToken(tokenURI, BookwormRole.EDITOR);

        RestAssured.baseURI = testURI;
        booksPath = "/books";
    }

    private JSONObject createBookData(Integer publisherId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("registryNumber", "1");
        jsonObject.put("title", "Red Mars");
        jsonObject.put("placeOfOrigin", "Warsaw");
        jsonObject.put("year", "2002");
        jsonObject.put("volume", "1");
        jsonObject.put("acquireDate", "2000-01-02");
        jsonObject.put("acquiringMethod", "PURCHASED");
        jsonObject.put("acquiringEmployeeId", "28319c80-449d-11ec-81d3-0242ac130003");
        jsonObject.put("status", "AVAILABLE");
        jsonObject.put("publisherId", publisherId.toString());
        jsonObject.put("language", "ENGLISH");
        jsonObject.put("jointPublication", "false");

        return jsonObject;
    }

    private JSONObject createBookDataForUpdate(Integer publisherId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", BOOK_UUID_1);
        jsonObject.put("registryNumber", "1");
        jsonObject.put("title", "Green Mars"); //changed
        jsonObject.put("placeOfOrigin", "Warsaw");
        jsonObject.put("year", "2002");
        jsonObject.put("volume", "1");
        jsonObject.put("acquireDate", "2000-01-02");
        jsonObject.put("acquiringMethod", "PURCHASED");
        jsonObject.put("acquiringEmployeeId", "28319c80-449d-11ec-81d3-0242ac130003");
        jsonObject.put("status", "AVAILABLE");
        jsonObject.put("publisherId", publisherId.toString());
        jsonObject.put("language", "ENGLISH");
        jsonObject.put("jointPublication", "false");

        return jsonObject;
    }

    private JSONObject createBookDataForUpdatePart(Integer publisherId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", BOOK_UUID_1);
        jsonObject.put("title", "Blue Mars"); //changed

        return jsonObject;
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_publisher.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void save_book_and_return_book() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createBookData(PUBLISHER_ID))
                .when()
                .post(booksPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.CREATED.value()));
        assertThat(response.jsonPath().getString("title"), equalTo("Red Mars"));
        assertThat(response.jsonPath().getString("id"), notNullValue());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_book.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void update_one_book_with_put_and_return_book() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createBookDataForUpdate(PUBLISHER_ID))
                .when()
                .put(booksPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("title"), equalTo("Green Mars"));
        assertThat(response.jsonPath().getString("year"), equalTo("2002"));
        assertThat(response.jsonPath().getString("id"), notNullValue());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_book.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void update_one_book_with_patch_and_return_book() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createBookDataForUpdatePart(PUBLISHER_ID))
                .when()
                .patch(booksPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("title"), equalTo("Blue Mars"));
        assertThat(response.jsonPath().getString("year"), equalTo("2002"));
        assertThat(response.jsonPath().getString("id"), notNullValue());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_book.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_all_books_and_return_books_list() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(booksPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("totalElements"), equalTo("2"));
        List<Map<String, String>> books = response.jsonPath().getList("content");
        assertThat(books.get(0).get("id"), anyOf(equalTo(BOOK_UUID_1), equalTo(BOOK_UUID_2)));
        assertThat(books.get(1).get("id"), anyOf(equalTo(BOOK_UUID_1), equalTo(BOOK_UUID_2)));
    }

    @Test()
    @SqlGroup({
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void find_all_books_and_return_not_found() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(booksPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_book.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_one_book_by_id_and_return_book() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(booksPath + "/" + BOOK_UUID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("title"), equalTo("Red Mars"));
        assertThat(response.jsonPath().getString("id"), equalTo(BOOK_UUID_1));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_book.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void delete_one_book_by_id_and_return_ok() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .delete(booksPath + "/" + BOOK_UUID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
    }
}
