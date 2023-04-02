package user;

import base.UserApi;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class UpdateUserInfoTest {

    private User user;
    private User userUpdated;
    private UserApi userApi;

    public UpdateUserInfoTest(User updated) {
        this.userUpdated = updated;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new User("test3003_upd@ya.ru", "test", "TestUser")},
                {new User("test3003@ya.ru", "test_upd", "TestUser")},
                {new User("test3003@ya.ru", "test", "TestUser_upd")}
        };
    }

    @Before
    public void setUp() {
        userApi = new UserApi();
        user = new User("test3003@ya.ru", "test", "TestUser");
    }

    @Test
    @DisplayName("Check successful update user")
    public void testUpdateUserInfo() {
        userApi.createUser(user);

        userApi.updateUserInfo(user, userUpdated)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        userApi.deleteUser(userUpdated);
    }
}
