package dev.kiser.daotests;

import dev.kiser.daos.EmployeeDaoIF;
import dev.kiser.daos.EmployeeDaoPostgres;
import dev.kiser.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class EmployeeTest {

    EmployeeDaoIF edao = new EmployeeDaoPostgres();

    @Test
    void get_all_employees() {
        Set<Employee> employees = edao.getAllEmployees();

        System.out.println("\nget_all_employees: assertEquals" +
                "\n\tExpected: 6" +
                "\n\tActual: " + employees.size());
        Assertions.assertEquals(6, employees.size());

    }

    @Test
    void get_employee_by_id() {
        int empId = 2;
        String username = "Unwonted";
        String password = "Brian";

        Employee employee = edao.getEmployeeById(empId, username, password);

        System.out.println("\nget_employee_by_id: assertEquals" +
                "\nEmployee ID" +
                "\n\tExpected: " + empId +
                "\n\tActual: " + employee.getEmpId() +
                "\nUsername" +
                "\n\tExpected : " + username +
                "\n\tActual: " + employee.getUsername() +
                "\nPassword" +
                "\n\tExpected: " + password +
                "\n\tActual: " + employee.getPassword());
        Assertions.assertEquals(empId, employee.getEmpId());
        Assertions.assertEquals(username, employee.getUsername());
        Assertions.assertEquals(password, employee.getPassword());
    }

    @Test
    void update_employee() {
        Employee employee = new Employee(1, "Brooke", "Kiser", "BLKiser@yahoo.com",
                "Brooke", "BaKiser");

        Employee upEmployee = edao.updateEmployee(employee);

        System.out.println("\nupdate_employee: assertEquals" +
                "\n\tExpected: " + employee.getUsername() +
                "\n\tActual: " + upEmployee.getUsername());
        Assertions.assertEquals(employee.getUsername(), upEmployee.getUsername());
    }

//    @Test
//    void delete_employee(){
//        int empId = 1;
//
//        boolean deleted = edao.deleteEmployee(empId);
//
//        System.out.println("\ndelete_employee: assertTrue" +
//                "\n\tExpected: true" +
//                "\n\tActual: " + deleted);
//        Assertions.assertTrue(deleted);
//    }
}
