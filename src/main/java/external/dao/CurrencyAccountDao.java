package external.dao;

import external.connection.HibernateUtil;
import external.entity.CurrencyAccount;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


@Log4j
public class CurrencyAccountDao {

    public CurrencyAccount findByAccountNumber(Integer number) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            CurrencyAccount currentAccount = (CurrencyAccount) session
                    .createQuery("FROM CurrencyAccount WHERE accountNumber = :number")
                    .setParameter("number", number)
                    .getSingleResult();

            transaction.commit();

            return currentAccount;
        } catch (HibernateException e){
            if (transaction != null)
                transaction.rollback();

            log.error(e.getMessage(), e);
        }

        return null;
    }

    public List <CurrencyAccount> findAllById(Integer id) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            List<CurrencyAccount> currentAccount = session
                    .createQuery("FROM CurrencyAccount WHERE userAccount.id = :id")
                    .setParameter("id", id)
                    .getResultList();

            transaction.commit();

            return currentAccount;
        } catch (HibernateException e){
            if (transaction != null)
                transaction.rollback();

            log.error(e.getMessage(), e);
        }

        return null;
    }
    public void saveOrUpdate(CurrencyAccount currentAccount){
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(currentAccount);
            transaction.commit();
        } catch(HibernateException hibernateException){
            log.error(hibernateException.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(CurrencyAccount currentAccount) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(currentAccount);
            transaction.commit();
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
