package external.dao;

import external.entity.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;

public class UserDaoTest {
    UserDao userDao;
    User user;

    @BeforeMethod
    void setUp() {
        userDao = new UserDao();
        user = new User("Test", "Test");
    }

    @Test(dependsOnMethods="shouldSaveOrUpdate")
    void shouldFindByUserLogin() {
        User user = userDao.findByUserLogin("Test");
        Assert.assertNotNull(user);
    }

    @Test
    void shouldSaveOrUpdate() {
        userDao.saveOrUpdate(user);
    }

    @Test(dependsOnMethods = "shouldFindByUserLogin", expectedExceptions = NoResultException.class)
    void shouldDelete() {
        userDao.delete(userDao.findByUserLogin("Test"));
        Assert.assertNull(userDao.findByUserLogin("Test"));
    }
}