package external.entity;

import external.dao.UserAccountDao;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.*;

public class UserAccountTest {

    UserAccountDao userAccountDao;

    @BeforeMethod
    public void setUp() {
        userAccountDao = new UserAccountDao();
    }
}