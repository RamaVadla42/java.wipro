import java.util.*;

class BankingApplication {

    static Scanner scanner = new Scanner(System.in);
    static List<Account> accounts = new ArrayList<>();
    static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\nBanking Application");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Create a new account
    private static void createAccount() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter account number: ");
        String number = scanner.nextLine();
        System.out.print("Enter Account Type (1 for Savings, 2 for Current): ");
        int accountType = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Account account;
        if (accountType == 1) {
            account = new SavingsAccount(name);
        } else {
            account = new CurrentAccount(name);
        }

        accounts.add(account);
        System.out.println("Account created successfully for " + name + ".");
    }

    // Deposit money into an account
    private static void deposit() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter Deposit Amount: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
            transactions.add(new Transaction(account, "Deposit", amount));
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    // Withdraw money from an account
    private static void withdraw() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter Withdrawal Amount: ");
            double amount = scanner.nextDouble();
            if (account.withdraw(amount)) {
                transactions.add(new Transaction(account, "Withdraw", amount));
                System.out.println("Withdrawal successful.");
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    // Transfer funds between accounts
    private static void transferFunds() {
        System.out.print("Enter Sender Account Number: ");
        String senderAccountNumber = scanner.nextLine();
        Account senderAccount = findAccount(senderAccountNumber);

        if (senderAccount != null) {
            System.out.print("Enter Receiver Account Number: ");
            String receiverAccountNumber = scanner.nextLine();
            Account receiverAccount = findAccount(receiverAccountNumber);

            if (receiverAccount != null) {
                System.out.print("Enter Transfer Amount: ");
                double amount = scanner.nextDouble();

                if (senderAccount.withdraw(amount)) {
                    receiverAccount.deposit(amount);
                    transactions.add(new Transaction(senderAccount, "Transfer", amount));
                    transactions.add(new Transaction(receiverAccount, "Transfer", amount));
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Receiver account not found.");
            }
        } else {
            System.out.println("Sender account not found.");
        }
    }

    // View all transactions
    private static void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    // Find an account by account number
    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

abstract class Account {
    protected String accountNumber;
    protected String customerName;
    protected double balance;

    public Account(String customerName) {
        this.customerName = customerName;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);

    // Generate a unique account number
    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

class SavingsAccount extends Account {
    public SavingsAccount(String customerName) {
        super(customerName);
    }

    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Savings Account [Account Number: " + accountNumber + ", Customer: " + customerName + ", Balance: " + balance + "]";
    }
}

class CurrentAccount extends Account {
    public CurrentAccount(String customerName) {
        super(customerName);
    }

    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        // Current account may have overdraft, so no balance limit check here.
        this.balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "Current Account [Account Number: " + accountNumber + ", Customer: " + customerName + ", Balance: " + balance + "]";
    }
}

class Transaction {
    private Account account;
    private String type;
    private double amount;

    public Transaction(Account account, String type, double amount) {
        this.account = account;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction [Account: " + account.getAccountNumber() + ", Type: " + type + ", Amount: " + amount + "]";
    }
}
