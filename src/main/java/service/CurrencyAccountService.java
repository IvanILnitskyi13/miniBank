package service;

import com.squareup.okhttp.OkHttpClient;
import external.client.NbpApiClient;
import external.dao.CurrencyAccountDao;
import external.dao.ExchangeRateDao;
import external.dao.TransactionLogDao;
import external.entity.CurrencyAccount;
import external.entity.TransactionLog;
import external.entity.UserAccount;
import view.Menu;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

class CurrencyAccountService {

    private final CurrencyAccountDao currencyAccountDao = new CurrencyAccountDao();
    private final CurrencyAccount currencyAccount;
    private final Menu menu;
    private final InputDataService inputDataService;


    public CurrencyAccountService(CurrencyAccount currencyAccount, Menu menu, InputDataService inputDataService) {
        this.currencyAccount = currencyAccount;
        this.menu = menu;
        this.inputDataService = inputDataService;
    }

    void currencyAccountMenu() {
        menu.showCurrencyAccount(currencyAccount);
        menu.showCurrencyAccountMenuOption();
        switch (inputDataService.getCurrencyAccountMenuOption()) {
            case "PAY":
                double paidUp = inputDataService.getPaidUpCapital();
                payIntoCurrencyAccount(currencyAccount, paidUp);
                saveCurrencyAccountLog(currencyAccount, paidUp);
                currencyAccountMenu();
                break;
            case "TRANSFER":
                makeATransfer();
                currencyAccountMenu();
                break;
            case "HISTORY":
                menu.showTransactionLog(currencyAccount);
                if ("BACK".equals(inputDataService.getTransactionLogMenuOption())) {
                    currencyAccountMenu();
                }
                break;
            case "DELETE":
                menu.deleteYourCurrencyAccount();
                if (inputDataService.yesOrNo()) {
                    deleteCurrencyAccount();
                } else {
                    currencyAccountMenu();
                }
                break;
            case "BACK":
                break;
        }
    }


    private void makeATransfer() {
        CurrencyAccount currencyAccountToTransfer = null;
        int accountNumber = inputDataService.getCurrencyAccountNumberToTransfer();
        List<CurrencyAccount> userCurrencyAccounts = currencyAccount.getUserAccount().getCurrencyAccounts();

        for (CurrencyAccount userCurrencyAccount : userCurrencyAccounts) {
            if (userCurrencyAccount.getAccountNumber().equals(accountNumber)) {
                currencyAccountToTransfer = userCurrencyAccount;
            }
        }

        if (currencyAccountToTransfer == null) {
            try {
                currencyAccountToTransfer = currencyAccountDao.findByAccountNumber(accountNumber);
                transferToAnotherCurrencyAccount(currencyAccountToTransfer, inputDataService.getAmountToTransfer());
            } catch (NoResultException e) {
                System.out.println("There is no such account");
            }
        } else {
            transferToAnotherCurrencyAccount(currencyAccountToTransfer, inputDataService.getAmountToTransfer());
        }
    }

    private void deleteCurrencyAccount() {
        currencyAccountDao.delete(currencyAccount);
        currencyAccount.getUserAccount().getCurrencyAccounts().removeIf(
                e -> e.getId().equals(currencyAccount.getId()));
    }

    private void payIntoCurrencyAccount(CurrencyAccount currencyAccount, double value) {
        currencyAccount.setBalance(currencyAccount.getBalance() + value);
        currencyAccountDao.saveOrUpdate(currencyAccount);

    }


    private void transferToAnotherCurrencyAccount(CurrencyAccount currencyAccountToTransfer, Double amount) {
        ExchangeRateDao exchangeRateDao = new ExchangeRateDao();
        NbpApiClient nbpApiClient = new NbpApiClient(new OkHttpClient());
        ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao, nbpApiClient);

