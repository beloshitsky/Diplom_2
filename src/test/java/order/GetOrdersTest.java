package order;

import base.OrdersApi;
import base.UserApi;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {

    private OrdersApi ordersApi;
    private UserApi userApi;
    private User user;

    @Before
    public void setUp() {
        ordersApi = new OrdersApi();
        userApi = new UserApi();

        user = new User("test3003@ya.ru", "test", "TestUser");
        userApi.createUser(user);
    }

    @Test
    public void testGetOrdersWithoutAuthorization() {
        ordersApi.getOrders()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    public void testGetUserOrders() {
        String token = userApi.login(user).extract().path("accessToken");

        ordersApi.getOrders(token)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        userApi.deleteUser(user);
    }
}
