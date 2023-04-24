package base;

import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrdersApi extends StellarRestClient {

    private static final String ORDERS_URI = BASE_URI + "orders";
    private static final String INGREDIENTS_URI = BASE_URI + "ingredients";

    public ValidatableResponse getOrders() {
        return given()
                .get(ORDERS_URI)
                .then();
    }

    public ValidatableResponse getOrders(String token) {
        return given()
                .header("Authorization", token)
                .get(ORDERS_URI)
                .then();
    }

    public List<String> getIngredients() {
        return given()
                .get(INGREDIENTS_URI)
                .then()
                .extract().path("data._id");
    }

    public ValidatableResponse createOrder() {
        return given()
                .spec(getReqSpec())
                .post(ORDERS_URI)
                .then();
    }

    public ValidatableResponse createOrder(String ingredients) {
        return given()
                .spec(getReqSpec())
                .body(ingredients)
                .post(ORDERS_URI)
                .then();
    }
}
