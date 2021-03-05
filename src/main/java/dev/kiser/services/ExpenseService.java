package dev.kiser.services;

import dev.kiser.daos.EmployeeDaoIF;
import dev.kiser.daos.ExpenseDaoIF;
import dev.kiser.daos.ManagerDaoIF;
import dev.kiser.entities.Expense;
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
            logger.debug("Amount was 0 or emp reason was '' or null in registerExpense");
            return null;
        }
        if (edao.getEmployeeById(empId) == null) {
            logger.debug("Employee by id in registerExpense is null");
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
        if (edao.getEmployeeById(empId) == null) {
            return null;
        }
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
     */
    @Override
    public Expense updateExpense(int manId, Expense expense) {
        if (mdao.getManagerByID(expense.getEmpId()) == null) {
            return null;
        }
        if (!(expense.getStatus().toLowerCase()).equals("approved") && !(expense.getStatus().toLowerCase()).equals("denied")) {
            return null;
        }
        if (expense.getEmpId() == manId) {
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
}
