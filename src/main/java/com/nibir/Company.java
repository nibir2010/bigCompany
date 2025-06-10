package com.nibir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Company {
    private Map<Integer, Employee> employees;
    private Employee ceo;

    public Company(String csvFile) throws IOException {
        this.employees = new HashMap<>();
        loadEmployees(csvFile);
        buildHierarchy();
    }

    private void loadEmployees(String csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                int salary = Integer.parseInt(parts[3]);
                Integer managerId = (parts.length > 4 && !parts[4].isEmpty()) ? Integer.parseInt(parts[4]) : null;

                Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                employees.put(id, employee);

                if (Objects.isNull(managerId)) {
                    ceo = employee;
                }
            }
        }
    }

    private void buildHierarchy() {
        for (Employee employee : employees.values()) {
            if (Objects.nonNull(employee.getManagerId())) {
                Employee manager = employees.get(employee.getManagerId());
                manager.addSubordinate(employee);
            }
        }
    }

    public List<ManagerSalary> analyzeManagerSalaries() {
        List<ManagerSalary> underpaidManagers = new ArrayList<>();
        List<ManagerSalary> overpaidManagers = new ArrayList<>();

        for (Employee employee : employees.values()) {
            if (employee.isManager()) {
                List<Employee> subordinates = employee.getSubordinates();
                double avgSubordinateSalary = subordinates.stream()
                        .mapToInt(Employee::getSalary)
                        .average()
                        .orElse(0.0);

                double minSalary = avgSubordinateSalary * 1.2; // 20% more
                double maxSalary = avgSubordinateSalary * 1.5; // 50% more

                if (employee.getSalary() < minSalary) {
                    double deficit = minSalary - employee.getSalary();
                    underpaidManagers.add(new ManagerSalary(employee, deficit, true));
                } else if (employee.getSalary() > maxSalary) {
                    double excess = employee.getSalary() - maxSalary;
                    overpaidManagers.add(new ManagerSalary(employee, excess, false));
                }
            }
        }

        List<ManagerSalary> allIssues = new ArrayList<>();
        allIssues.addAll(underpaidManagers);
        allIssues.addAll(overpaidManagers);
        return allIssues;
    }

    public List<ReportingLine> findLongReportingLines() {
        List<ReportingLine> longReportingLines = new ArrayList<>();

        for (Employee employee : employees.values()) {
            if (employee != ceo) {
                int managerCount = countManagersToCEO(employee);
                if (managerCount > 4) {
                    int excessLevels = managerCount - 4;
                    longReportingLines.add(new ReportingLine(employee, managerCount, excessLevels));
                }
            }
        }

        return longReportingLines;
    }

    private int countManagersToCEO(Employee employee) {
        int count = 0;
        Employee current = employee;

        while (Objects.nonNull(current.getManagerId())) {
            count++;
            current = employees.get(current.getManagerId());
        }

        return count;
    }

    public void printReport() {
        List<ManagerSalary> salaryIssues = analyzeManagerSalaries();

        List<ManagerSalary> underpaid = salaryIssues.stream()
                .filter(ManagerSalary::isUnderpaid)
                .collect(Collectors.toList());

        if (!underpaid.isEmpty()) {
            System.out.println("Managers earning less then they should:");
            for (ManagerSalary issue : underpaid) {
                System.out.printf("  %s should earn %.2f more%n",
                        issue.getManager(), issue.getAmount());
            }
        }

        List<ManagerSalary> overpaid = salaryIssues.stream()
                .filter(issue -> !issue.isUnderpaid())
                .collect(Collectors.toList());

        if (!overpaid.isEmpty()) {
            System.out.println("Managers earning more than they should:");
            for (ManagerSalary issue : overpaid) {
                System.out.printf("  • %s should earn %.2f less%n",
                        issue.getManager(), issue.getAmount());
            }
        }

        List<ReportingLine> longLines = findLongReportingLines();

        if (!longLines.isEmpty()) {
            System.out.println("Employees who have a long reporting line:");
            for (ReportingLine issue : longLines) {
                System.out.printf("  • %s has %d managers above them (%d too many)%n",
                        issue.getEmployee(), issue.getTotalLevels(), issue.getExcessLevels());
            }
        }
    }

    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    public Employee getCeo() {
        return ceo;
    }
}