package external.dao;

import external.client.NbpRate;
import external.client.NbpTable;
import external.connection.HibernateUtil;
import external.entity.ExchangeRate;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;

@Log4j
public class ExchangeRateDao {

    public ExchangeRate findByCurrencyAndDate(String currency, LocalDate localDate) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            ExchangeRate exchangeRate = (ExchangeRate) session
                    .createQuery("FROM ExchangeRate WHERE currency = :currency AND rateDate = : localDate")
                    .setParameter("currency", currency)
                    .setParameter("localDate", localDate)
                    .getSingleResult();

            transaction.commit();

            return exchangeRate;
        } catch (HibernateException e){
            if (transaction != null)
                transaction.rollback();

            log.error(e.getMessage(), e);
        }

        return null;
    }

    public ExchangeRate findByCodeAndDate(String code, LocalDate localDate) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            ExchangeRate exchangeRate = (ExchangeRate) session
                    .createQuery("FROM ExchangeRate WHERE code = :code AND rateDate = : localDate")
                    .setParameter("code", code)
                    .setParameter("localDate", localDate)
                    .getSingleResult();

            transaction.commit();

            return exchangeRate;
        } catch (HibernateException e){
            if (transaction != null)
                transaction.rollback();

            log.error(e.getMessage(), e);
        }

        return null;
    }

    public void save(NbpTable nbpTable) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            for (NbpRate rate : nbpTable.getRates()) {
                ExchangeRate exchangeRate = new ExchangeRate(
                  rate.getCurrency(),
                  rate.getCode(),
                  rate.getAsk(),
                  rate.getBid(),
                  nbpTable.getEffectiveDate()
                );
                session.save(exchangeRate);
            }

            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            log.error(e.getMessage(), e);
        }
    }
}
