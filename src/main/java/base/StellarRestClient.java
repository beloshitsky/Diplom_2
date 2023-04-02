package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class StellarRestClient {

    protected static final String BASE_URI = "https://stellarburgers.nomoreparties.site/api/";

    protected RequestSpecification getReqSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }

    protected RequestSpecification getAuthReqSpec(String token) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }
}
