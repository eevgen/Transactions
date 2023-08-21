import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;

public class GettingProcess implements Runnable{

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    Main main = new Main();
    PuttingProcess puttingProcess;

    @Override
    public void run() {
        getFromList();
    }

    public void getFromList() {
        lock.lock();
        try {
            if(main.takeFromList().isEmpty()) {
                condition.await();
            }
            LinkedList<User> newListForKeys = new LinkedList<>();
            main.takeFromList().forEach((key, value) -> newListForKeys.add(key));
            User user = newListForKeys.getLast();
            int transactionSum = main.takeFromList().get(user);
            user.addTransactionSumToBalance(transactionSum);
            System.out.printf("%s has received %d$ and now %s's balance is %d$\n", user.getName(), transactionSum, user.getName(), user.getBalance());
        } catch (InterruptedException e) {
            currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void signalLockGetting() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

}
