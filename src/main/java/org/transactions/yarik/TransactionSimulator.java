package org.transactions.yarik;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class TransactionSimulator implements Runnable {
    private final List<User> users;
    private final Random random = new Random();

    public TransactionSimulator(List<User> users) {
        this.users = users;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int senderId = random.nextInt(users.size());
            int receiverId = random.nextInt(users.size());
            while (senderId == receiverId) {
                receiverId = random.nextInt(users.size());
            }

            User sender = users.get(senderId);
            User receiver = users.get(receiverId);

            BigDecimal transactionValue = BigDecimal.valueOf(random.nextDouble() * 10 + 1).setScale(2, RoundingMode.HALF_UP);
            System.out.println("trying to create transaction for " + transactionValue + "$");

            synchronized (users) {
                if (sender.getBalance().compareTo(transactionValue) >= 0) {
                    sender.setBalance(sender.getBalance().subtract(transactionValue));
                    receiver.setBalance(receiver.getBalance().add(transactionValue));

                    System.out.println(sender.getName() + " sent " + transactionValue + "$ to " + receiver.getName());
                    System.out.println(sender.getName() + " has " + sender.getBalance() + "$");
                    System.out.println(receiver.getName() + " has " + receiver.getBalance() + "$");
                } else {
                    System.err.println(sender.getName() + " has not enough balance (" + sender.getBalance() + ")");
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
