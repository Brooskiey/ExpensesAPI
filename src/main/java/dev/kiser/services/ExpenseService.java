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
        if (expense.getAmount() == 0 || expense.getEmpReason().equals("") || expense.getEmpReason() == null) {
            return null;
        }
        if (edao.getEmployeeById(empId) == null) {
            return null;
        }
        expense.setStatus("pending");
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
        if (empId != -1 && edao.getEmployeeById(empId) == null) {
            return null;
        }
        if (empId == -1) {
            Set<Expense> expenses = xdao.getExpensesByStatus(status);
            return expenses;
        } else {
            Set<Expense> expenses = xdao.getExpensesByStatus(empId, status);
            return expenses;
        }
    }

    /**
     * update the expense (MANAGER ONLY)
     *
     * @param manId   manager id
     * @param expense expense to update with
     */
    @Override
    public Expense updateExpense(int manId, Expense expense) {
        if (mdao.getManagerByID(expense.getEmpId()) == null) {
            return null;
        }
        if (!(expense.getStatus().toLowerCase()).equals("approved") && !(expense.getStatus().toLowerCase()).equals("denied")) {
            return null;
        }
        if (expense.getEmpId() == mdao.getManagerByID(expense.getEmpId()).getManId()) {
            return null;
        }

        String lower = expense.getStatus().toLowerCase();
        expense.setStatus(lower);
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
        if (edao.getEmployeeById(empId) == null) {
            return false;
        }
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
        if (edao.getEmployeeById(empId) == null) {
            return null;
        }
        return mdao.getManagerByID(empId);
    }
}
