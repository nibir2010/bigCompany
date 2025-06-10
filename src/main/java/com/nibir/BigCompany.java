package com.nibir;

import java.io.IOException;

public class BigCompany {
    public static void main(String[] args) {
        try {
            // Analyze the company structure from the CSV file
            Company company = new Company("employees.csv");
            company.printReport();

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}
