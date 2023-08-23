package org.transactions.evgeniy;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Processes implements Runnable{

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    ArrayList listOfUsers;

    public Processes(ArrayList listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    @Override
    public void run() {
        processOfAddingAndMinusUsersBalances();
    }

    public int createTransaction() {
        return (new Random()).nextInt(100, 1000);
    }

    public void processOfAddingAndMinusUsersBalances() {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                int transaction = createTransaction();
                User senderUser = (User) listOfUsers.get((new Random()).nextInt(0, listOfUsers.size()));
                User goalUser = (User) listOfUsers.get((new Random()).nextInt(0, listOfUsers.size()));

                senderUser.minusTransactionSumToBalance(transaction);
                goalUser.addTransactionSumToBalance(transaction);
                System.out.printf("%s has sent %d$ to %s and now sender's user's balance: %d$\n", senderUser.getName(), transaction, goalUser.getName(), senderUser.getBalance());
                System.out.printf("%s has received %d$ from %s and now goal's user's balance: %d$\n", goalUser.getName(), transaction, senderUser.getName(), goalUser.getBalance());
                System.out.printf("%s - %d\n", goalUser.getName(), transaction);
            } finally {
                lock.unlock();
            }
        }
    }
}
