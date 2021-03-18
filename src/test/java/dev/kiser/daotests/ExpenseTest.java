package dev.kiser.daotests;

import dev.kiser.daos.ExpenseDaoIF;
import dev.kiser.daos.ExpenseDaoPostgres;
import dev.kiser.entities.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ExpenseTest {

    ExpenseDaoIF xdao = new ExpenseDaoPostgres();

    @Test
    void create_expense() {
        Expense expense = new Expense("Approved", 0, null,
                "I need a plane ticket home",
                0, 1500, null, null, 0);

        expense = xdao.createExpense(3, expense);

        System.out.println("\ncreate_expense: assertNotEquals" +
                "\n\tUnexpected: 0" +
                "\n\tActual: " + expense.getExpenseId());
        Assertions.assertNotEquals(0, expense.getExpenseId());
    }

    @Test
    void get_all_expenses() {
        Set<Expense> expenses = xdao.getAllExpenses();

        System.out.println("\nget_all_expenses: assertNotEquals" +
                "\n\tUnexpected Size: 0" +
                "\n\tActual Size: " + expenses.size());
        Assertions.assertNotEquals(0, expenses.size());
    }

    @Test
    void get_expense_by_id() {
        int empId = 3;
        int xId = 8;

        Expense expense = xdao.getExpenseById(empId, xId);

        System.out.println("\nget_all_expenses: assertEquals" +
                "\nEmployee Id" +
                "\n\tExpected: " + empId +
                "\n\tActual: " + expense.getEmpId() +
                "\nExpense ID" +
                "\n\tExpected: " + xId +
                "\n\tActual: " + expense.getExpenseId());
        Assertions.assertEquals(empId, expense.getEmpId());
        Assertions.assertEquals(xId, expense.getExpenseId());
    }

    @Test
    void get_expense_by_status() {
        String status = "pending";
        int empId = 3;

        Set<Expense> expense = xdao.getExpensesByStatus(empId, status);

        System.out.println("\nget_expenses_by_status: assertNotEquals" +
                "\n\tUnexpected Size: 0" +
                "\n\tActual Size: " + expense.size());
        Assertions.assertNotEquals(0, expense.size());
    }

    @Test
    void get_all_expense_by_status() {
        String status = "pending";
        Set<Expense> expense = xdao.getExpensesByStatus(status);

        System.out.println("\nget_all_expenses_by_status: assertNotEquals" +
                "\n\tUnexpected Size: 0" +
                "\n\tActual Size: " + expense.size());
        Assertions.assertNotEquals(0, expense.size());
    }

    @Test
    void get_expense_by_employee() {
        int empId = 7;
        Set<Expense> expense = xdao.getExpenseByEmployee(empId);

        System.out.println("\nget_expense_by_employee: assertNotEquals" +
                "\n\tUnexpected Size: 0" +
                "\n\tActual Size: " + expense.size());
        Assertions.assertNotEquals(0, expense.size());
    }

    @Test
    void update_expense() {
        Expense expense = new Expense("Approved", 14, "Go with Potato. Go see your sister and Auntie BB.",
                "I need a plane ticket home",
                3, 1500, null, null, 2);
        int manId = 2;

        expense = xdao.updateExpense(manId, expense);

        System.out.println("\nupdate_expense: assertEquals" +
                "\n\tExpected: " + manId +
                "\n\tActual: " + expense.getManagerId());
        Assertions.assertEquals(manId, expense.getManagerId());
    }

    @Test
    void delete_expense() {
        int expenseId = 2;
        int employeeId = 3;

        boolean result = xdao.deleteExpense(employeeId, expenseId);

        System.out.println("\ndelete_expense: assertTrue" +
                "\n\tExpected: true" +
                "\n\tActual: " + result);
        Assertions.assertTrue(result);
    }
}
