import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class User {

    private int balance = 5000;
    private String name;

    public User (String name) {
        this.name= name;
    }

    public void addTransactionSumToBalance(int sumOfTransaction) {
        balance += sumOfTransaction;
    }
    public void minusTransactionSumToBalance(int sumOfTransaction) {
        balance -= sumOfTransaction;
    }
    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
}
