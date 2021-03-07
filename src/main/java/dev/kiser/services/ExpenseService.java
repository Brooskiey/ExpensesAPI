package dev.kiser.services;

import dev.kiser.daos.EmployeeDaoIF;
import dev.kiser.daos.ExpenseDaoIF;
import dev.kiser.daos.ManagerDaoIF;
import dev.kiser.entities.Employee;
import dev.kiser.entities.Expense;
import dev.kiser.entities.Manager;
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
    public Expense registerExpense(int empId, Expense expense) {

        // make sure the values are appropriate
        if (expense.getAmount() == 0 || expense.getEmpReason().equals("") || expense.getEmpReason() == null) {
            return null;
        }
        //make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            return null;
        }
        // all new expenses should have a 'pending' status
        expense.setStatus("pending");
        expense.setManagerId(0);
        expense.setManagerReason("");
        return xdao.createExpense(empId, expense);

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
    public Set<Expense> getExpensesByEmployee(int empId) {
        // make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            return null;
        }
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
        // make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            return null;
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
    public Set<Expense> getExpenseByStatus(int empId, String status) {
        // make sure the values are correct.
        if (empId != -1 && edao.getEmployeeById(empId) == null) {
            return null;
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
    public Expense updateExpense(int empId, Expense expense) {

        // get the manager
        Manager manager = mdao.getManagerByID(empId);

        // make sure the manager does exist
        if (manager == null) {
            return null;
        }
        // make sure the status is 'approved' or 'denied'
        if (!(expense.getStatus().toLowerCase()).equals("approved") && !(expense.getStatus().toLowerCase()).equals("denied")) {
            return null;
        }
        // make sure the manager is not trying to approve their own expense
        if (expense.getEmpId() == manager.getManId()) {
            return null;
        }

        // uniformity in database, all statuses are lowercase
        String lower = expense.getStatus().toLowerCase();
        expense.setStatus(lower);
        // make sure the manager id is correct takes precedents
        expense.setManagerId(manager.getManId());
        return xdao.updateExpense(empId, expense);
    }

    /**
     * delete the specific expense
     *
     * @param empId     employee id
     * @param expenseId expense id
     */
    @Override
    public boolean deleteExpense(int empId, int expenseId) {
        // make sure the employee exists
        if (edao.getEmployeeById(empId) == null) {
            return false;
        }
        // make sure the expense exists
        if (xdao.getExpenseById(empId, expenseId) == null) {
            return false;
        }
        return xdao.deleteExpense(empId, expenseId);
    }

    /**
     * gets the employee by username and password
     *
     * @param username username
     * @param password password
     */
    @Override
    public Employee getEmpById(String username, String password) {
        return edao.getEmployeeByName(username, password);
    }

    /**
     * gets the manager by the employee id
     *
     * @param empId employee id
     */
    @Override
    public Manager getManById(int empId) {
        // make sure the employee exists. Must exist for manager to exist
        if (edao.getEmployeeById(empId) == null) {
            return null;
        }
        return mdao.getManagerByID(empId);
    }
}
