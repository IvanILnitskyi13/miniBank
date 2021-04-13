package service;

import external.entity.UserAccount;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InputDataService {

    private final static Scanner scanner = new Scanner(System.in);

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    boolean yesOrNo() {
        System.out.print("Please enter YES or NO\n-> ");
        String option = scanner.nextLine().toUpperCase();
        if (option.equals("YES")) {
            return true;
        } else if (option.equals("NO")) {
            return false;
        } else
            return yesOrNo();
    }

    String getFirstLastName() {
        System.out.print("\n-> ");
        String firstName = scanner.nextLine().trim();
        if (firstName.length() >= 3) {
            return firstName;
        } else
            System.out.print("Login length must be at least 3 characters ");
        return getFirstLastName();
    }

    String getPhoneNumber() {
        System.out.print("\n-> ");
        String stringPhoneNumber = scanner.nextLine();
        try {
            Integer.parseInt(stringPhoneNumber);
            if (stringPhoneNumber.length() == 9) {
                return stringPhoneNumber;
            } else {
                System.out.print("Phone number should be 9 digits long");
            }
        } catch (NumberFormatException e) {
            System.out.print("Value is not a number");
        }
        return getPhoneNumber();
    }

    String getEmail() {
        System.out.print("\n-> ");
        String email = scanner.nextLine();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (matcher.find()) {
            return email;
        } else
            System.out.print("Email has an invalid format");
        return getEmail();
    }

    String getLogin() {
        System.out.print("\n-> ");
        String loginWithoutWhiteSpace = scanner.nextLine().replaceAll("\\s+", "");

        if (loginWithoutWhiteSpace.equals("")) {
            System.out.print("Login cannot be empty\n-> ");
        } else if (loginWithoutWhiteSpace.length() < 5) {
            System.out.print("Login length must be at least 5 characters ");
        } else {
            return loginWithoutWhiteSpace;
        }
        return getLogin();
    }

    String getPassword(String login) {
        System.out.print("\n-> ");
        String passwordWithoutWhiteSpace = scanner.nextLine().replaceAll("\\s+", "");
        if (passwordWithoutWhiteSpace.equals("")) {
            System.out.print("Password cannot be empty ");
        } else if (passwordWithoutWhiteSpace.length() < 5) {
            System.out.print("Password length must be at least 5 characters");
        } else if (login.equals(passwordWithoutWhiteSpace)) {
            System.out.print("Password cannot be the same as login");
        } else {
            return passwordWithoutWhiteSpace;
        }
        return getPassword(login);
    }

    String getUserAccountOption(UserAccount userAccount) {
        System.out.print("\n-> ");
        String option = scanner.nextLine().toUpperCase();
        List<String> userAccountOption = new ArrayList<>();
        userAccountOption.add("ADD");
        userAccountOption.add("DELETE");
        userAccountOption.add("SETTING");
        userAccountOption.add("EXIT");
        userAccount.getCurrencyAccounts().forEach(e -> userAccountOption.add(e.getCode()));

        for (String s : userAccountOption) {
            if (s.equals(option)) return option;
        }

        System.out.print("The options are not correct");
        return getUserAccountOption(userAccount);
    }

    String getPossibleCurrencyAccount() {
        System.out.print("\n-> ");
        String[] possibleCurrencyAccount = new String[]{
                "PLN", "USD", "AUD", "CAD", "EUR", "HUF", "GBP", "JPY", "CZK", "DKK", "SEK", "XDR"};
        String currentAccount = scanner.nextLine().toUpperCase();
        for (String s : possibleCurrencyAccount) {
            if (s.equals(currentAccount)) {
                return currentAccount;
            }
        }
        System.out.print("The options are not correct");
        return getPossibleCurrencyAccount();
    }

    String getSettingMenuOption() {
        System.out.print("\n-> ");
        String option = scanner.nextLine().toUpperCase();
        switch (option) {
            case "PASSWORD":
            case "BACK":
                return option;
        }
        System.out.print("The options are not correct");
        return getSettingMenuOption();
    }

    String getCurrencyAccountMenuOption() {
        System.out.print("\n-> ");
        String option = scanner.nextLine().toUpperCase();
        switch (option) {
            case "PAY":
            case "TRANSFER":
            case "HISTORY":
            case "DELETE":
            case "BACK":
                return option;
        }
        System.out.print("The options are not correct");
        return getCurrencyAccountMenuOption();
    }

    double getPaidUpCapital() {
        System.out.print("\nEnter paid-up capital\n-> ");
        try {
            double value = scanner.nextDouble();
            scanner.nextLine();
            return value;
        } catch (InputMismatchException e) {
            System.out.print("Value is not correct, input value like - 10.0\n-> ");
        }
        return getPaidUpCapital();
    }

    int getCurrencyAccountNumberToTransfer() {
        System.out.println("\nAccount number have 4 digit");
        System.out.print("Enter account number\n-> ");
        String accountNumber = scanner.nextLine();
        try {
            int accountNumberToTransfer = Integer.parseInt(accountNumber);
            if (accountNumberToTransfer / 1000 > 0) {
                return accountNumberToTransfer;
            }
        } catch (NumberFormatException e) {
            System.out.print("Value is not a number\n-> ");
        }
        return getCurrencyAccountNumberToTransfer();
    }

    double getAmountToTransfer() {
        System.out.print("\nEnter amount to transfer\n -> ");
        try {
            double value = scanner.nextDouble();
            scanner.nextLine();
            return value;
        } catch (InputMismatchException e) {
            System.out.print("Value is not correct, input value like - 10.0\n-> ");
        }
        return getPaidUpCapital();
    }

    String getTransactionLogMenuOption() {
        System.out.print("\n-> ");
        String option = scanner.nextLine().toUpperCase();
        if ("BACK".equals(option)) {
            return option;
        }
        System.out.print("The options are not correct \n-> ");
        return getTransactionLogMenuOption();
    }


}
