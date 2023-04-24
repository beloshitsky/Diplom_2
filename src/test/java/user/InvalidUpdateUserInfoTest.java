package user;

import base.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class InvalidUpdateUserInfoTest {

    private User user;
    private User userWithExistingEmail;
    private User userUpdated;
    private static UserApi userApi;

    @Before
    public void setUp() {
        userApi = new UserApi();

        user = new User("test3003@ya.ru", "test", "TestUser");
        userWithExistingEmail = new User("test3003new@ya.ru", "test", "TestUser");
        userUpdated = new User(userWithExistingEmail.getEmail(), "test_upd", null);

        userApi.createUser(user);
        userApi.createUser(userWithExistingEmail);
    }

    @Test
    @DisplayName("Check update user without authorization")
    public void testUpdateUserInfoWithoutAuthorization() {
        userApi.invalidUpdate(userUpdated)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Check update user without data")
    public void testUpdateUserInfoWithInvalidData() {
        userApi.updateUserInfo(user, userUpdated)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("User with such email already exists"));
    }

    @After
    public void tearDown() {
        userApi.deleteUser(user);
        userApi.deleteUser(userWithExistingEmail);
    }
}
