public class SavingsAccount extends BankAccount {

    // Savings Account inherits methods from parent
    // final and static keyword to denote that variable is constant

    static final double MINIMUM_BALANCE = 20;
    private static final double WITHDRAW_FEE= 5;
    private static final double TRANSFER_FEE = 5;

    // Constructor uses super class constructor to create class
    SavingsAccount(String firstName, String lastName, double startingBalance, int accountNumber) {
        super("Savings Account",firstName,lastName,startingBalance,accountNumber,MINIMUM_BALANCE,WITHDRAW_FEE,TRANSFER_FEE);
    }

}
