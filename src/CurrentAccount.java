public class CurrentAccount extends BankAccount {

    // Current Account inherits methods from parent
    // final and static keyword to denote that variable is constant

    static final double MINIMUM_BALANCE = 500;
    static final double WITHDRAW_FEE = 5;
    static final double TRANSFER_FEE = 10;

    // Constructor uses super class constructor to create class
    CurrentAccount(String firstName, String lastName, double startingBalance, int accountNumber) {
        super("Current Account",firstName,lastName,startingBalance,accountNumber,MINIMUM_BALANCE,WITHDRAW_FEE,TRANSFER_FEE);
    }
}
