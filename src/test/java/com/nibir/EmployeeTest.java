package com.nibir;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class EmployeeTest {

    private Employee employee;
    private Employee manager;

    @Before
    public void setUp() {
        manager = new Employee(1, "John", "Manager", 100000, null);
        employee = new Employee(2, "Jane", "Employee", 70000, 1);
    }

    @Test
    public void testEmployeeCreation() {
        assertEquals(2, employee.getId());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Employee", employee.getLastName());
        assertEquals(70000, employee.getSalary());
        assertEquals(Integer.valueOf(1), employee.getManagerId());
    }

    @Test
    public void testAddSubordinate() {
        manager.addSubordinate(employee);
        List<Employee> subordinates = manager.getSubordinates();

        assertEquals(1, subordinates.size());
        assertTrue(subordinates.contains(employee));
    }

    @Test
    public void testIsManager() {
        assertFalse(employee.isManager());

        manager.addSubordinate(employee);
        assertTrue(manager.isManager());
    }

    @Test
    public void testCEOHasNullManagerId() {
        assertNull(manager.getManagerId());
        assertNotNull(employee.getManagerId());
    }

    @Test
    public void testEmployeeToString() {
        String employeeString = employee.toString();
        assertTrue(employeeString.contains("Jane"));
        assertTrue(employeeString.contains("Employee"));
    }

    @Test
    public void testMultipleSubordinates() {
        Employee anotherEmployee = new Employee(3, "Alice", "Worker", 60000, 1);

        manager.addSubordinate(employee);
        manager.addSubordinate(anotherEmployee);

        assertEquals(2, manager.getSubordinates().size());
    }
}