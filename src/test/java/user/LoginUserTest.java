package user;

import base.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    private User user;
    private UserApi userApi;

    @Before
    public void setUp() {
        userApi = new UserApi();
        user = new User("test3003@ya.ru", "test", "TestUser");

        userApi.createUser(user);
    }

    @Test
    @DisplayName("Check login user")
    public void testLoginUser() {
        userApi.login(user)
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
