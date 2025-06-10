package com.nibir;

public class ReportingLine {
    private Employee employee;
    private int totalLevels;
    private int excessLevels;

    public ReportingLine(Employee employee, int totalLevels, int excessLevels) {
        this.employee = employee;
        this.totalLevels = totalLevels;
        this.excessLevels = excessLevels;
    }

    public Employee getEmployee() { return employee; }
    public int getTotalLevels() { return totalLevels; }
    public int getExcessLevels() { return excessLevels; }
}
