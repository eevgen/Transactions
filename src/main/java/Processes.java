import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;

public class Processes implements Runnable{

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    ArrayList listOfUsers;
    Main main = new Main();

    public Processes() {
        listOfUsers = main.getUsersList();
    }

    @Override
    public void run() {
        processOfAddingAndMinusUsersBalances();
    }

    public int createTransaction() {
        return (new Random()).nextInt(100, 1000);
    }

    public void processOfAddingAndMinusUsersBalances() {
        lock.lock();
        try {
            int transaction = createTransaction();
            User senderUser = (User) listOfUsers.get((new Random()).nextInt(0, listOfUsers.size() - 1));
            ArrayList usersListWithoutSenderUser = listOfUsers;
            usersListWithoutSenderUser.remove(senderUser);
            User goalUser = (User) usersListWithoutSenderUser.get((new Random()).nextInt(0, usersListWithoutSenderUser.size() - 1));
            System.out.printf("%s has been removed from senders users list. And now list's size is %d\n", senderUser.getName(), main.getUsersList().size());
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
