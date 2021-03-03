package dev.kiser.services;

import dev.kiser.daos.*;
import dev.kiser.entities.Expense;

import java.util.Set;

public class ExpenseService implements ExpenseServiceIF {

    private ExpenseDaoIF xdao = new ExpenseDaoPostgres();
    private EmployeeDaoIF edao = new EmployeeDaoPostgres();
    private ManagerDaoIF mdao = new ManagerDaoPostgres();

    /**
     * Register the expense
     *
     * @param empId   employee id
     * @param expense expense to create
     */
    @Override
    public Expense registerExpense(int empId, Expense expense) {
        if (expense.getAmount() == 0 || expense.getEmpReason().equals("") || expense.getEmpReason() == null) {
            return null;
        }
        expense.setStatus("Pending");
        return xdao.createExpense(empId, expense);
    }

    /**
     * get all the expenses for every employee
     *
     * @param role manager or employee
     */
    @Override
    public Set<Expense> getAllExpenses(String role) {
        if (role.equals("employee")) {
            return null;
        }
        return xdao.getAllExpenses();
    }

    /**
     * get the expenses for an employee
     *
     * @param empId employee id
     */
    @Override
    public Set<Expense> getExpensesByEmployee(int empId) {
        return xdao.getExpenseByEmployee(empId);
    }

    /**
     * get the expenses by the id
     *
     * @param empId     employee id
     * @param expenseId expense id
     */
    @Override
    public Expense getExpenseById(int empId, int expenseId) {
        return xdao.getExpenseById(empId, expenseId);
    }

    /**
     * get the expense by status -1: manager getting all the expenses of a status
     *
     * @param empId  employee id
     * @param status status of the expenses
     */
    @Override
    public Set<Expense> getExpenseByStatus(int empId, String status) {
        if (empId == -1) {
            return xdao.getExpensesByStatus(status);
        } else {
            return xdao.getExpensesByStatus(empId, status);
        }
    }

    /**
     * update the expense (MANAGER ONLY)
     *
     * @param manId   manager id
     * @param expense expense to update with
     * @param role    employee or manager
     */
    @Override
    public Expense updateExpense(int manId, Expense expense, String role) {
        if (role.equals("employee") || mdao.getManagerByID(expense.getEmpId()) == null) {
            return null;
        }
        return xdao.updateExpense(manId, expense);
    }

    /**
     * delete the specific expense
     *
     * @param empId     employee id
     * @param expenseId expense id
     */
    @Override
    public boolean deleteExpense(int empId, int expenseId) {
        if (xdao.getExpenseById(empId, expenseId) == null) {
            return false;
        }
        return xdao.deleteExpense(empId, expenseId);
    }
}
