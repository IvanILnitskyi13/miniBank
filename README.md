**miniBank**

The program simulates the operation of the bank.
You can create user account and open currency account, pay to  you 
currency account and transfer money to another currency account.
if you transfer from  currency account to foreign currency account,
the value of the transferred costs is converted in accordance 
with the current NBP exchange rate.

1. Create a database
- src/main/resources/dataBase.sql
    
2. Configure database access in hibernate.cfg.xml
- src/main/resources/hibernate.cfg.xml
    
3. Start program
 - src/main/java/Main.java