package com.nibir;

import org.junit.*;
import java.io.*;
import java.nio.file.*;
import java.util.Map;

import static org.junit.Assert.*;

public class CompanyTest {

    private Path tempCsv;
    private Company company;

    @Before
    public void setUp() throws IOException {
        tempCsv = Files.createTempFile("test_employees", ".csv");
        String csvContent = "id,firstName,lastName,salary,managerId\n" +
                "1,Alice,Smith,100000,\n" +
                "2,Bob,Johnson,80000,1\n" +
                "3,Carol,Williams,70000,2\n" +
                "4,David,Brown,60000,2\n";
        Files.write(tempCsv, csvContent.getBytes());

        company = new Company(tempCsv.toString());
    }

    @Test
    public void testCompanyLoadsEmployees() {
        Map<Integer, Employee> employees = company.getEmployees();
        assertEquals(4, employees.size());
        assertNotNull(company.getCeo());
        assertEquals("Alice", company.getCeo().getFirstName());
    }

    @Test
    public void testAnalyzeManagerSalaries() {
        assertNotNull(company.findManagerSalariesWithIssues());
    }

    @Test
    public void testFindLongReportingLines() {
        assertNotNull(company.findLongReportingLines());
    }
}