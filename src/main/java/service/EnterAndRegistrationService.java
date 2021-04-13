package service;

import external.dao.CurrencyAccountDao;
import external.dao.UserAccountDao;
import external.dao.UserDao;
import external.entity.User;
import external.entity.UserAccount;
import view.Menu;

import javax.persistence.NoResultException;


public class EnterAndRegistrationService {
    private final UserAccountDao userAccountDao = new UserAccountDao();
    private final InputDataService inputDataService = new InputDataService();
    private final Menu menu = new Menu();
    private UserAccount userAccount = null;

    public void start() {
        menu.doYouHaveAccountInOurBank();
        UserAccountService userAccountService;
        if (inputDataService.yesOrNo()) {
            if(checkingIfAUserExists() && checkingPassword()){
               userAccountService = new UserAccountService(userAccount, inputDataService, menu);
                userAccountService.userAccountMenu();
            }
        } else {
            menu.doYourWontRegister();
            if (inputDataService.yesOrNo()) {
                saveUserAccountToDBAfterRegistration();
                userAccountService = new UserAccountService(userAccount, inputDataService, menu);
                userAccountService.userAccountMenu();
            } else {
                System.out.println("Bye");
            }
        }
    }

    private boolean checkingIfAUserExists() {
        try {
            menu.enterLogin();
            userAccount = userAccountDao.findByUserLogin(inputDataService.getLogin());
            return true;
        } catch (NoResultException e) {
            System.out.println("\nThe user with such login does not exist, check the correctness of the entered data\n");
        }
        return checkingIfAUserExists();
    }

    private boolean checkingPassword() {
        menu.enterPassword();
        String password = inputDataService.getPassword(userAccount.getUser().getUserLogin());
        if (userAccount.getUser().getUserPassword().equals(password)) {
            return true;
        } else
            System.out.println("\nInvalid password\n");
        return checkingPassword();
    }

    void saveUserAccountToDBAfterRegistration() {
       userAccount = createUserAccountDuringRegistration();

        new UserDao().saveOrUpdate(userAccount.getUser());

        userAccountDao.saveOrUpdate(userAccount);

        new CurrencyAccountDao().saveOrUpdate(userAccount.getCurrencyAccounts().get(0));
    }

    private UserAccount createUserAccountDuringRegistration() {
        System.out.println("\nRegistration menu:");

        menu.enterLogin();
        String login = inputDataService.getLogin();

        menu.enterFirstName();
        String firstName = inputDataService.getFirstLastName();

        menu.enterLastName();
        String lastName = inputDataService.getFirstLastName();

        menu.enterPhoneNumber();
        String phoneNumber = inputDataService.getPhoneNumber();

        menu.enterEmail();
        String email = inputDataService.getEmail();

        menu.enterPassword();
        String password = inputDataService.getPassword(login);

        UserAccount userAccount = new UserAccount(firstName, lastName, phoneNumber, email,
                new User(login, password));

        userAccount.getCurrencyAccounts().add(CurrencyAccountService.getPolishZlotyAccount(userAccount));

        return userAccount;
    }

}
