package external.dao;

import external.connection.HibernateUtil;
import external.entity.UserAccount;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Log4j
public class UserAccountDao {

    public UserAccount findByUserLogin(String login) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            UserAccount userAccount = (UserAccount) session.createQuery("FROM UserAccount WHERE user.userLogin = :userLogin").
                    setParameter("userLogin", login).getSingleResult();

            transaction.commit();

            return userAccount;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            log.error(e.getMessage(), e);
        }

        return null;
    }

    public void saveOrUpdate(UserAccount userAccount){
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            session.saveOrUpdate(userAccount);

            transaction.commit();
        } catch(HibernateException hibernateException){
            log.error(hibernateException.getMessage());
            if (transaction != null) {
                transaction.rollback();

                log.error(hibernateException.getMessage(), hibernateException);
            }
        }
    }

    public void delete(UserAccount userAccount) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(userAccount);

            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (transaction != null)
                transaction.rollback();

            log.error(hibernateException.getMessage(), hibernateException);
        }
    }
}
