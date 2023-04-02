package user;

import base.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    private User user;
    private UserApi userApi;

    @Before
    public void setUp() {
        userApi = new UserApi();
        user = new User("test3003@ya.ru", "test", "TestUser");
    }

    @Test
    @DisplayName("Check create user")
    public void testCreateUser() {
        userApi.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Check create user which already exists")
    public void testCreateUserAlreadyExist() {
        userApi.createUser(user)
                .assertThat()
                .statusCode(SC_OK);

        User newUser = user;

        userApi.createUser(newUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("User already exists"));
    }

    @After
    public void tearDown() {
       userApi.deleteUser(user);
    }
}
