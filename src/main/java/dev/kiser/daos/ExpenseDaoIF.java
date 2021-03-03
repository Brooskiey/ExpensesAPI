package dev.kiser.daos;

import dev.kiser.entities.Expense;

import java.util.Set;

public interface ExpenseDaoIF {

    /** create an expense */
    Expense createExpense(int empId, Expense expense);

    /** get all Expenses
     * MANAGER ONLY
     */
    Set<Expense> getAllExpenses();

    /** get expense by id */
    Expense getExpenseById(int empId, int expenseId);

    /** get expenses by status
     * -1: manager getting all the expenses of a status
     */
    Set<Expense> getExpensesByStatus(int empId, String status);

    /** get all expenses for an employee */
    Set<Expense> getExpenseByEmployee(int empId);

    /**
     * update an expense MANAGER ONLY: update status
     */
    Expense updateExpense(int manId, Expense expense);

    /** delete an expense */
    boolean deleteExpense(int empId, int expenseId);
}
