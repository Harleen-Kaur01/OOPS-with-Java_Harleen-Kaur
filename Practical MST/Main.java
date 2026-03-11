import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class InsufficientBalanceException extends Exception {
    InsufficientBalanceException(String message) {
        super(message);
    }
}

class BankAccount {

    int accountNumber;
    String holderName;
    double balance;

    BankAccount(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
        TransactionLogger.logTransaction(accountNumber, "Deposit", amount);
    }

    void withdraw(double amount) throws InsufficientBalanceException {
        if(amount > balance) {
            throw new InsufficientBalanceException("Insufficient Balance!");
        }
        balance -= amount;
        System.out.println("Withdrawn: " + amount);
        TransactionLogger.logTransaction(accountNumber, "Withdraw", amount);
    }

    void calculateAverage(int totalTransactions) {
         if (totalTransactions <= 0) {
            System.out.println("No transactions to calculate average.");
            return;
        }
        double avg = balance / totalTransactions;
        System.out.println("Average balance value: " + avg);
    }
}

class TransactionLogger {
    static void logTransaction(int accNo, String type, double amount) {

        try {
            FileWriter writer = new FileWriter("transactions.txt", true);
            writer.write(accNo + " | " + type + " | " + amount + "\n");
            writer.close();
        } 
        catch(IOException e) {
            System.out.println("File Error Occurred");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        BankAccount account = new BankAccount(101, "Harleen", 1000);

        try {
            account.deposit(500);
            account.withdraw(200);
            account.calculateAverage(0); 
        }
        catch(InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
        catch(ArithmeticException e) {
            System.out.println("Arithmetic Error: Division by zero");
        }
        catch(Exception e) {
            System.out.println("Some other error occurred");
        }
    }
}