        if (currencyAccount.getCode().equals("PLN") && currencyAccountToTransfer.getCode().equals("PLN")) {
            transferFromPLNToPLNCurrencyAccount(currencyAccountToTransfer, amount);
        } else if (currencyAccount.getCode().equals("PLN") && !currencyAccountToTransfer.getCode().equals("PLN")) {
            transferFromPLNToForeignCurrencyAccount(currencyAccountToTransfer, amount, exchangeRateService);
        } else if (!currencyAccount.getCode().equals("PLN") && currencyAccountToTransfer.getCode().equals("PLN")) {
            transferFromForeignToPLNCurrencyAccount(currencyAccountToTransfer, amount, exchangeRateService);
        } else {
            transferFromForeignToForeignCurrencyAccount(currencyAccountToTransfer, amount, exchangeRateService);
        }
    }

    private void transferFromForeignToForeignCurrencyAccount(CurrencyAccount currencyAccountToTransfer, Double amount, ExchangeRateService exchangeRateService) {
        Double toTransferAmount = exchangeRateService.exchangeMoneyAsk(
                exchangeRateService.exchangeMoneyBid(amount, currencyAccount.getCode()), currencyAccountToTransfer.getCode());

        transferAndSaveLog(currencyAccountToTransfer, amount, toTransferAmount);
    }

    private void transferFromPLNToForeignCurrencyAccount(CurrencyAccount currencyAccountToTransfer, Double amount, ExchangeRateService exchangeRateService) {
        Double toTransferAmount = exchangeRateService.exchangeMoneyAsk(amount, currencyAccountToTransfer.getCode());

        transferAndSaveLog(currencyAccountToTransfer, amount, toTransferAmount);
    }

    private void transferFromForeignToPLNCurrencyAccount(CurrencyAccount currencyAccountToTransfer, Double amount, ExchangeRateService exchangeRateService) {
        Double toTransferAmount = exchangeRateService.exchangeMoneyBid(amount, currencyAccount.getCode());

        transferAndSaveLog(currencyAccountToTransfer, amount, toTransferAmount);
    }

    private void transferFromPLNToPLNCurrencyAccount(CurrencyAccount currencyAccountToTransfer, Double amount) {
        transferAndSaveLog(currencyAccountToTransfer, amount, amount);
    }

    private void transferAndSaveLog(CurrencyAccount currencyAccountToTransfer, Double amount, Double toTransferAmount) {
        currencyAccount.setBalance(currencyAccount.getBalance() - amount);
        currencyAccountToTransfer.setBalance(currencyAccountToTransfer.getBalance() +
                toTransferAmount);

        currencyAccount.getUserAccount().getCurrencyAccounts().forEach(currency -> {
            if (currencyAccount.getAccountNumber().equals(currencyAccountToTransfer.getAccountNumber())) {
                currency.setBalance(currencyAccountToTransfer.getBalance());
            }
        });

        currencyAccountDao.saveOrUpdate(currencyAccountToTransfer);
        currencyAccountDao.saveOrUpdate(currencyAccount);
        saveCurrencyAccountLog(currencyAccountToTransfer, amount);
        saveCurrencyAccountToTransferLog(currencyAccountToTransfer, toTransferAmount);
    }

    private void saveCurrencyAccountToTransferLog(CurrencyAccount currencyAccountToTransfer, Double amount) {
        List<TransactionLog> transactionLogs = currencyAccountToTransfer.getTransactionLogs();
        transactionLogs.add(new TransactionLog(LocalDate.now(), currencyAccount.getAccountNumber(), amount, currencyAccountToTransfer.getBalance(), currencyAccountToTransfer));
        new TransactionLogDao().save(transactionLogs.get(transactionLogs.size() - 1));
    }

    private void saveCurrencyAccountLog(CurrencyAccount currencyAccountToTransfer, Double amount) {
        List<TransactionLog> transactionLogs = currencyAccount.getTransactionLogs();
        transactionLogs.add(new TransactionLog(LocalDate.now(), currencyAccountToTransfer.getAccountNumber(), amount, currencyAccount.getBalance(), currencyAccount));
        new TransactionLogDao().save(transactionLogs.get(transactionLogs.size() - 1));
    }


    private static int generateAccountNumber() {
        int accountNumber = new Random().nextInt(8999) + 1000;

        while (availabilityOfTheAccountNumberInTheDB(accountNumber)) {
            accountNumber = generateAccountNumber();
        }
        return accountNumber;
    }

    private static boolean availabilityOfTheAccountNumberInTheDB(Integer accountNumber) {
        try {
            new CurrencyAccountDao().findByAccountNumber(accountNumber);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    static CurrencyAccount getPolishZlotyAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "polski złoty", "PLN", 0.0, userAccount);
    }

    static CurrencyAccount getAmericanDollarAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "dolar amerykański", "USD", 0.0, userAccount);
    }

    static CurrencyAccount getAustralianDollarAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "dolar australijski", "AUD", 0.0, userAccount);
    }

    static CurrencyAccount getCanadianDollarAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "dolar kanadyjski", "CAD", 0.0, userAccount);
    }

    static CurrencyAccount getEuroAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "euro", "EUR", 0.0, userAccount);
    }

    static CurrencyAccount getForintAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "forint (Węgry)", "HUF", 0.0, userAccount);
    }

    static CurrencyAccount getSwissFrancAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "frank szwajcarski", "CHF", 0.0, userAccount);
    }

    static CurrencyAccount getPoundSterlingAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "funt szterling", "GBP", 0.0, userAccount);
    }

    static CurrencyAccount getJapanYenAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "jen (Japonia)", "JPY", 0.0, userAccount);
    }

    static CurrencyAccount getCzechKorunaAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "korona czeska", "CZK", 0.0, userAccount);
    }

    static CurrencyAccount getDanishKroneAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "korona duńska", "DKK", 0.0, userAccount);
    }

    static CurrencyAccount getNorvegianKroneAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "korona norweska", "NOK", 0.0, userAccount);
    }

    static CurrencyAccount getSwedishCrownAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "korona szwedzka", "SEK", 0.0, userAccount);
    }

    static CurrencyAccount getSpecialDrawingRightsAccount(UserAccount userAccount) {
        return new CurrencyAccount(generateAccountNumber(), "SDR (MFW)", "XDR", 0.0, userAccount);
    }

}
