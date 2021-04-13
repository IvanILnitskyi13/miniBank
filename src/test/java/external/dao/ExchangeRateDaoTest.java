package external.dao;

import external.client.NbpRate;
import external.client.NbpTable;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExchangeRateDaoTest {

    private ExchangeRateDao exchangeRateDao;
    private NbpTable nbpTable;

    @BeforeMethod
    public void setUp() {
        exchangeRateDao = new ExchangeRateDao();
        nbpTable = new NbpTable(LocalDate.parse("2021-03-19"),
                new ArrayList<>() {{
                    add(new NbpRate("euro", "EUR", 4.5774, 4.6698));
                }});
    }

    @Test
    public void shouldSaveRates() {
        exchangeRateDao.save(nbpTable);
        Assert.assertNotNull(exchangeRateDao.findByCodeAndDate("EUR", LocalDate.parse("2021-03-19")));
    }

    @Test(dependsOnMethods = "shouldSaveRates")
    public void shouldFindByCurrencyAndDate() {
        Assert.assertNotNull(exchangeRateDao.findByCurrencyAndDate("euro", LocalDate.parse("2021-03-19")));
    }


}