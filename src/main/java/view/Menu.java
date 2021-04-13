package view;


import external.entity.CurrencyAccount;
import external.entity.UserAccount;

public class Menu {

    public void doYouHaveAccountInOurBank() {
        System.out.println("Hello, do you have account in our bank?");
    }

    public void doYourWontRegister() {
        System.out.println("\nDo you want to register in our bank?");
    }

    public void enterLogin() { System.out.print("\nEnter login:"); }

    public void enterPassword() {
        System.out.print("\nEnter password:");
    }

    public void enterPhoneNumber() {
        System.out.print("\nEnter phone number:");
    }

    public void enterEmail() {
        System.out.print("\nEnter email:");
    }

    public void enterFirstName() {
        System.out.print("\nEnter first name:");
    }

    public void enterLastName() {
        System.out.print("\nEnter last name:");
    }

    public void showUserAccount(UserAccount userAccount) {
        System.out.println("\nYou can enter to your currency account, enter currency account name like - PLN\n");
        System.out.println("Your currency accounts:");
        userAccount.getCurrencyAccounts().forEach(a -> System.out.println("- " + a.getCode() + " " + a.getBalance()));
        System.out.println();
        System.out.println("-> Add new currency account (add)");
        System.out.println("-> Setting");
        System.out.println("-> Delete");
        System.out.println("-> Exit");

        chooseOneOfTheOptions();
    }

    private void chooseOneOfTheOptions() {
        System.out.print("\nChoose one of the options");
    }

    public void showPossibleCurrencyAccounts() {
        System.out.println("Enter PLN -> Polish zloty currency account");
        System.out.println("Enter USD -> American dollar currency account");
        System.out.println("Enter AUD -> Australian dollar currency account");
        System.out.println("Enter CAD -> Canadian dollar currency account");
        System.out.println("Enter EUR -> Euro currency account");
        System.out.println("Enter HUF -> Hungarian forint currency account");
        System.out.println("Enter GBP -> Pound sterling currency account");
        System.out.println("Enter JPY -> Japan yen currency account");
        System.out.println("Enter CZK -> Czech koruna currency account");
        System.out.println("Enter DKK -> Danish krone currency account");
        System.out.println("Enter SEK -> Swedish crown currency account");
        System.out.println("Enter XDR -> Special drawing rights currency account");
        chooseOneOfTheOptions();
    }

    public void showSetting(UserAccount userAccount) {
        System.out.println("\nYou can change your data\n");
        System.out.println("First name   - " + userAccount.getFirstName());
        System.out.println("Last name    - " + userAccount.getLastName());
        System.out.println("Email        - " + userAccount.getEmail());
        System.out.println("Phone number - " + userAccount.getPhoneNumber());
        System.out.println("\nLogin        - " + userAccount.getUser().getUserLogin());
        System.out.println("Password     - " + userAccount.getUser().getUserPassword());

    }

    public void showSettingMenuOption() {
        System.out.println("\n-> Change password (password)");
        System.out.println("-> Back");

        chooseOneOfTheOptions();
    }

    public void showCurrencyAccount(CurrencyAccount currencyAccount) {
        System.out.println("\nCurrency account number - " + currencyAccount.getAccountNumber());
        System.out.println("Currency                - " + currencyAccount.getCurrency());
        System.out.println("Code                    - " + currencyAccount.getCode());
        System.out.printf("Balance                 - %.2f\n", currencyAccount.getBalance());

    }

    public void deleteYourCurrencyAccount(){
        System.out.println("Are you sure you want to delete your foreign currency account ?");
    }

    public void showCurrencyAccountMenuOption() {
        System.out.println("\n-> Pay into account (pay)");
        System.out.println("-> Make a transfer (transfer)");
        System.out.println("-> See the history of transfers (history)");
        System.out.println("-> Delete currency account (delete)");
        System.out.println("-> Back");

        chooseOneOfTheOptions();
    }

    public void showTransactionLog(CurrencyAccount currencyAccount) {
        System.out.println("\n   Date    | Foreign currency account | transfer amount | balance after transaction |");
        currencyAccount.getTransactionLogs().forEach(t -> {
            String transferAmount = String.format("%.2f", t.getTransferAmount());
            String balanceAfterTransaction = String.format("%.2f", t.getBalanceAfterTransaction());
            System.out.print(t.getDate() + " |" + " " + t.getForeignCurrencyAccount() + "                     | " + transferAmount);
            for (int i = 0; i < 16 - transferAmount.length(); i++) {
                System.out.print(" ");
            }
            System.out.print("| "+ balanceAfterTransaction);
            for (int i = 0; i < 26-balanceAfterTransaction.length(); i++) {
                System.out.print(" ");
            }
            System.out.println("|");
        });
        System.out.println("\n-> Back");

        chooseOneOfTheOptions();
    }

    public void warningAboutTheDeletionOfTheUserAccount(){
        System.out.println("\nWARNING!!!\n");
        System.out.println("All data, accounts and logs will be deleted!");
        System.out.println("Are you sure you want to delete your user account?\n");

    }
}
