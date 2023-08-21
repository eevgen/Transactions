import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.*;

public class PuttingProcess implements Runnable {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    Main main = new Main();
    GettingProcess gettingProcess;

    public PuttingProcess(GettingProcess gettingProcess) {
        this.gettingProcess = gettingProcess;
    }

    @Override
    public void run() {
        addToList();
    }


    public int createTransaction() {
        return (new Random()).nextInt(100, 1000);
    }

    public void addToList() {
        lock.lock();
        try {
            int transaction = createTransaction();
            User senderUser = (User) main.getUsersList().get((new Random()).nextInt(0, main.getUsersList().size() - 1));
            ArrayList usersListWithoutSenderUser = main.getUsersList();
            usersListWithoutSenderUser.remove(senderUser);
            User goalUser = (User) usersListWithoutSenderUser.get((new Random()).nextInt(0, usersListWithoutSenderUser.size() - 1));
            System.out.printf("%s has been removed from senders users list. And now list's size is %d\n", senderUser.getName(), main.getUsersList().size());
            main.addToList(goalUser, transaction);
            senderUser.minusTransactionSumToBalance(transaction);
            System.out.printf("%s has sent %d$ to %s and now sender's user's balance: %d$\n", senderUser.getName(), transaction, goalUser.getName(), senderUser.getBalance());
            main.takeFromList().forEach((key, value) -> System.out.printf("Name of the transaction recipient: %s - Transaction amount: %d\n", key.getName(), value));
            gettingProcess.signalLockGetting();
        } finally {
            lock.unlock();
        }
    }
}
