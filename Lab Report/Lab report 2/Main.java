class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName, double balance) { //setter
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        setBalance(balance);
    }

    public void printAccount() { // getter
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
        System.out.println("---------------------------");
    }

    public void setBalance(double balance) { // setter for balance with validation
        if (balance < 0) {
            System.out.println("Error: Balance cannot be negative. Value not updated.");
        } else {
            this.balance = balance;
        }
    }
}
public class Main {
    public static void main(String[] args) {

        // Creating BankAccount objects
        BankAccount ac1 = new BankAccount("A101", "Johirul", 5000);
        BankAccount ac2 = new BankAccount("A102", "Islam", 8000);
        BankAccount ac3 = new BankAccount("A103", "Niloy", 12000);

        System.out.println("\n--- Account Details Before Updating Balance ---");
        ac1.printAccount();
        ac2.printAccount();
        ac3.printAccount();

        ac1.setBalance(7000);
        ac2.setBalance(-500); // not update the balance because it's negative
        ac3.setBalance(15000);
        // ac1.balance = 99999;  // it shows error because balance is private

        System.out.println("\n--- Account Details After Updating Balance ---");
        ac1.printAccount();
        ac2.printAccount();
        ac3.printAccount();

    }

}