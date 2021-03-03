package dev.kiser.services;

import dev.kiser.entities.Expense;

import java.util.Set;

public interface ExpenseServiceIF {

    /**
     * Register the expense
     */
    Expense registerExpense(int empId, Expense expense);

    /**
     * get all the expenses for every employee
     */
    Set<Expense> getAllExpenses(String role);

    /**
     * get the expenses for an employee
     */
    Set<Expense> getExpensesByEmployee(int empId);

    /**
     * get the expenses by the id
     */
    Expense getExpenseById(int empId, int expenseId);

    /**
     * get the expense by status -1: manager getting all the expenses of a status
     */
    Set<Expense> getExpenseByStatus(int empId, String status);

    /**
     * update the expense (MANAGER ONLY)
     */
    Expense updateExpense(int manId, Expense expense, String role);

    /**
     * delete the specific expense
     */
    boolean deleteExpense(int empId, int expenseId);
}
