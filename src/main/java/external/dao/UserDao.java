package external.dao;

import external.connection.HibernateUtil;
import external.entity.User;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Log4j
public class UserDao {

    public User findByUserLogin(String userLogin) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = (User) session
                    .createQuery("FROM User WHERE userLogin = :login")
                    .setParameter("login", userLogin)
                    .getSingleResult();

            transaction.commit();

            return user;
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            if (transaction != null) {
                transaction.rollback();
            }

        }

        return null;
    }

    public void saveOrUpdate(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(user);

            transaction.commit();
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    public void delete(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(user);

            transaction.commit();

        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
