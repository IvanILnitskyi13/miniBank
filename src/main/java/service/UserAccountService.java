package service;

import external.dao.CurrencyAccountDao;
import external.dao.UserAccountDao;
import external.dao.UserDao;
import external.entity.CurrencyAccount;
import external.entity.UserAccount;
import view.Menu;

class UserAccountService {
    private final UserAccount userAccount;
    private final InputDataService inputDataService;
    private final Menu menu;
    private CurrencyAccountService currencyAccountService;

    public UserAccountService(UserAccount userAccount, InputDataService inputDataService, Menu menu) {
        this.userAccount = userAccount;
        this.inputDataService = inputDataService;
        this.menu = menu;
    }

    void userAccountMenu() {
        menu.showUserAccount(userAccount);
        String option = inputDataService.getUserAccountOption(userAccount);

        switch (option) {
            case "ADD":
                menu.showPossibleCurrencyAccounts();
                addNewCurrencyAccount(inputDataService.getPossibleCurrencyAccount());
                userAccountMenu();
                break;
            case "DELETE":
                menu.warningAboutTheDeletionOfTheUserAccount();
                if (inputDataService.yesOrNo()) {
                    deleteAll();
                    System.out.println("Thank you for the money you leave us ;)");
                } else {
                    userAccountMenu();
                }
                break;
            case "SETTING":
                settingMenu();
                break;
            case "EXIT":
                break;
            default:
                userAccount.getCurrencyAccounts().forEach(currencyAccount -> {
                    if (currencyAccount.getCode().equals(option)) {
                        currencyAccountService = new CurrencyAccountService(currencyAccount, menu, inputDataService);
                        currencyAccountService.currencyAccountMenu();
                        userAccountMenu();
                    }
                });
        }


    }

    private void settingMenu() {
        menu.showSetting(userAccount);
        menu.showSettingMenuOption();
        switch (inputDataService.getSettingMenuOption()) {
            case "PASSWORD": {
                menu.enterPassword();
                userAccount.getUser().setUserPassword(inputDataService.getPassword(userAccount.getUser().getUserLogin()));
                new UserDao().saveOrUpdate(userAccount.getUser());
                settingMenu();
            }
            case "BACK": {
                userAccountMenu();
            }
        }
    }

    private void addNewCurrencyAccount(String currencyCode) {
        CurrencyAccountDao currencyAccountDao = new CurrencyAccountDao();
        switch (currencyCode) {
            case "PLN": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getPolishZlotyAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "USD": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getAmericanDollarAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "AUD": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getAustralianDollarAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "CAD": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getCanadianDollarAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "EUR": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getEuroAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "HUF": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getForintAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "CHF": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getSwissFrancAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "GBP": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getPoundSterlingAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "JPY": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getJapanYenAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "CZK": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getCzechKorunaAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "DKK": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getDanishKroneAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "NOK": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getNorvegianKroneAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "SEK": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getSwedishCrownAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
            case "XDR": {
                CurrencyAccount currencyAccount = CurrencyAccountService.getSpecialDrawingRightsAccount(userAccount);
                userAccount.getCurrencyAccounts().add(currencyAccount);
                currencyAccountDao.saveOrUpdate(currencyAccount);
                break;
            }
        }
    }

    private void deleteAll(){
       /* CurrencyAccountDao currencyAccountDao = new CurrencyAccountDao();
        TransactionLogDao transactionLogDao = new TransactionLogDao();

        for (CurrencyAccount currencyAccount : userAccount.getCurrencyAccounts()) {
            if(currencyAccount.getTransactionLogs().size() > 0){
                transactionLogDao.deleteAll(currencyAccount.getTransactionLogs());
            }
            currencyAccountDao.delete(currencyAccount);
        }*/
        //User user = userAccount.getUser();
        new UserAccountDao().delete(userAccount);
       // new UserDao().delete(user);
    }

}
