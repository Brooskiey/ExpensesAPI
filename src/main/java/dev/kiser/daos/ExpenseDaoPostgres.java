package dev.kiser.daos;

import dev.kiser.entities.Expense;

import java.util.Set;

public class ExpenseDaoPostgres  implements ExpenseDaoIF{

    /**
     * create an expense
     *
     * @param empId: employee id
     * @param expense: expense id
     */
    @Override
    public Expense createExpense(int empId, Expense expense) {
        return null;
    }

    /**
     * get all Expenses (MANAGER ONLY)
     */
    @Override
    public Set<Expense> getAllExpenses() {
        return null;
    }

    /**
     * get expense by id
     *
     * @param empId: employee id
     * @param expenseId: expense id
     */
    @Override
    public Expense getExpenseById(int empId, int expenseId) {
        return null;
    }

    /**
     * get expenses by status
     * -1: manager getting all the expenses of a status
     *
     * @param empId: employee id
     * @param status: "denied", "approved"
     */
    @Override
    public Set<Expense> getExpensesByStatus(int empId, String status) {
        return null;
    }

    /**
     * get all expenses for an employee
     *
     * @param empId: employee id
     */
    @Override
    public Set<Expense> getExpenseByEmployee(int empId) {
        return null;
    }

    /**
     * update an expense (MANAGER ONLY: update status)
     *
     * @param empId: employee id
     * @param expenseId: expense id
     */
    @Override
    public Expense updateExpense(int empId, int expenseId) {
        return null;
    }

    /**
     * delete an expense
     *
     * @param empId: employee id
     * @param expenseId: expense id
     */
    @Override
    public boolean deleteExpense(int empId, int expenseId) {
        return false;
    }
}
