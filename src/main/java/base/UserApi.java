package base;

import io.restassured.response.ValidatableResponse;
import model.User;

import static io.restassured.RestAssured.given;

public class UserApi extends StellarRestClient {

    private static final String USER_URI = BASE_URI + "auth/";

    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getReqSpec())
                .body(user)
                .post(USER_URI + "register")
                .then();
    }

    public ValidatableResponse login(User user) {
        return given()
                .spec(getReqSpec())
                .body(user)
                .post(USER_URI + "login")
                .then();
    }

    public String getToken(User user) {
        return login(user).extract().path("accessToken");
    }

    public ValidatableResponse updateUserInfo(User user, User updated) {
        return given()
                .spec(getAuthReqSpec(getToken(user)))
                .body(updated)
                .patch(USER_URI + "user")
                .then();
    }

    public ValidatableResponse invalidUpdate(User updated) {
        return given()
                .spec(getReqSpec())
                .body(updated)
                .patch(USER_URI + "user")
                .then();
    }

    public ValidatableResponse deleteUser(User user) {
        return given()
                .spec(getAuthReqSpec(getToken(user)))
                .body(user)
                .delete(USER_URI + "user")
                .then();
    }
}
