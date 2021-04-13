package external.dao;

import external.connection.HibernateUtil;
import external.entity.CurrencyAccount;
import external.entity.TransactionLog;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

@Log4j
public class TransactionLogDao {


    public TransactionLog findByAccountId(Integer id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            TransactionLog transactionLog = (TransactionLog) session
                    .createQuery("FROM TransactionLog WHERE currencyAccount.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();
            return transactionLog;
        } catch (HibernateException e) {
            log.error(e.getMessage(), e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    public void save(TransactionLog transactionLog) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

                session.save(transactionLog);

            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            log.error(e.getMessage(), e);
        }
    }

    public void deleteAll(List<TransactionLog> transactionLog) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (TransactionLog element : transactionLog) {
                session.delete(element);
            }
            transaction.commit();
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
