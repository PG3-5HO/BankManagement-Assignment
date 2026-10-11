import java.util.*; // importing the functions need to execute code

public class Main {

    // generation of random numbers used to create id
    private static final Random random = new Random();


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        List<BankAccount> accounts = new ArrayList<>();

        System.out.println("===== Welcome to ZEB BANKING =====");
        int choice;

        do{
            viewMenu();
            choice = readInt(input, "Please select a choice: ");
            try{
                switch(choice){
                    case 1: createAccount(input,accounts);
                    break;
                    case 2: deposit(input,accounts);
                    break;
                    case 3: withdraw(input,accounts);
                    break;
                    case 4: transferFunds(input,accounts);
                    break;
                    case 5: viewBalance(input,accounts);
                    break;
                    case 6: viewStatement(input,accounts);
                    break;
                    case 7: viewLedger(input,accounts);
                    break;
                    case 8: displayAccounts(accounts);
                    break;
                    case 9: deleteAccount(input,accounts);
                    break;
                    case 0: System.out.println("Thank you for using this program.");
                    break;
                    default: System.out.println("Invalid choice.");
                }
            }catch(IllegalArgumentException | IndexOutOfBoundsException e){
                System.out.println("Error: " + e.getMessage());
            }
        }while(choice !=0);
        input.close();

    }

    // Menu setup
    public static void viewMenu(){
        System.out.println();
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer funds");
        System.out.println("5. Balance");
        System.out.println("6. Bank Statement");
        System.out.println("7. Bank Ledger: containing all accounts");
        System.out.println("8. Display Accounts");
        System.out.println("9. Delete Account");
        System.out.println("0. Exist");

    }

    // Checks if account is present and returns the account
    private static BankAccount findAccount(Scanner input, List<BankAccount> accounts){
        if(accounts.isEmpty()){
            System.out.println("No accounts founds");
            return null;
        }
        int number = readInt(input,"Enter Account Number: ");
        for(BankAccount account : accounts){
            if(account.getAccountNumber() == number){
                return account;
            }
        }
        System.out.println("Account number not found");
        return null;
    }

    // generates account Numbers
    private static int generateAccountId(List<BankAccount> accounts){
        int id;
        boolean found;

        do{
            id = 10000 + new Random().nextInt(90000);
            found = false;
            for(BankAccount account : accounts){
                if(account.getAccountNumber() == id){
                    found = true;
                    break;
                }
            }
        }while(found);
        return id;
    }

    // validates integers and returns integer
    private static int readInt(Scanner input, String prompt){
        while(true){
            System.out.print(prompt);
            try{
                return Integer.parseInt(input.nextLine().trim());
            }catch(NumberFormatException e){
                System.out.println("Invalid input enter integer");
            }
        }
    }

    // validates doubles and returns double
    private static double readDouble(Scanner input, String prompt){
        while(true){
            System.out.print(prompt);
            try{
                double value =  Double.parseDouble(input.nextLine().trim());
                if(Double.isFinite(value)){return value;}
            }catch(NumberFormatException e){

            }
            System.out.println("Invalid input enter the correct format");
        }
    }

    // validates texts and returns text
    private static String readText(Scanner input, String prompt){
        while(true){
            System.out.print(prompt);
            String text = input.nextLine().trim();
            if(!text.isEmpty()){return text;}
            System.out.println("Text cannot be empty");
        }
    }

    // creates account using Bank Account
    private static void createAccount(Scanner input,List<BankAccount> accounts){
        System.out.println("Choose Account");
        System.out.println("1. Current Account (minimum "+BankAccount.kwacha(CurrentAccount.MINIMUM_BALANCE)+" )");
        System.out.println("2. Savings Account (minimum "+BankAccount.kwacha(SavingsAccount.MINIMUM_BALANCE)+" )");
        int choice = readInt(input,"Input: ");

        if(choice !=1 && choice !=2){
            System.out.println("Invalid Choice");
            return;
        }

        String firstName = readText(input,"Enter first name: ");
        String lastName = readText(input,"Enter last name: ");
        double starting = readDouble(input,"Enter starting balance: ");
        int id = generateAccountId(accounts);

        BankAccount account = (choice == 1)
                ? new CurrentAccount(firstName,lastName,starting,id)
                : new SavingsAccount(firstName,lastName,starting,id);

        accounts.add(account);
        account.welcomeMessage();
    }

    // validates depositing
    private static void deposit(Scanner input,List<BankAccount> accounts){
        BankAccount account = findAccount(input,accounts);
        if(account == null){ return;}
        account.deposit(readDouble(input,"Enter amount to be deposited: "));
        System.out.println("Deposit successfully");
        System.out.println("Current balance: "+BankAccount.kwacha(account.getBalance()));
    }

    // validates withdraw
    private static void withdraw(Scanner input,List<BankAccount> accounts){
        BankAccount account = findAccount(input,accounts);
        if(account == null){ return;}
        account.withdraw(readDouble(input,"Enter amount to withdraw "));
        System.out.println("Withdraw successfully");
        System.out.println("Current balance: "+BankAccount.kwacha(account.getBalance()));
    }

    //validates transfers
    private static void transferFunds(Scanner input,List<BankAccount> accounts){
        System.out.println("--- Senders Info ---");
        BankAccount sender = findAccount(input, accounts);
        if(sender == null){ return;}
        System.out.println("--- Receivers Info ---");
        BankAccount receiver = findAccount(input, accounts);
        if(receiver == null){ return;}
        sender.transferFunds(readDouble(input,"Transfer Amount: "),receiver);
        System.out.println("Amount successfully transferred to "+receiver.getAccountNumber()+
                ". Balance: "+BankAccount.kwacha(sender.getBalance()));
    }


    private static void viewBalance(Scanner input,List<BankAccount> accounts){
        BankAccount account = findAccount(input,accounts);
        if(account == null){ return;}
        System.out.println("Current balance: "+BankAccount.kwacha(account.getBalance()));
    }

    private static void viewStatement(Scanner input,List<BankAccount> accounts){
        BankAccount account = findAccount(input,accounts);
        if(account == null){ return;}
        account.printStatement();
    }

    private static void viewLedger(Scanner input,List<BankAccount> accounts){
        if(accounts.isEmpty()){
            System.out.println("No Accounts found");
            return;
        }
        for(BankAccount account : accounts){
            account.printStatement();
        }
    }

    private static void displayAccounts(List<BankAccount> accounts){
        if(accounts.isEmpty()){
            System.out.println("No Accounts found");
            return;
        }
        for(BankAccount account : accounts){
            System.out.println(account);
        }
    }

    // handles deletion
    private static void deleteAccount(Scanner input,List<BankAccount> accounts){
        System.out.println("--- Account Deletion ---");
        BankAccount account = findAccount(input,accounts);
        if(account == null){ return;}
        accounts.remove(account);
        System.out.println("Account "+account.getAccountNumber()+" successfully deleted");
    }


}
