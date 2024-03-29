package org.transactions.yarik;

import java.math.BigDecimal;

public class User {
    private String name;
    private BigDecimal balance;

    public User(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "org.transactions.evgeniy.User{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
