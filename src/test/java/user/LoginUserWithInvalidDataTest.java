package user;

import base.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginUserWithInvalidDataTest {

    private User user;
    private User userCreated;
    private static UserApi userApi;

    public LoginUserWithInvalidDataTest(User user) {
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new User("test3003@ya.ru", "invalid", "TestUser")},
                {new User("test@ya.ru", "test", "TestUser")},
        };
    }

    @Before
    public void setUp() {
        userApi = new UserApi();
        userCreated = new User("test3003@ya.ru", "test", "TestUser");

        userApi.createUser(userCreated);
    }


    @Test
    @DisplayName("Check login user with invalid data")
    public void testLoginUser() {
        userApi.login(user)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void tearDown() {
        userApi.deleteUser(userCreated);
    }
}
