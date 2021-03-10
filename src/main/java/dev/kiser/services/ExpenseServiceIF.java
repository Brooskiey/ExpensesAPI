package dev.kiser.services;

import dev.kiser.entities.Employee;
import dev.kiser.entities.Expense;
import dev.kiser.entities.Manager;
import dev.kiser.exceptions.*;

import java.util.Set;

public interface ExpenseServiceIF {

    /**
     * Register the expense
     */
    Expense registerExpense(int empId, Expense expense) throws EmployeeNotFoundException, OperationNotPossible, ExpenseException;

    /**
     * get all the expenses for every employee
     */
    Set<Expense> getAllExpenses();

    /**
     * get the expenses for an employee
     */
    Set<Expense> getExpensesByEmployee(int empId) throws EmployeeNotFoundException, ExpenseException;

    /**
     * get the expenses by the id
     */
    Expense getExpenseById(int empId, int expenseId) throws EmployeeNotFoundException;

    /**
     * get the expense by status -1: manager getting all the expenses of a status
     */
    Set<Expense> getExpenseByStatus(int empId, String status) throws EmployeeNotFoundException, OperationNotPossible;

    /**
     * update the expense (MANAGER ONLY)
     */
    Expense updateExpense(int manId, Expense expense) throws ManagerNotFoundException, OperationNotPossible, ExpenseNotFoundException;

    /**
     * delete the specific expense
     */
    boolean deleteExpense(int empId, int expenseId) throws EmployeeNotFoundException, ExpenseNotFoundException, OperationNotPossible;

    /**
     * gets the employee by username and password
     */
    Employee getEmpById(String username, String password) throws LoginFailedExceptions;

    /**
     * gets the manager by the employee id
     */
    Manager getManById(int empId) throws EmployeeNotFoundException, ManagerNotFoundException;
}
