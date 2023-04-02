package order;

import base.OrdersApi;
import base.UserApi;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

public class CreateOrderTest {

    private OrdersApi ordersApi;
    private UserApi userApi;
    private String ingredient;
    private List<String> ingredients;

    @Before
    public void setUp() {
        ordersApi = new OrdersApi();
        userApi = new UserApi();

        ingredients = ordersApi.getIngredients();
        ingredient = String.format("{\"ingredients\": [\"%s\"]}", ingredients.get(0));
    }

    @Test
    public void testCreateOrderWithIngredients() {
        ordersApi.createOrder(String
                .format("{\"ingredients\": [\"%s\",\"%s\"]}",
                        ingredients.get(0), ingredients.get(1)))
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    public void testCreateOrderWithoutIngredients() {
        ordersApi.createOrder()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void testCreateOrderWithInvalidIngredients() {
        String invalidIngredients = "{\"ingredients\": [\"some invalid hash\"]}";

        ordersApi.createOrder(invalidIngredients)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);

    }

    @Test
    public void testCreateOrderWithoutAuthorization() {
        ordersApi.createOrder(ingredient)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    public void testCreateOrderWithUserLogin() {
        User user = new User("test3003@ya.ru", "test", "TestUser");
        userApi.createUser(user);

        String token = userApi.getToken(user);

        ordersApi.createOrder(ingredient)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));

        assertNotNull(ordersApi.getOrders(token));

        userApi.deleteUser(user);
    }
}
