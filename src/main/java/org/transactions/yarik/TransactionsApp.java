package org.transactions.yarik;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionsApp {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new User("Bob", BigDecimal.valueOf(1000.00)));
        users.add(new User("John", BigDecimal.valueOf(2000.00)));
        users.add(new User("Rob", BigDecimal.valueOf(500.00)));

        users.forEach(System.out::println);

        for (int i = 0; i < 5; i++) {
            (new Thread(new TransactionSimulator(users))).start();
        }
    }
}
