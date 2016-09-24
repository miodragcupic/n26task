package com.cupic;

/**
 * Created by Miodrag on 9/24/2016.
 */
public class Transaction {

    private double amount;

    private String type;

    private Long parent_id;

    public Transaction(String type, double amount, Long parent_id) {
        this.amount = amount;
        this.type = type;
        this.parent_id = parent_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parent_id;
    }

    public void setParentId(Long parent_id) {
        this.parent_id = parent_id;
    }
}
