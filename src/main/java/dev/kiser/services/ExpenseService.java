package dev.kiser.services;

import dev.kiser.daos.EmployeeDaoIF;
import dev.kiser.daos.ExpenseDaoIF;
import dev.kiser.daos.ManagerDaoIF;
import dev.kiser.entities.Employee;
import dev.kiser.entities.Expense;
import dev.kiser.entities.Manager;
import dev.kiser.exceptions.*;
import org.apache.log4j.Logger;

import java.util.Set;

public class ExpenseService implements ExpenseServiceIF {

    Logger logger = Logger.getLogger(ExpenseService.class);

    private final ExpenseDaoIF xdao;
    private final EmployeeDaoIF edao;
    private final ManagerDaoIF mdao;


    // constructor
    public ExpenseService(ExpenseDaoIF xdao, EmployeeDaoIF edao, ManagerDaoIF mdao) {
        this.xdao = xdao;
        this.edao = edao;
        this.mdao = mdao;
    }

    /**
     * Register the expense
     *
     * @param empId   employee id
     * @param expense expense to create
     */
    @Override
    public Expense registerExpense(int empId, Expense expense)
            throws EmployeeNotFoundException, OperationNotPossible, ExpenseException {

        // make sure the values are appropriate
        if (expense.getAmount() == 0 || expense.getEmpReason().equals("") || expense.getEmpReason() == null) {
            throw new OperationNotPossible("Fields must be correct");
        }
        //make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            throw new EmployeeNotFoundException("Employee could not be found");
        }
        // all new expenses should have a 'pending' status
        expense.setStatus("pending");
        expense.setManagerId(0);
        expense.setManagerReason("");
        // create the expense
        Expense newExpense = xdao.createExpense(empId, expense);
        // make sure the dao ran appropriately
        if (newExpense == null) {
            throw new ExpenseException("Error creating expense");
        }

        return newExpense;

    }

    /**
     * get all the expenses for every employee
     */
    @Override
    public Set<Expense> getAllExpenses() {
        return xdao.getAllExpenses();
    }

    /**
     * get the expenses for an employee
     *
     * @param empId employee id
     */
    @Override
    public Set<Expense> getExpensesByEmployee(int empId)
            throws EmployeeNotFoundException, ExpenseException {

        // make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            throw new EmployeeNotFoundException("Employee could not be found");
        }
        Set<Expense> expenses = xdao.getExpenseByEmployee(empId);
        if (expenses == null) {
            throw new ExpenseException("Could not get all expenses for employee");
        }
        return expenses;
    }

    /**
     * get the expenses by the id
     *
     * @param empId     employee id
     * @param expenseId expense id
     */
    @Override
    public Expense getExpenseById(int empId, int expenseId)
            throws EmployeeNotFoundException {

        // make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            throw new EmployeeNotFoundException("Employee could not be found");
        }
        return xdao.getExpenseById(empId, expenseId);
    }

    /**
     * get the expense by status -1: manager getting all the expenses of a status
     *
     * @param empId  employee id
     * @param status status of the expenses
     */
    @Override
    public Set<Expense> getExpenseByStatus(int empId, String status)
            throws EmployeeNotFoundException, OperationNotPossible {

        // make sure the values are correct.
        if (empId != -1 && edao.getEmployeeById(empId) == null) {
            throw new EmployeeNotFoundException("Employee could not be found");
        }
        // make sure the status is a valid status
        if (!(status.toLowerCase()).equals("approved") && !(status.toLowerCase()).equals("denied")
                && !(status.toLowerCase().equals("pending"))) {
            throw new OperationNotPossible("The status is incorrect");
        }
        // if -1 run the manager get expense by status; otherwise run employee
        if (empId == -1) {
            return xdao.getExpensesByStatus(status);
        } else {
            return xdao.getExpensesByStatus(empId, status);
        }
    }

    /**
     * update the expense (MANAGER ONLY)
     *
     * @param empId   employee id of the manager
     * @param expense expense to update with
     */
    @Override
    public Expense updateExpense(int empId, Expense expense)
            throws ManagerNotFoundException, OperationNotPossible, ExpenseNotFoundException {

        // get the manager
        Manager manager = mdao.getManagerByID(empId);

        // make sure the manager does exist
        if (manager == null) {
            throw new ManagerNotFoundException("Manager could not be found");
        }
        // make sure the status is 'approved' or 'denied'
        if (!(expense.getStatus().toLowerCase()).equals("approved") && !(expense.getStatus().toLowerCase()).equals("denied")) {
            throw new OperationNotPossible("The status is incorrect");
        }
        // make sure the manager is not trying to approve their own expense
        if (expense.getEmpId() == manager.getEmpId()) {
            throw new OperationNotPossible("Manager cannot update their own expense");
        }
        // make sure the expense to update exists
        if (xdao.getExpenseById(expense.getEmpId(), expense.getExpenseId()) == null) {
            throw new ExpenseNotFoundException("Expense does not exist");
        }

        // uniformity in database, all statuses are lowercase
        String lower = expense.getStatus().toLowerCase();
        expense.setStatus(lower);
        // make sure the manager id is correct takes precedents
        expense.setManagerId(manager.getManId());
        return xdao.updateExpense(manager.getManId(), expense);
    }

    /**
     * delete the specific expense
     *
     * @param empId     employee id
     * @param expenseId expense id
     */
    @Override
    public boolean deleteExpense(int empId, int expenseId)
            throws EmployeeNotFoundException, ExpenseNotFoundException, OperationNotPossible {
        // make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            throw new EmployeeNotFoundException("Employee could not be found");
        }
        // make sure the expense exists

        Expense expenseById = xdao.getExpenseById(empId, expenseId);
        if (expenseById == null) {
            throw new ExpenseNotFoundException("The expense does not exist");
        }
        // make sure only pending requests can be deleted
        if (expenseById.getStatus().equals("pending")) {
            return xdao.deleteExpense(empId, expenseId);
        } else {
            throw new OperationNotPossible("The status must be 'pending'");
        }
    }

    /**
     * gets the employee by username and password
     *
     * @param username username
     * @param password password
     */
    @Override
    public Employee getEmpById(String username, String password) throws LoginFailedExceptions {
        Employee login = edao.getEmployeeByName(username, password);
        if (login == null) {
            throw new LoginFailedExceptions("The username or password is incorrect");
        }
        return login;
    }

    /**
     * gets the manager by the employee id
     *
     * @param empId employee id
     */
    @Override
    public Manager getManById(int empId) throws EmployeeNotFoundException, ManagerNotFoundException {
        // make sure the employee exists. Must exist for manager to exist
        if (edao.getEmployeeById(empId) == null) {
            throw new EmployeeNotFoundException("Employee could not be found");
        }

        Manager manager = mdao.getManagerByID(empId);
        // make sure the manager exists
        if (manager == null) {
            throw new ManagerNotFoundException("The manager does not exist");
        }
        return manager;
    }
}
