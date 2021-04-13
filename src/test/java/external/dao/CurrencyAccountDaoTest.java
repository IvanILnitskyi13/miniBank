package external.dao;

import external.entity.CurrencyAccount;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;


public class CurrencyAccountDaoTest {

    private CurrencyAccountDao currencyAccountDao;
    private CurrencyAccount currencyAccount;

    @BeforeMethod
    void setUp() {
      /*  currencyAccount = new CurrencyAccount(
                1111,
                "United State dolar",
                "USD",
                200.0);*/
        currencyAccountDao = new CurrencyAccountDao();
    }

    @Test(dependsOnMethods = "shouldSaveOrUpdate")
    void shouldFindById() {
        //Assert.assertNotNull(currencyAccountDao.findById(1));
    }

    @Test
    void shouldSaveOrUpdate() {
        currencyAccountDao.saveOrUpdate(currencyAccount);
      //  Assert.assertNotNull(currencyAccountDao.findById(1));

    }

    @Test(dependsOnMethods="shouldFindById", expectedExceptions = NoResultException.class)
    void shouldDelete() {
        currencyAccountDao.delete(currencyAccount);
       // Assert.assertNull(currencyAccountDao.findById(1));
    }
}