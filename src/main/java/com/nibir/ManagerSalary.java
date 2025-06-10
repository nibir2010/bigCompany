package com.nibir;

public class ManagerSalary {
    private Employee manager;
    private double amount;
    private boolean underpaid;

    public ManagerSalary(Employee manager, double amount, boolean underpaid) {
        this.manager = manager;
        this.amount = amount;
        this.underpaid = underpaid;
    }

    public Employee getManager() { return manager; }
    public double getAmount() { return amount; }
    public boolean isUnderpaid() { return underpaid; }
}