package user;

import base.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserWithInvalidDataTest {

    private User user;
    private UserApi userApi;

    public CreateUserWithInvalidDataTest(User user) {
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new User("test3003@ya.ru", null, "TestUser")},
                {new User(null, "test", "TestUser")},
                {new User("test3003@ya.ru", null, null)},
        };
    }

    @Before
    public void setUp() {
        userApi = new UserApi();
    }

    @Test
    @DisplayName("Check create user without data")
    public void testCreateUserWithoutData() {
        userApi.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
