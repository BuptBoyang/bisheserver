package indi.boyang.bishe.model;

import java.math.BigInteger;

public class AccountInfo {
    private String address;
    private int balance;

    public AccountInfo(String address, int balance){
        this.address = address;
        this.balance = balance;
    }


    public String getAddress() {
        return address;
    }

    public int getBalance() {
        return balance;
    }
}
