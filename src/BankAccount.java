import java.util.ArrayList;
import java.util.List;

// Abstract classes cannot be initialized , this class will be used as a blueprint for other classes

public abstract class BankAccount { // defining parameters
    private final String accountType;
    private final String firstName;
    private final String lastName;
    private double balance;
    private final int accountNumber;
    private final double minimumBalance;
    private final double withdrawFee;
    private final double transferFee;
    private final List<String> bankStatement = new ArrayList<String>();

    // Constructor gets 8 parameters which are used to build the class
    BankAccount(String accountType, String firstName, String lastName, double startingBalance,int accountNumber, double minimumBalance, double withdrawFee,double transferFee) {
        if(!(startingBalance >= minimumBalance)){
            throw new IllegalArgumentException("Minimum balance must be greater than or equal to "+kwacha(minimumBalance));
        }
        this.accountType = accountType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.minimumBalance = minimumBalance;
        this.withdrawFee = withdrawFee;
        this.transferFee = transferFee;
        this.balance = startingBalance;

        bankStatement.add("STARTING BALANCE"+kwacha(startingBalance));

    }

    // getters : private variables can only be accessed through getters

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // method to deposit
    void deposit(double amount){
        validateAmount(amount);
        cashIn(amount,"DEPOSIT");
    }

    // withdraw
    void withdraw(double amount){
        validateAmount(amount);
        double total= amount + withdrawFee;
        checkFunds(total);
        balance -= total;
        bankStatement.add("WITHDRAW "+kwacha(amount)+" (Fee "+ kwacha(withdrawFee)+")");

    }

    // transferFunds from one account to another account
    void transferFunds(double amount, BankAccount e){
        if(e == this){
            throw new IllegalArgumentException("Funds cannot be transferred to your own account");
        }
        validateAmount(amount);
        double total= amount + transferFee;
        checkFunds(total);
        balance -= total;
        bankStatement.add("FUNDS TRANSFERRED to "+e.accountNumber+" "+kwacha(amount)
        + "(Fee "+ kwacha(amount)+ ")");
        e.cashIn(amount,"TRANSFER IN from "+ accountNumber);

    }

    // prints out bank statement
    void printStatement(){
        System.out.println();
        System.out.println("===== MINI BANK STATEMENT =====");
        System.out.println("Names: "+ firstName + " " + lastName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type " + accountType);
        System.out.println("Current balance: " + kwacha(balance));
        System.out.println();
        System.out.println("---------------------------------");
        for (String s : bankStatement) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("===========================");
    }

    // This message will be displayed when an account is created
    void welcomeMessage(){
        System.out.println(accountType);
        System.out.println("Name "+firstName+" "+lastName);
        System.out.println("Account Number "+accountNumber);
        System.out.println("Thank you for using ZED Banking");
    }

    // returns information about the class
    public String toString() {
        return "Name:"+firstName+" "+lastName+" | "+accountType+" | "+" | Account number: "+accountNumber;
    }

    // checks if transaction can be made
    private void checkFunds(double total){
        if(balance - total < minimumBalance){
            throw new IllegalArgumentException("Minimum balance must be greater than or equal to "+minimumBalance);
        }
    }

    // ensures that users enter the right value
    private void validateAmount(double amount){
        if(!(amount > 0)){
            throw new IllegalArgumentException("Amount must be greater than or equal to 0");
        }
    }

    // adds this to the bank statement
    void cashIn(double amount, String type){
        balance += amount;
        bankStatement.add(type+" "+kwacha(amount));
    }

    // this method is used to format the currency
    static String kwacha(double amount){
        return String.format("ZMW %.2f",amount);
    }
}
