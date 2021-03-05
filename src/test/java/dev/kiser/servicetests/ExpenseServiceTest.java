package dev.kiser.servicetests;

import dev.kiser.daos.*;
import dev.kiser.entities.Expense;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import org.junit.jupiter.api.*;

import java.util.Set;

/**
 * Mockito tests for the service
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseServiceTest {


    ExpenseDaoIF xdao = new ExpenseDaoPostgres();
    EmployeeDaoIF edao = new EmployeeDaoPostgres();
    ManagerDaoIF mdao = new ManagerDaoPostgres();

    ExpenseServiceIF serviceIf = new ExpenseService(xdao, edao, mdao);

    @Test
    @Order(1)
    void get_all_expenses() {
        Set<Expense> set = serviceIf.getAllExpenses();

        System.out.println("\nregister_expense: assertNotEquals" +
                "\n\tUnexpected: 0" +
                "\n\tActual: " + set.size());
        Assertions.assertNotEquals(0, set.size());
    }

    @Test
    @Order(2)
    void get_expense_by_employee() {
        Set<Expense> set = serviceIf.getExpensesByEmployee(3);

        System.out.println("\nregister_expense: assertNotEquals" +
                "\n\tUnexpected: 0" +
                "\n\tActual: " + set.size());
        Assertions.assertNotEquals(0, set.size());
    }

    @Test
    @Order(3)
    void get_expense_by_status() {
        Set<Expense> expenses = serviceIf.getExpenseByStatus(2, "approve");

        for (Expense expense : expenses) {
            if (!expense.getStatus().equals("approve")) {
                Assertions.fail();
            }
        }

        System.out.println("\nget_expense_by_status: Assertion.fail()" +
                "\n\tTests pass with no fail");
    }

    @Test
    @Order(4)
    void get_expense_by_id() {
        int empId = 2;
        Expense expense = new Expense(null, 0, null,
                "I need a plane ticket home",
                2, 300, null, null, 0);
        serviceIf.registerExpense(empId, expense);

        Expense expenseID = serviceIf.getExpenseById(empId, expense.getExpenseId());

        System.out.println("\nget_expense_by_id: assertEquals" +
                "\nEmployee Id" +
                "\n\tExpected: " + empId +
                "\n\tActual: " + expenseID.getEmpId() +
                "\nExpense ID" +
                "\n\tExpected: " + expense.getExpenseId() +
                "\n\tActual: " + expenseID.getExpenseId());
        Assertions.assertEquals(empId, expenseID.getEmpId());
        Assertions.assertEquals(expense.getExpenseId(), expenseID.getExpenseId());
    }

    @Test
    @Order(5)
    void update_expense() {
        Expense expense = new Expense("Approved", 0, null,
                "I need a plane ticket home",
                3, 300, null, null, 0);
        int manId = 2;

        serviceIf.registerExpense(3, expense);
        expense.setStatus("Approved");

        expense = serviceIf.updateExpense(manId, expense);

        System.out.println("\nupdate_expense: assertEquals" +
                "\n\tExpected: " + manId +
                "\n\tActual: " + expense.getManagerId());
        Assertions.assertEquals(manId, expense.getManagerId());
    }

    @Test
    @Order(6)
    void delete_expense() {
        Expense expense = new Expense("Approved", 0, null,
                "I need a plane ticket home",
                3, 300, null, null, 0);
        serviceIf.registerExpense(3, expense);

        boolean result = xdao.deleteExpense(expense.getEmpId(), expense.getExpenseId());

        System.out.println("\ndelete_expense: assertTrue" +
                "\n\tExpected: true" +
                "\n\tActual: " + result);
        Assertions.assertTrue(result);
    }
}
