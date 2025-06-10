package com.nibir;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int salary;
    private Integer managerId;
    private java.util.List<Employee> subordinates;

    public Employee(int id, String firstName, String lastName, int salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
        this.subordinates = new java.util.ArrayList<>();
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getSalary() { return salary; }
    public Integer getManagerId() { return managerId; }
    public java.util.List<Employee> getSubordinates() { return subordinates; }

    // Helper methods
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    public boolean isManager() {
        return !subordinates.isEmpty();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (ID: " + id + ")";
    }
}
