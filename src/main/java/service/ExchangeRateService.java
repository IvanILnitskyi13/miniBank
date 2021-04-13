package service;



import external.client.NbpApiClient;
import external.client.NbpTable;
import external.dao.ExchangeRateDao;
import external.entity.ExchangeRate;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ExchangeRateService {
    private final ExchangeRateDao exchangeRateDao;
    private final NbpApiClient nbpApiClient;

    public ExchangeRateService(ExchangeRateDao exchangeRateDao, NbpApiClient nbpApiClient) {
        this.exchangeRateDao = exchangeRateDao;
        this.nbpApiClient = nbpApiClient;
    }

    public Double exchangeMoneyAsk(Double amount, String code) {
        try{
            ExchangeRate rate = exchangeRateDao.findByCodeAndDate(code, weekdays());
            return new BigDecimal(amount / rate.getAsk()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } catch(NoResultException e) {
            saveNbpTableToDB();
            ExchangeRate newRate = exchangeRateDao.findByCodeAndDate(code, weekdays());
            return new BigDecimal(amount / newRate.getAsk()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
    }

    public Double exchangeMoneyBid(Double amount, String code) {
        try{
            ExchangeRate rate = exchangeRateDao.findByCodeAndDate(code, weekdays());
            return  new BigDecimal(amount * rate.getBid()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } catch(NoResultException e) {
            saveNbpTableToDB();
            ExchangeRate newRate = exchangeRateDao.findByCodeAndDate(code, weekdays());
            return new BigDecimal(amount / newRate.getBid()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
    }


   private void saveNbpTableToDB(){
       NbpTable tableForADay = nbpApiClient.getNbpTableForDay(weekdays());
       exchangeRateDao.save(tableForADay);
   }

   private LocalDate weekdays() {
        LocalDate ld = LocalDate.now();
        int dayOfWeekValue = ld.getDayOfWeek().getValue();

        if (dayOfWeekValue == 6) {
            return ld.minusDays(1);
        } else if (dayOfWeekValue == 7) {
            return ld.minusDays(2);
        } else return ld;
    }
}
