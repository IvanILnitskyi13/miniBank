package external.dao;

import external.entity.TransactionLog;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;


public class TransactionLogDaoTest {

    private TransactionLogDao transactionLogDao;
    private TransactionLog transactionLog;
   // private List<TransactionLog> transactionLogList;

    @BeforeMethod
    public void setUp() {
        transactionLogDao = new TransactionLogDao();
//        transactionLog = new TransactionLog(
//                LocalDate.parse("2021-03-19"),
//                11111,
//                50.0,
//                100.0);
//    }

//    @Test(dependsOnMethods = "shouldSave")
//    void shouldFindAllByAccountId() {
//      //  transactionLogList = transactionLogDao.findAllByAccountId(1);
//      //  Assert.assertNotNull(transactionLogList);
//    }

//    @Test
//    void shouldSave() {
//        transactionLogDao.save(transactionLog);
//        transactionLogDao.save(transactionLog);
//    }

   // @Test(dependsOnMethods = "shouldFindAllByAccountId", expectedExceptions = NoResultException.class)
  //  void shouldDeleteAll() {
   //     transactionLogDao.deleteAll(transactionLogList);
   }
}