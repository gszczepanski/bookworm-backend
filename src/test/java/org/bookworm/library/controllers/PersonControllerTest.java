package org.bookworm.library.controllers;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.bookworm.library.AbstractOAuth2Config;
import org.bookworm.library.entities.PersonType;
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
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Category(IntegrationTest.class)
public class PersonControllerTest extends AbstractOAuth2Config {

    @Value("${token.test.uri}")
    private String tokenURI;

    @Value("${app.test.uri}")
    private String testURI;

    protected String personsPath;

    private final String PERSON_UUID_1 = "c0a80015-7d8c-1d2f-817d-8c2e93df2202";
    private final String PERSON_UUID_2 = "c0a80015-7d8c-1d2f-817d-8c2e93df2212";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        accessToken = fetchAccessToken(tokenURI, BookwormRole.EDITOR);

        RestAssured.baseURI = testURI;
        personsPath = "/persons";
    }

    private JSONObject createPersonData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastName", "Reagan");
        jsonObject.put("middleName", "Wilson");
        jsonObject.put("firstName", "Ronald");
        jsonObject.put("idCardNumber", "123ABCDE4");
        jsonObject.put("idCardType", 1);
        jsonObject.put("type", 1);

        return jsonObject;
    }

    private JSONObject createPersonDataForUpdate() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", PERSON_UUID_1);
        jsonObject.put("lastName", "Wilson");
        jsonObject.put("middleName", null);
        jsonObject.put("firstName", "Woodrow");
        jsonObject.put("idCardNumber", "345ASD8");
        jsonObject.put("idCardType", 1);
        jsonObject.put("type", 1);

        return jsonObject;
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void save_person_and_return_person() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createPersonData())
                .when()
                .post(personsPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.CREATED.value()));
        assertThat(response.jsonPath().getString("lastName"), equalTo("Reagan"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_person.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void update_one_person_with_put_and_return_person() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createPersonDataForUpdate())
                .when()
                .put(personsPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("lastName"), equalTo("Wilson"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_person.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_all_persons_and_return_persons_list() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(personsPath)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("totalElements"), equalTo("2"));
        List<Map<String, String>> persons = response.jsonPath().getList("content");
        assertThat(persons.get(0).get("id"), anyOf(equalTo(PERSON_UUID_1), equalTo(PERSON_UUID_2)));
        assertThat(persons.get(1).get("id"), anyOf(equalTo(PERSON_UUID_1), equalTo(PERSON_UUID_2)));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_person.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void find_one_person_by_id_and_return_person() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(personsPath + "/" + PERSON_UUID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("firstName"), equalTo("Ronald"));
        assertThat(response.jsonPath().getString("lastName"), equalTo("Reagan"));
        assertThat(response.jsonPath().getString("id"), equalTo(PERSON_UUID_1));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_person.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void delete_one_person_by_id_and_return_ok() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .delete(personsPath + "/" + PERSON_UUID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test_data_4_person.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:make_tables_empty.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void set_one_person_type_and_return_ok_and_type() {
        MockMvcResponse response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .body(createPersonDataForUpdate())
                .when()
                .patch(personsPath + "/" + PERSON_UUID_1 + "/type/" + PersonType.DEPUTY_MANAGER)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));

        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE)
                .when()
                .get(personsPath + "/" + PERSON_UUID_1)
                .prettyPeek()
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), equalTo(HttpStatus.OK.value()));
        assertThat(response.jsonPath().getString("type"), equalTo(PersonType.DEPUTY_MANAGER.toString()));
    }
}
