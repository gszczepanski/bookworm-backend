package org.bookworm.library;

import org.bookworm.library.utils.BookwormRole;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class AbstractOAuth2Config {

    protected String accessToken;

    protected String fetchAccessToken(String tokenURI, BookwormRole role) {

        String username = role.equals(BookwormRole.EDITOR) ? "bookworm_john" : "bookworm_jill";

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
                        .post(tokenURI)
                        .prettyPeek()
                        .then();

        response.statusCode(200);

        return response.extract().body().jsonPath().getString("access_token");
    }
}
